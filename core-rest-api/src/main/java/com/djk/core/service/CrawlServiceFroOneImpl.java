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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
@Data
public class CrawlServiceFroOneImpl extends BaseSimpleCrawlService implements CrawlService
{
    private static int reqCount = 0;
    private static int tokenIndex = 0;

    @Autowired
    BasePortMapper basePortMapper;

    @Autowired
    CrawlMetadataWebsiteConfigMapper crawlMetadataWebsiteConfigMapper;

    @Autowired
    CrawlProductInfoMapper productInfoMapper;

    @Autowired
    CrawlProductContainerMapper productContainerMapper;

    @Autowired
    CrawlProductFeeItemMapper productFeeItemMapper;

    public static List<ContainerDist> containerList = new ArrayList<>(3);

    static {
        containerList.add(ContainerDist.builder().containerCode("22G1").containerSize("20").containerType("D2").build());
        containerList.add(ContainerDist.builder().containerCode("42G1").containerSize("40").containerType("D4").build());
        containerList.add(ContainerDist.builder().containerCode("45G1").containerSize("40").containerType("D5").build());
    }

    @Override
    @Transactional
    public void queryData(QueryRouteVo queryRouteVo, String hostCode)
    {
        this.setHostCode(hostCode);
        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 开始爬取数据");
        BasePort fromPort = getFromPort(queryRouteVo);
        BasePort toPort = getToPort(queryRouteVo);
        JSONObject portInfoFrom = getPortInfo(queryRouteVo, fromPort.getOneCode(), fromPort.getCountryCode());
        JSONObject portInfoTo = getPortInfo(queryRouteVo, toPort.getOneCode(), toPort.getCountryCode());

        BaseShippingCompany baseShippingCompany = getShipCompany(hostCode);


        Map<String, CrawlProductInfo> existMap = new HashMap<>();
        List<CrawlProductInfo> productInfoList = new ArrayList<>();
        List<CrawlProductContainer> productContainerList = new ArrayList<>();
        List<CrawlProductFeeItem> productFeeItemList = new ArrayList<>();

        for (ContainerDist container : containerList) {
            while (reqCount < Constant.MAX_REQ_COUNT) {
                try {
                    reqCount++;
                    Map<String, String> header = getHeader();

                    Map<String, Object> fillData = new HashMap<>(1);
                    fillData.put("fromPortCode", portInfoFrom.getString("locationCode"));
                    fillData.put("toPortCode", portInfoTo.getString("locationCode"));
                    fillData.put("containerCode", container.getContainerCode());
                    String jsonParam = FreeMakerUtil.createByTemplate("oneQuery.ftl", fillData);

                    log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求, \n" + "header: " + JSONObject.toJSONString(header) + "\nbody: " + JSONObject.toJSONString(JSONObject.parseObject(jsonParam)));

                    HttpResp resp = HttpUtil.postBody("https://ecomm.one-line.com/api/v1/quotation/schedules/vessel-dates", header, jsonParam);
                    Response response = resp.getResponse();
                    String bodyJson = resp.getBodyJson();
                    JSONObject retObj = JSONObject.parseObject(bodyJson);
                    if (response.code() != 200 && response.code() != 201) {
                        tokenIndex++;
                        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求返回失败\n" + bodyJson);
                        continue;
                    }
                    //code == 201成功
                    log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求返回成功");

                    //开始处理入库
                    JSONArray offers = retObj.getJSONArray("data");
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

        insertData(queryRouteVo, hostCode, productInfoList, productContainerList, productFeeItemList);
    }


    private void parseData(BaseShippingCompany baseShippingCompany, ContainerDist container, JSONArray offers, BasePort fromPort, BasePort toPort, List<CrawlProductInfo> productInfoList, List<CrawlProductContainer> productContainerList, List<CrawlProductFeeItem> productFeeItemList, Map<String, CrawlProductInfo> existMap) throws ParseException
    {
        int containerType = computeContainerType(container.getContainerCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Object o : offers) {
            JSONObject item = (JSONObject) o;
            JSONArray freightInfos = item.getJSONArray("freightInfos");
//            if (null != freightInfos && !freightInfos.isEmpty()) {
            //排除售罄
            if (null != freightInfos && !freightInfos.isEmpty()
                    && !freightInfos.getJSONObject(0).getString("status").equalsIgnoreCase("Sold Out")) {
                JSONObject freightInfo = freightInfos.getJSONObject(0);
                JSONArray departures = freightInfo.getJSONArray("departures");
                JSONObject departureStart = departures.getJSONObject(0);
                JSONObject departureEnd = departures.getJSONObject(departures.size() - 1);

                CrawlProductInfo productInfo = new CrawlProductInfo();
                productInfo.setDeparturePortZh(fromPort.getPortNameZh());
                productInfo.setDeparturePortEn(fromPort.getPortCode());
                productInfo.setDestinationPortZh(toPort.getPortNameZh());
                productInfo.setDestinationPortEn(toPort.getPortCode());
                productInfo.setShippingCompanyId(baseShippingCompany.getId());
                productInfo.setCnFullName(baseShippingCompany.getCnFullName());
                productInfo.setCnAbbreviation(baseShippingCompany.getCnAbbreviation());
                productInfo.setImage(baseShippingCompany.getImage());
                productInfo.setEstimatedDepartureDate(sdf.parse(departureStart.getString("departureDateEstimated")));
                //scheduleDetails有多个就是中转，只有一个就是直达
                productInfo.setRoute(departures.size() == 1 ? 1 : 2);
                productInfo.setCourse(fromPort.getPortNameZh() + " - " + toPort.getPortNameZh());
                productInfo.setArrivalTime(sdf.parse(departureEnd.getString("arrivalDateEstimated")));
                productInfo.setProductExpiryDate(sdf.parse(freightInfo.getString("docCutoff")));

                productInfo.setShipName(freightInfo.getString("transportName"));
                productInfo.setVoyageNumber(freightInfo.getString("conveyanceNumber"));
                productInfo.setDistance(freightInfo.getString("duration"));
                productInfo.setCargoType("G");
                productInfo.setProductType("P");
                productInfo.setWaiverOfContainerInstructions(getFreeMsg(departureStart.getString("departureLoc"), departureEnd.getString("arrivalLoc"), departureEnd.getString("arrivalDateEstimated"), container, freightInfo.getString("spotRateOfferingId")));
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
                    productInfo.setProductNumber(getProductNumber());
                    productInfoList.add(productInfo);
                    existMap.put(existKey, productInfo);
                } else {
                    CrawlProductInfo existProduct = existMap.get(existKey);
                    productInfo.setId(existProduct.getId());
                    productInfo.setProductNumber(existProduct.getProductNumber());
                }

                CrawlProductContainer productContainer = new CrawlProductContainer();
                productContainer.setContainerType(containerType);
                productContainer.setProductId(productInfo.getId());
                productContainer.setSpotId(productInfo.getSpotId());
                productContainer.setSellingPrice(freightInfo.getBigDecimal("price"));
                productContainer.setCost(freightInfo.getBigDecimal("price"));
                productContainer.setCreateTime(new Date());
                productContainer.setUpdateTime(new Date());
                productContainer.setDeleted(false);
                productContainer.setTenantId(0L);
                productContainerList.add(productContainer);

                CrawlProductFeeItem productFeeItem = new CrawlProductFeeItem();
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

                JSONArray basicOceanFreightCharges = freightInfo.getJSONArray("basicOceanFreightCharges");
                for (Object bofc : basicOceanFreightCharges) {
                    JSONObject basicOceanFreightCharge = (JSONObject) bofc;
                    confirmValue(productFeeItem, basicOceanFreightCharge);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), CrawlProductFeeItem.class));
                }

                JSONArray originCharges = freightInfo.getJSONArray("originCharges");
                for (Object oc : originCharges) {
                    JSONObject originCharge = (JSONObject) oc;
                    confirmValue(productFeeItem, originCharge);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), CrawlProductFeeItem.class));
                }

                JSONArray destinationCharges = freightInfo.getJSONArray("destinationCharges");
                for (Object dc : destinationCharges) {
                    JSONObject destinationCharge = (JSONObject) dc;
                    confirmValue(productFeeItem, destinationCharge);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), CrawlProductFeeItem.class));
                }

            }
        }

    }

    private void confirmValue(CrawlProductFeeItem productFeeItem, JSONObject surcharge)
    {
        String chargeTarget = surcharge.getString("chargeTarget");
        if ("ORIGIN".equalsIgnoreCase(chargeTarget)) {
            productFeeItem.setFeeCostType(1);
        } else if ("DESTINATION".equalsIgnoreCase(chargeTarget)) {
            productFeeItem.setFeeCostType(2);
        } else if ("FREIGHT".equalsIgnoreCase(chargeTarget)) {
            //基本海运费
            productFeeItem.setFeeCostType(3);
        }
        String currency = surcharge.getString("chargeCurrency");
        if ("USD".equalsIgnoreCase(currency)) {
            productFeeItem.setFeeCurrency(2);
        } else if ("CNY".equalsIgnoreCase(currency)) {
            productFeeItem.setFeeCurrency(1);
        } else if ("EUR".equalsIgnoreCase(currency)) {
            productFeeItem.setFeeCurrency(3);
        }

        String chargeCode = surcharge.getString("chargeCode");
        if ("DOC".equalsIgnoreCase(chargeCode)) {
            productFeeItem.setPriceComputeType(1);
        } else {
            productFeeItem.setPriceComputeType(0);
        }

        productFeeItem.setPrice(surcharge.getBigDecimal("chargeAmount"));
        productFeeItem.setFeeCnName(surcharge.getString("chargeName"));
        productFeeItem.setFeeEnName(surcharge.getString("chargeName"));
    }


    private Map<String, String> getHeader()
    {
        JSONObject tokenBean = getToken("one", tokenIndex);
        Map<String, String> header = new HashMap<>(3);
        header.put("Authorization", tokenBean.getString("authorization"));
        header.put("Host", "del");
        header.put("User-Agent", "del");
        return header;
    }

    public JSONObject getPortInfo(QueryRouteVo queryRouteVo, String portCodeEn, String countryCode)
    {
        String api = "https://ecomm.one-line.com/api/v1/quotation/locations?location=" + portCodeEn + "&orgDest=origin";
        Map<String, String> header = getHeader();
        try {
            HttpResp resp = HttpUtil.get(api, header);
            String bodyJson = resp.getBodyJson();
            JSONObject retObj = JSONObject.parseObject(bodyJson);
            JSONArray arr = retObj.getJSONArray("data");
            for (Object o : arr) {
                JSONObject item = (JSONObject) o;
                if (checkDisplayedName(item.getString("displayedName"), portCodeEn) && item.getString("countryCode").equalsIgnoreCase(countryCode)) {
                    return item;
                }
            }
        } catch (Exception e) {
            log.error(getLogPrefix(queryRouteVo.getSpotId(), this.getHostCode()) + " - 查询港口代码出错,\n" + api + "\n" + JSONObject.toJSONString(header));
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
        }
        throw new RuntimeException(getLogPrefix(queryRouteVo.getSpotId(), this.getHostCode()) + " - 网站未查询到港口代码信息: " + portCodeEn + ", 请检查base_port的" + this.getHostCode() + "_code信息");
    }

    private boolean checkDisplayedName(String displayedName, String portCodeEn)
    {
        String[] split = displayedName.split(",");
        for (String str : split) {
            if (str.contains(portCodeEn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取标准免费期
     *
     * @return
     */
    public String getFreeMsg(String fromCode, String toCode, String arriveTime, ContainerDist containerDist, String spotRateOfferingId)
    {
        String jsonParam = "{" +
                "    \"originUNLocationCode\": \"" + fromCode + "\",\n" +
                "    \"destinationUNLocationCode\": \"" + toCode + "\",\n" +
                "    \"effectiveDateTime\": \"" + arriveTime + "\",\n" +
                "    \"additionalDays\": 1,\n" +
                "    \"containerCargos\": [\n" +
                "        {\n" +
                "            \"equipmentIsoCode\": \"" + containerDist.getContainerCode() + "\",\n" +
                "            \"equipmentONECntrTpSz\": \"" + containerDist.getContainerType() + "\",\n" +
                "            \"quantity\": 1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"oftCurrency\": \"USD\",\n" +
                "    \"spotRateOfferingId\": \"" + spotRateOfferingId + "\",\n" +
                "    \"isExtendedFreeTime\": false\n" +
                "}";

        try {
            HttpResp resp = HttpUtil.postBody("https://ecomm.one-line.com/api/v1/quotation/demurrage-detention-info", getHeader(), JSONObject.parseObject(jsonParam).toJSONString());
            String bodyJson = resp.getBodyJson();
            JSONObject jsonObject = JSONObject.parseObject(bodyJson);
            JSONObject origin = jsonObject.getJSONObject("origin");
            String ftDys1 = origin.getJSONObject("demurrage").getString("ftDys");
            String ftDys2 = origin.getJSONObject("detention").getString("ftDys");

            JSONObject destination = jsonObject.getJSONObject("destination");
            String ftDys3 = destination.getJSONObject("demurrage").getString("ftDys");
            String ftDys4 = destination.getJSONObject("detention").getString("ftDys");

            String msg = "ONE QUOTE 免箱期信息\n" +
                    "始发地\n" +
                    "合并滞港费和滞箱费 " + Math.max(Integer.parseInt(ftDys1), Integer.parseInt(ftDys2)) + "天\n" +
                    "目的地\n" +
                    "滞期费 " + ftDys3 + "天\n" +
                    "滞期费 " + ftDys4 + "天";
            return msg;
        } catch (Exception e) {
            log.error("查询标准免费期信息出错");
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
        }
        return null;
    }

}
