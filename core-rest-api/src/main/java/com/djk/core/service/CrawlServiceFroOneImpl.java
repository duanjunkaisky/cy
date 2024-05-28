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
import com.djk.core.utils.MyProxyUtil;
import com.djk.core.vo.ContainerDist;
import com.djk.core.vo.QueryRouteVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
@Data
public class CrawlServiceFroOneImpl extends BaseSimpleCrawlService implements CrawlService {
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
        containerList.add(ContainerDist.builder().flag("1").containerCode("22G1").containerSize("20").containerType("D2").build());
        containerList.add(ContainerDist.builder().flag("2").containerCode("42G1").containerSize("40").containerType("D4").build());
        containerList.add(ContainerDist.builder().flag("3").containerCode("45G1").containerSize("40").containerType("D5").build());
    }

    @Override
    public String queryData(BaseShippingCompany baseShippingCompany, BasePort fromPort, BasePort toPort, QueryRouteVo queryRouteVo) {
        String hostCode = queryRouteVo.getHostCode();
        this.setHostCode(hostCode);
        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 开始爬取数据, ip: " + null);

        Map<String, ProductInfo> existMap = new HashMap<>();
        List<ProductInfo> productInfoList = new ArrayList<>();
        List<ProductContainer> productContainerList = new ArrayList<>();
        List<ProductFeeItem> productFeeItemList = new ArrayList<>();

        List<ContainerDist> realList = containerList.stream().filter(i -> i.getFlag().equalsIgnoreCase(queryRouteVo.getContainerType())).collect(Collectors.toList());
        for (ContainerDist container : realList) {
            String proxy = MyProxyUtil.getProxy();
            while (reqCount < Constant.MAX_REQ_COUNT) {
                try {
                    reqCount++;
                    Map<String, Object> fillData = new HashMap<>(1);
                    fillData.put("fromPortCode", fromPort.getOneCode());
                    fillData.put("toPortCode", toPort.getOneCode());
                    fillData.put("containerCode", container.getContainerCode());
                    String jsonParam = FreeMakerUtil.createByTemplate("real_oneQuery.ftl", fillData);

                    log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次发起请求, \nbody: " + JSONObject.toJSONString(JSONObject.parseObject(jsonParam)));

                    TimeUnit.MILLISECONDS.sleep(500L);
                    HttpResp resp = HttpUtil.postBody("https://ecomm.one-line.com/api/v1/quotation/schedules/vessel-dates", getHeader(), jsonParam, proxy);
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
                    if (null == offers || offers.isEmpty()) {
                        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + reqCount + "次 请求获取返回未空");
                    } else {
                        parseData(queryRouteVo, baseShippingCompany, container, offers, fromPort, toPort, productInfoList, productContainerList, productFeeItemList, existMap, proxy);
                    }
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

        return String.valueOf(productInfoList.size());
    }

    private void parseData(QueryRouteVo queryRouteVo, BaseShippingCompany baseShippingCompany, ContainerDist container, JSONArray offers, BasePort fromPort, BasePort toPort, List<ProductInfo> productInfoList, List<ProductContainer> productContainerList, List<ProductFeeItem> productFeeItemList, Map<String, ProductInfo> existMap, String proxy) throws ParseException {
        int containerType = computeContainerType(container.getContainerCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Object o : offers) {
            JSONObject item = (JSONObject) o;
            JSONArray freightInfos = item.getJSONArray("freightInfos");
//            if (null != freightInfos && !freightInfos.isEmpty()) {
            //排除售罄
            if (null != freightInfos && !freightInfos.isEmpty()
                    && !freightInfos.getJSONObject(0).getString("status").equalsIgnoreCase("Sold Out")) {

                productFeeItemList = new ArrayList<>();
                JSONObject freightInfo = freightInfos.getJSONObject(0);
                JSONArray departures = freightInfo.getJSONArray("departures");
                JSONObject departureStart = departures.getJSONObject(0);
                JSONObject departureEnd = departures.getJSONObject(departures.size() - 1);

                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductNumber(getProductNumber());
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
                productInfo.setWaiverOfContainerInstructions("无说明");
                productInfo.setDeficitFreightInstructions("无说明");
                productInfo.setReviewStatus(3);
                productInfo.setStatus((byte) 0);
                productInfo.setCreateTime(new Date());
                productInfo.setUpdateTime(new Date());
                productInfo.setDeleted(false);
                productInfo.setTenantId(0L);

                productInfo.setSpotId(queryRouteVo.getSpotId());

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
                productInfoMapper.insertSelective(productInfo);

                ProductContainer productContainer = new ProductContainer();
                productContainer.setContainerType(containerType);
                productContainer.setProductId(productInfo.getId());
                productContainer.setSpotId(productInfo.getSpotId());

                BigDecimal totalPrice = item.getBigDecimal("totalPrice");
                BigDecimal totalPricePremium = item.getBigDecimal("totalPricePremium");
                productContainer.setSellingPrice(totalPrice.add(totalPricePremium));
                productContainer.setCost(freightInfo.getBigDecimal("price"));
                String currency = item.getJSONArray("currencies").getString(0);
                productContainer.setFeeCurrency(parseCurrentCy(currency));
                productContainer.setCreateTime(new Date());
                productContainer.setUpdateTime(new Date());
                productContainer.setDeleted(false);
                productContainer.setTenantId(0L);
                productContainer.setShippingCompanyId(productInfo.getShippingCompanyId());
                productContainerList.add(productContainer);
                productContainerMapper.insertSelective(productContainer);

                ProductFeeItem productFeeItem = new ProductFeeItem();
                productFeeItem.setShippingCompanyId(productInfo.getShippingCompanyId());
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
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray originCharges = freightInfo.getJSONArray("originCharges");
                for (Object oc : originCharges) {
                    JSONObject originCharge = (JSONObject) oc;
                    confirmValue(productFeeItem, originCharge);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray premiumCharges = freightInfo.getJSONArray("premiumCharges");
                for (Object oc : premiumCharges) {
                    JSONObject premiumCharge = (JSONObject) oc;
                    confirmValue(productFeeItem, premiumCharge);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray freightCharges = freightInfo.getJSONArray("freightCharges");
                for (Object oc : freightCharges) {
                    JSONObject freightCharge = (JSONObject) oc;
                    confirmValue(productFeeItem, freightCharge);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray destinationCharges = freightInfo.getJSONArray("destinationCharges");
                for (Object dc : destinationCharges) {
                    JSONObject destinationCharge = (JSONObject) dc;
                    confirmValue(productFeeItem, destinationCharge);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                getFreeFee(productFeeItemList, productFeeItem, departureStart.getString("departureLoc"), departureEnd.getString("arrivalLoc"), departureEnd.getString("arrivalDateEstimated"), container, freightInfo.getString("spotRateOfferingId"), proxy);

                productFeeItemMapper.batchInsert(productFeeItemList);

                customDao.executeSql("update crawl_request_status set use_time=" + System.currentTimeMillis() + "-start_time where spot_id='" + queryRouteVo.getSpotId() + "' and host_code='" + queryRouteVo.getHostCode() + "' and (use_time is null or use_time='')");
            }
        }

    }

    private void confirmValue(ProductFeeItem productFeeItem, JSONObject surcharge) {
        String chargeTarget = surcharge.getString("chargeTarget");
        if ("ORIGIN".equalsIgnoreCase(chargeTarget)) {
            productFeeItem.setFeeCostType(1);
        } else if ("DESTINATION".equalsIgnoreCase(chargeTarget)) {
            productFeeItem.setFeeCostType(2);
        } else if ("FREIGHT".equalsIgnoreCase(chargeTarget)) {
            //基本海运费
            productFeeItem.setFeeCostType(6);
        }
        String currency = surcharge.getString("chargeCurrency");
        int cy = parseCurrentCy(currency);
        productFeeItem.setFeeCurrency(cy);

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

    private Map<String, String> getHeader() {
        JSONObject tokenBean = getToken(this.getHostCode().toLowerCase(), tokenIndex++);
        Map<String, String> header = new HashMap<>(3);
        header.put("Authorization", tokenBean.getString("authorization"));
        header.put("Host", "del");
        header.put("User-Agent", "del");
        return header;
    }

    public JSONObject getPortInfo(QueryRouteVo queryRouteVo, String portCodeEn, String countryCode, String proxy) {
        int count = 0;
        while (count < Constant.MAX_REQ_COUNT) {
            String api = "https://ecomm.one-line.com/api/v1/quotation/locations?location=" + portCodeEn + "&orgDest=origin";
            Map<String, String> header = getHeader();
            try {
                TimeUnit.MILLISECONDS.sleep(500L);
                HttpResp resp = HttpUtil.get(api, header, proxy);
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
            count++;
        }
        throw new RuntimeException(getLogPrefix(queryRouteVo.getSpotId(), this.getHostCode()) + "查询港口代码出错： ");
    }

    private boolean checkDisplayedName(String displayedName, String portCodeEn) {
        String[] split = displayedName.split(",");
        for (String str : split) {
            if (str.toLowerCase().contains(portCodeEn.toLowerCase())) {
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
    public String getFreeFee(List<ProductFeeItem> productFeeItemList, ProductFeeItem productFeeItem, String fromCode, String toCode, String arriveTime, ContainerDist containerDist, String spotRateOfferingId, String proxy) {
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
            TimeUnit.MILLISECONDS.sleep(1000L);
            HttpResp resp = HttpUtil.postBody("https://ecomm.one-line.com/api/v1/quotation/demurrage-detention-info", getHeader(), JSONObject.parseObject(jsonParam).toJSONString(), proxy);
            String bodyJson = resp.getBodyJson();
            JSONObject jsonObject = JSONObject.parseObject(bodyJson);
            JSONObject origin = jsonObject.getJSONObject("origin");
            String ftDys1 = origin.getJSONObject("demurrage").getString("ftDys");
            String ftDys1Price = origin.getJSONObject("demurrage").getJSONArray("additionalAmount").getJSONObject(0).getString("pricePerUnit");
            String currentCy1 = origin.getJSONObject("demurrage").getJSONArray("additionalAmount").getJSONObject(0).getString("currency");
            String ftDys2 = origin.getJSONObject("detention").getString("ftDys");
            String ftDys2Price = origin.getJSONObject("detention").getJSONArray("additionalAmount").getJSONObject(0).getString("pricePerUnit");
            String currentCy2 = origin.getJSONObject("detention").getJSONArray("additionalAmount").getJSONObject(0).getString("currency");

            JSONObject destination = jsonObject.getJSONObject("destination");
            String ftDys3 = destination.getJSONObject("demurrage").getString("ftDys");
            String ftDys3Price = destination.getJSONObject("demurrage").getJSONArray("additionalAmount").getJSONObject(0).getString("pricePerUnit");
            String currentCy3 = destination.getJSONObject("demurrage").getJSONArray("additionalAmount").getJSONObject(0).getString("currency");
            String ftDys4 = destination.getJSONObject("detention").getString("ftDys");
            String ftDys4Price = destination.getJSONObject("detention").getJSONArray("additionalAmount").getJSONObject(0).getString("pricePerUnit");
            String currentCy4 = destination.getJSONObject("detention").getJSONArray("additionalAmount").getJSONObject(0).getString("currency");

            String msg = "ONE QUOTE 免箱期信息\n" +
                    "始发地\n" +
                    "合并滞港费和滞箱费 " + Math.max(Integer.parseInt(ftDys1), Integer.parseInt(ftDys2)) + "天\n" +
                    "目的地\n" +
                    "滞期费 " + ftDys3 + "天\n" +
                    "滞期费 " + ftDys4 + "天";
            productFeeItem.setPriceComputeType(0);
            productFeeItem.setFeeCostType(5);

            productFeeItem.setPrice(new BigDecimal(0));
            productFeeItem.setFeeCnName("origin demurrage (1-" + ftDys1 + ")");
            productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
            productFeeItem.setFeeCurrency(parseCurrentCy(currentCy1));
            productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

            productFeeItem.setPrice(new BigDecimal(ftDys1Price));
            productFeeItem.setFeeCnName("origin demurrage (" + ftDys1 + "+)");
            productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
            productFeeItem.setFeeCurrency(parseCurrentCy(currentCy1));
            productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

            productFeeItem.setPrice(new BigDecimal(0));
            productFeeItem.setFeeCnName("origin detention (1-" + ftDys2 + ")");
            productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
            productFeeItem.setFeeCurrency(parseCurrentCy(currentCy2));
            productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

            productFeeItem.setPrice(new BigDecimal(ftDys2Price));
            productFeeItem.setFeeCnName("origin detention (" + ftDys2 + "+)");
            productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
            productFeeItem.setFeeCurrency(parseCurrentCy(currentCy2));
            productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

            productFeeItem.setPrice(new BigDecimal(0));
            productFeeItem.setFeeCnName("destination demurrage (1-" + ftDys3 + ")");
            productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
            productFeeItem.setFeeCurrency(parseCurrentCy(currentCy3));
            productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

            productFeeItem.setPrice(new BigDecimal(ftDys3Price));
            productFeeItem.setFeeCnName("destination demurrage (" + ftDys3 + "+)");
            productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
            productFeeItem.setFeeCurrency(parseCurrentCy(currentCy3));
            productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

            productFeeItem.setPrice(new BigDecimal(0));
            productFeeItem.setFeeCnName("destination detention (1-" + ftDys4 + ")");
            productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
            productFeeItem.setFeeCurrency(parseCurrentCy(currentCy4));
            productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

            productFeeItem.setPrice(new BigDecimal(ftDys4Price));
            productFeeItem.setFeeCnName("destination detention (" + ftDys4 + "+)");
            productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
            productFeeItem.setFeeCurrency(parseCurrentCy(currentCy4));
            productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

            return msg;
        } catch (Exception e) {
            log.error("查询标准免费期信息出错");
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
        }
        return null;
    }

}
