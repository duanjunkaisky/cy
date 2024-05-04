package com.djk.core.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.djk.core.config.Constant;
import com.djk.core.mapper.*;
import com.djk.core.model.*;
import com.djk.core.utils.FreeMakerUtil;
import com.djk.core.utils.HttpResp;
import com.djk.core.utils.HttpUtil;
import com.djk.core.vo.ContainerDist;
import com.djk.core.vo.QueryRouteVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
@Data
public class CrawlServiceFroCmaImpl extends BaseSimpleCrawlService implements CrawlService
{
    private static int reqCount = 0;
    private static int tokenIndex = 0;

    @Autowired
    BasePortMapper basePortMapper;

    @Autowired
    CrawlMetadataWebsiteConfigMapper crawlMetadataWebsiteConfigMapper;

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Autowired
    ProductContainerMapper productContainerMapper;

    @Autowired
    ProductFeeItemMapper productFeeItemMapper;

    public static List<ContainerDist> containerList = new ArrayList<>(3);

    static {
        containerList.add(ContainerDist.builder().containerCode("20ST").containerType("22G1").build());
        containerList.add(ContainerDist.builder().containerCode("40ST").containerType("42G1").build());
        containerList.add(ContainerDist.builder().containerCode("45HC").containerType("45G1").build());
    }

    @Override
    @Transactional
    public String queryData(QueryRouteVo queryRouteVo, String hostCode)
    {
        this.setHostCode(hostCode);
        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 开始爬取数据");
        BasePort fromPort = getFromPort(queryRouteVo);
        BasePort toPort = getToPort(queryRouteVo);
        JSONObject portInfoFrom = getPortInfo(queryRouteVo, fromPort.getOneCode(), fromPort.getCountryCode());
        JSONObject portInfoTo = getPortInfo(queryRouteVo, toPort.getOneCode(), toPort.getCountryCode());

        BaseShippingCompany baseShippingCompany = getShipCompany(hostCode);

        Map<String, ProductInfo> existMap = new HashMap<>();
        List<ProductInfo> productInfoList = new ArrayList<>();
        List<ProductContainer> productContainerList = new ArrayList<>();
        List<ProductFeeItem> productFeeItemList = new ArrayList<>();

        for (ContainerDist container : containerList) {
            while (reqCount < Constant.MAX_REQ_COUNT) {
                try {
                    reqCount++;
                    Map<String, String> header = getHeader();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar instance = Calendar.getInstance();
                    instance.setTime(new Date());
                    instance.add(Calendar.DAY_OF_YEAR, 2);
                    Date queryTime = instance.getTime();

                    Map<String, Object> fillData = new HashMap<>(1);
                    fillData.put("fromPortCode", portInfoFrom.getString("portCode"));
                    fillData.put("toPortCode", portInfoTo.getString("portCode"));
                    fillData.put("containerCode", container.getContainerCode());
                    fillData.put("queryDate", format.format(queryTime));
                    String jsonParam = FreeMakerUtil.createByTemplate("cmaQuery.ftl", fillData);

                    log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求, \n" + "header: " + JSONObject.toJSONString(header) + "\nbody: " + JSONObject.toJSONString(JSONObject.parseObject(jsonParam)));

                    HttpResp resp = HttpUtil.postBody("https://www.cma-cgm.com/ebusiness/pricing/getbestoffer", header, jsonParam);
                    Response response = resp.getResponse();
                    String bodyJson = resp.getBodyJson();
                    JSONObject retObj = JSONObject.parseObject(bodyJson);
                    if (response.code() != 200 && response.code() != 201) {
                        tokenIndex++;
                        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求返回失败\n" + bodyJson);
                        continue;
                    }
                    log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求返回成功");
                    JSONArray quoteLineAndRoutingHeaders = retObj.getJSONArray("quoteLineAndRoutingHeaders");
                    if (null == quoteLineAndRoutingHeaders) {
                        tokenIndex++;
                        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求返回失败: quoteLineAndRoutingHeaders 为空\n" + bodyJson);
                        continue;
                    }

                    String loggedId = retObj.getString("loggedId");
                    String traceId = retObj.getString("traceId");

                    JSONArray offers = new JSONArray();
                    for (Object qlar : quoteLineAndRoutingHeaders) {
                        JSONObject quoteLineAndRoutingHeader = (JSONObject) qlar;
                        int solutionNumber = quoteLineAndRoutingHeader.getIntValue("SolutionNumber");
                        int ScheduleNumber = quoteLineAndRoutingHeader.getIntValue("ScheduleNumber");
                        String offerId = quoteLineAndRoutingHeader.getString("OfferId");
                        JSONObject feeObj = getProductStatus(queryRouteVo, loggedId, solutionNumber, ScheduleNumber, offerId, traceId);
                        if (null != feeObj) {
                            quoteLineAndRoutingHeader.put("loggedId", loggedId);
                            quoteLineAndRoutingHeader.put("traceId", traceId);
                            quoteLineAndRoutingHeader.put("fee", feeObj);
                            offers.add(quoteLineAndRoutingHeader);
                        }
                    }

                    //开始处理入库
                    parseData(baseShippingCompany, container, offers, fromPort, toPort, productInfoList, productContainerList, productFeeItemList, existMap);
                    reqCount = 0;
                    break;
                } catch (Exception e) {
                    log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求出错");
                    log.error(ExceptionUtil.getMessage(e));
                    log.error(ExceptionUtil.stacktraceToString(e));
                }
            }
            reqCount = 0;
        }

        return insertData(queryRouteVo, hostCode, productInfoList, productContainerList, productFeeItemList);
    }


    private void parseData(BaseShippingCompany baseShippingCompany, ContainerDist container, JSONArray offers, BasePort fromPort, BasePort toPort, List<ProductInfo> productInfoList, List<ProductContainer> productContainerList, List<ProductFeeItem> productFeeItemList, Map<String, ProductInfo> existMap) throws ParseException
    {
        int containerType = computeContainerType(container.getContainerType());

        for (Object o : offers) {
            JSONObject item = (JSONObject) o;
            JSONArray routingDetails = item.getJSONArray("RoutingDetails");
            JSONObject departureStart = routingDetails.getJSONObject(0);
            JSONObject departureEnd = routingDetails.getJSONObject(routingDetails.size() - 1);
            ProductInfo productInfo = new ProductInfo();
            productInfo.setDeparturePortZh(fromPort.getPortNameZh());
            productInfo.setDeparturePortEn(fromPort.getPortCode());
            productInfo.setDestinationPortZh(toPort.getPortNameZh());
            productInfo.setDestinationPortEn(toPort.getPortCode());
            productInfo.setShippingCompanyId(baseShippingCompany.getId());
            productInfo.setCnFullName(baseShippingCompany.getCnFullName());
            productInfo.setCnAbbreviation(baseShippingCompany.getCnAbbreviation());
            productInfo.setImage(baseShippingCompany.getImage());

            productInfo.setEstimatedDepartureDate(parseDate(departureStart.getString("DepartureDate")));
            //scheduleDetails有多个就是中转，只有一个就是直达
            productInfo.setRoute(routingDetails.size() == 1 ? 1 : 2);
            productInfo.setCourse(fromPort.getPortNameZh() + " - " + toPort.getPortNameZh());
            productInfo.setArrivalTime(parseDate(departureEnd.getString("ArrivalDate")));
            productInfo.setProductExpiryDate(parseDate(item.getString("BookingCutoffDate")));

            productInfo.setShipName(item.getString("VesselName"));
            productInfo.setVoyageNumber(item.getString("Voyage"));
            productInfo.setDistance(item.getString("TransitTime"));
            productInfo.setCargoType("G");
            productInfo.setProductType("P");
            productInfo.setWaiverOfContainerInstructions("无说明");
            productInfo.setDeficitFreightInstructions("无说明");
            productInfo.setReviewStatus(3);
            productInfo.setStatus((byte) 0);
            productInfo.setCreateTime(new Date());
            productInfo.setUpdateTime(new Date());
            productInfo.setDeleted(false);
            productInfo.setTenantId(0L);

            productInfo.setSpotId(createSpotId(productInfo.getDeparturePortEn(), productInfo.getDestinationPortEn()));

            String existKey = productInfo.getDeparturePortEn()
                    + productInfo.getDestinationPortEn()
                    + productInfo.getShippingCompanyId()
                    + productInfo.getEstimatedDepartureDate()
                    + productInfo.getShipName()
                    + productInfo.getVoyageNumber();
            if (!existMap.containsKey(existKey)) {
                productInfo.setId(Generator.nextId());
                productInfoList.add(productInfo);
                existMap.put(existKey, productInfo);
            } else {
                ProductInfo existProduct = existMap.get(existKey);
                productInfo.setId(existProduct.getId());
            }

            JSONObject fee = item.getJSONObject("fee");
            JSONObject chargeDetail = fee.getJSONArray("ChargeDetails").getJSONObject(0);

            ProductContainer productContainer = new ProductContainer();
            productContainer.setContainerType(containerType);
            productContainer.setProductId(productInfo.getId());
            productContainer.setSpotId(productInfo.getSpotId());
            productContainer.setSellingPrice(chargeDetail.getJSONObject("TotalCharge").getBigDecimal("Amount"));
            productContainer.setCost(productContainer.getSellingPrice());
            productContainer.setCreateTime(new Date());
            productContainer.setUpdateTime(new Date());
            productContainer.setDeleted(false);
            productContainer.setTenantId(0L);
            productContainerList.add(productContainer);

            ProductFeeItem productFeeItem = new ProductFeeItem();
            productFeeItem.setContainerType(containerType);
            productFeeItem.setSpotId(productInfo.getSpotId());
            productFeeItem.setProductId(productInfo.getId());
            //1箱；2柜
            productFeeItem.setFeeUnit(1);
            productFeeItem.setFeeSource(1);
            productFeeItem.setCreateTime(new Date());
            productFeeItem.setUpdateTime(new Date());
            productFeeItem.setDeleted(false);
            productFeeItem.setTenantId(0L);

            JSONObject oceanFreight = chargeDetail.getJSONObject("OceanFreight");
            //基础航运费
            parseFeeIntoList(productFeeItemList, productFeeItem, oceanFreight, 6);

            JSONArray prepaidChargeDetails = chargeDetail.getJSONArray("PrepaidChargeDetails");
            JSONObject blChargeDetail = fee.getJSONObject("BlChargeDetail");
            JSONArray prepaidChargeDetails2 = blChargeDetail.getJSONArray("PrepaidChargeDetails");
            JSONArray array = new JSONArray();
            array.addAll(prepaidChargeDetails);
            array.addAll(prepaidChargeDetails2);
            for (Object pcd : array) {
                JSONObject prepaidChargeDetail = (JSONObject) pcd;
                parseFeeIntoList(productFeeItemList, productFeeItem, prepaidChargeDetail, 1);
            }

            JSONArray collectChargeDetails = chargeDetail.getJSONArray("CollectChargeDetails");
            for (Object ccd : collectChargeDetails) {
                JSONObject collectChargeDetail = (JSONObject) ccd;
                parseFeeIntoList(productFeeItemList, productFeeItem, collectChargeDetail, 2);
            }

            getFreeFee(productFeeItemList, productFeeItem, item.getString("traceId"), item.getString("OfferId"));

            getOtherFee3(productFeeItemList, productFeeItem, item.getString("traceId"), item.getString("OfferId"));

        }

    }

    private void parseFeeIntoList(List<ProductFeeItem> productFeeItemList, ProductFeeItem productFeeItem, JSONObject feeItem, int costType)
    {
        JSONObject price = feeItem.getJSONObject("Price");
        BigDecimal amount = price.getBigDecimal("Amount");
        String currency = price.getJSONObject("Currency").getString("Code");
        String chargeName = feeItem.getString("ChargeName");

        productFeeItem.setFeeCostType(costType);

        productFeeItem.setFeeCurrency(parseCurrentCy(currency));
        if ("Export Declaration Surcharge".equalsIgnoreCase(chargeName)) {
            productFeeItem.setPriceComputeType(1);
        } else {
            productFeeItem.setPriceComputeType(0);
        }

        productFeeItem.setPrice(amount);
        productFeeItem.setFeeCnName(StringUtils.isEmpty(chargeName) ? "OceanFreight Basic" : chargeName);
        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());

        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
    }

    private Map<String, String> getHeader()
    {
        JSONObject tokenBean = getToken(this.getHostCode().toLowerCase(), tokenIndex);
        Map<String, String> header = new HashMap<>(3);
        tokenBean.keySet().stream().forEach(key -> {
            header.put(key, tokenBean.getString(key));
        });
        header.put("Host", "del");
        return header;
    }

    public JSONObject getPortInfo(QueryRouteVo queryRouteVo, String portCodeEn, String countryCode)
    {
        String api = "https://www.cma-cgm.com/api/Ports/Get?id=" + portCodeEn + "&manageChinaCountryCode=true";
        try {
            HttpResp resp = HttpUtil.get(api, null);
            String bodyJson = resp.getBodyJson();
            JSONArray arr = JSONObject.parseArray(bodyJson);
            for (Object o : arr) {
                JSONObject item = (JSONObject) o;
                String actualName = item.getString("ActualName");
                String[] split = actualName.split(";");
                String shortName = split[0].split(",")[0];
                if (split[1].trim().equalsIgnoreCase(countryCode) && shortName.trim().equalsIgnoreCase(portCodeEn)) {
                    JSONObject ret = new JSONObject();
                    ret.put("portCode", split[2].trim());
                    return ret;
                }
            }
        } catch (Exception e) {
            log.error(getLogPrefix(queryRouteVo.getSpotId(), this.getHostCode()) + " - 查询港口代码出错,\n" + api);
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
        }
        throw new RuntimeException(getLogPrefix(queryRouteVo.getSpotId(), this.getHostCode()) + " - 网站未查询到港口代码信息: " + portCodeEn + ", 请检查base_port的" + this.getHostCode() + "_code信息");
    }

    private JSONObject getProductStatus(QueryRouteVo queryRouteVo, String loggedId, int solutionNumber, int ScheduleNumber, String offerId, String traceId)
    {
        int count = 0;
        while (count < Constant.MAX_REQ_COUNT) {
            count++;
            try {
                String jsonParam = "{\n" +
                        "  \"LoggedId\": \"" + loggedId + "\",\n" +
                        "  \"NextDepartureSolutionNumber\": " + solutionNumber + ",\n" +
                        "  \"NextDepartureScheduleNumber\": " + ScheduleNumber + ",\n" +
                        "  \"DigitalAllocationsDisplay\": \"infoOnly\",\n" +
                        "  \"OfferId\": \"" + offerId + "\",\n" +
                        "  \"TraceId\": \"" + traceId + "\"\n" +
                        "}";
                HttpResp resp = HttpUtil.postBody("https://www.cma-cgm.com/ebusiness/pricing/getAllocationAndChargeDetails", getHeader(), JSONObject.parseObject(jsonParam).toJSONString());
                Response response = resp.getResponse();
                String bodyJson = resp.getBodyJson();
                JSONObject retObj = JSONObject.parseObject(bodyJson);
                if (response.code() != 200 && response.code() != 201) {
                    tokenIndex++;
                    log.info(getLogPrefix(queryRouteVo.getSpotId(), this.getHostCode()) + " - 第" + count + "次获取费目信息失败\n" + bodyJson);
                } else {
                    log.info(getLogPrefix(queryRouteVo.getSpotId(), this.getHostCode()) + " - 第" + count + "次获取费目信息成功");
                    Boolean aBoolean = retObj.getJSONObject("AllocationResponse").getBoolean("HasAllocation");
                    if (aBoolean) {
                        return retObj;
                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                log.error("获取费目出错");
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        return null;
    }

    public void getFreeFee(List<ProductFeeItem> productFeeItemList, ProductFeeItem productFeeItem, String traceId, String offerId)
    {
        int count = 0;
        while (count < Constant.MAX_REQ_COUNT) {
            count++;
            try {
                String api = "https://www.cma-cgm.com/ebusiness/pricing/getDetentionDemurrageNextDeparture/" + traceId + "/" + offerId;
                HttpResp resp = HttpUtil.get(api, getHeader());
                Response response = resp.getResponse();
                String bodyJson = resp.getBodyJson();
                JSONObject retObj = JSONObject.parseObject(bodyJson);
                if (response.code() != 200 && response.code() != 201) {
                    tokenIndex++;
                    log.info(this.getHostCode() + " - 第" + count + "次获取免箱费信息失败\n" + bodyJson);
                } else {
                    log.info(this.getHostCode() + " - 第" + count + "次获取免箱费信息成功");

                    JSONArray exportDetentionDemurrageDetails = retObj.getJSONArray("ExportDetentionDemurrageDetails");
                    for (Object edd : exportDetentionDemurrageDetails) {
                        JSONObject exportDetentionDemurrageDetail = (JSONObject) edd;
                        productFeeItem.setFeeCostType(5);
                        productFeeItem.setFeeCurrency(2);
                        productFeeItem.setPriceComputeType(0);
                        productFeeItem.setPrice(new BigDecimal(0));
                        productFeeItem.setFeeCnName("ExportDetentionDemurrage " + exportDetentionDemurrageDetail.getJSONObject("TariffType").getString("TariffName") + " " + exportDetentionDemurrageDetail.getString("NoOfFreeDays"));
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());

                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }

                    JSONArray importDetentionDemurrageDetails = retObj.getJSONArray("ImportDetentionDemurrageDetails");
                    for (Object idd : importDetentionDemurrageDetails) {
                        JSONObject importDetentionDemurrageDetail = (JSONObject) idd;
                        productFeeItem.setFeeCostType(5);
                        productFeeItem.setFeeCurrency(2);
                        productFeeItem.setPriceComputeType(0);
                        productFeeItem.setPrice(new BigDecimal(0));
                        productFeeItem.setFeeCnName("ImportDetentionDemurrage " + importDetentionDemurrageDetail.getJSONObject("TariffType").getString("TariffName") + " " + importDetentionDemurrageDetail.getString("NoOfFreeDays"));
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());

                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }
                    break;
                }
            } catch (Exception e) {
                log.error("获取免箱费信息出错");
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }

    }

    public void getOtherFee3(List<ProductFeeItem> productFeeItemList, ProductFeeItem productFeeItem, String traceId, String offerId)
    {
        int count = 0;
        while (count < Constant.MAX_REQ_COUNT) {
            count++;
            try {
                String api = "https://www.cma-cgm.com/ebusiness/pricing/cancellationfees/" + traceId + "/" + offerId;
                HttpResp resp = HttpUtil.get(api, getHeader());
                Response response = resp.getResponse();
                String bodyJson = resp.getBodyJson();
                JSONArray array = JSONObject.parseArray(bodyJson);
                if (response.code() != 200 && response.code() != 201) {
                    tokenIndex++;
                    log.info(this.getHostCode() + " - 第" + count + "次获取取消订单费用失败\n" + bodyJson);
                } else {
                    log.info(this.getHostCode() + " - 第" + count + "次获取取消订单费用成功");

                    for (Object o : array) {
                        JSONObject item = (JSONObject) o;
                        String chargeName = item.getJSONObject("Charge").getString("ChargeName");

                        productFeeItem.setFeeCostType(3);
                        productFeeItem.setFeeCurrency(parseCurrentCy(item.getJSONObject("Currency").getString("Code")));
                        productFeeItem.setPriceComputeType(0);
                        productFeeItem.setPrice(item.getBigDecimal("Amount"));
                        productFeeItem.setFeeCnName(chargeName);
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());

                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }
                    break;
                }
            } catch (Exception e) {
                log.error("获取取消订单费用信息出错");
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }

    }

    public Date parseDate(String dateString)
    {
        String pattern = "\\d+(\\.\\d+)?";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(dateString);

        if (m.find()) {
            return new Date(Long.parseLong(m.group()));
        }
        throw new RuntimeException("时间解析出错: " + dateString);
    }

}
