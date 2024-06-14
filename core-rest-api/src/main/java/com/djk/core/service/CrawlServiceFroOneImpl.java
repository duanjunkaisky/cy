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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.djk.core.config.Constant.BUSINESS_NAME_CRAWL;
import static com.djk.core.config.Constant.BUSINESS_NAME_ORDER;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
@Data
public class CrawlServiceFroOneImpl extends BaseSimpleCrawlService implements CrawlService {
    private static int reqCount = 0;

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

        if (StringUtils.isEmpty(fromPort.getOneCode()) || StringUtils.isEmpty(toPort.getOneCode())) {
            throw new RuntimeException("base_port未配置cma_code");
        }

        String commodityCode = getCommodityCode(queryRouteVo, fromPort, toPort);

        Map<String, ProductInfo> existMap = new HashMap<>();
        List<ProductInfo> productInfoList = new ArrayList<>();
        List<ProductContainer> productContainerList = new ArrayList<>();
        List<ProductFeeItem> productFeeItemList = new ArrayList<>();

        List<ContainerDist> realList = containerList.stream().filter(i -> i.getFlag().equalsIgnoreCase(queryRouteVo.getContainerType())).collect(Collectors.toList());
        for (ContainerDist container : realList) {
            while (reqCount < Constant.MAX_REQ_COUNT) {
                String bodyJson = null;
                try {
                    addLog(null, BUSINESS_NAME_CRAWL, "组织请求参数", null, queryRouteVo);
                    reqCount++;
                    String proxy = MyProxyUtil.getProxy();
                    String proxyIp = MyProxyUtil.getProxyIp(proxy);
                    String proxyPort = MyProxyUtil.getProxyPort(proxy);


                    addLog(null, BUSINESS_NAME_CRAWL, "获取代理ip: " + proxy, null, queryRouteVo);

                    Map<String, Object> fillData = new HashMap<>(1);
                    fillData.put("fromPortCode", fromPort.getOneCode());
                    fillData.put("toPortCode", toPort.getOneCode());
                    fillData.put("containerCode", container.getContainerCode());
                    fillData.put("commodityCode", commodityCode);
                    String innerJsonParam = FreeMakerUtil.createByTemplate("real_oneQuery.ftl", fillData);

                    fillData = new HashMap<>(1);
                    fillData.put("appId", DANLI_ACCESS_KEY);
                    fillData.put("method", "POST");
                    fillData.put("api", "https://ecomm.one-line.com/api/v1/quotation/schedules/vessel-dates");

                    Map<String, String> header = getHeader(queryRouteVo);
                    header.put("content-type", "application/json");
                    header.put("Origin", "https://ecomm.one-line.com");
                    fillData.put("header", header);

                    fillData.put("jsonParam", innerJsonParam);
                    fillData.put("timeOut", 15);
                    fillData.put("ip", proxyIp);
                    fillData.put("port", proxyPort);
                    fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                    String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                    addLog(null, BUSINESS_NAME_CRAWL, "发起请求->开始第" + reqCount + "次请求数据接口", jsonParam, queryRouteVo);
                    HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                    bodyJson = resp.getBodyJson();

                    try {
                        JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                        if (jsonObject.getBoolean("succ")) {
                            String data = jsonObject.getString("data");
                            addLog(null, BUSINESS_NAME_CRAWL, "成功->第" + reqCount + "次请求数据接口", data, queryRouteVo);
                            JSONObject retObj = JSONObject.parseObject(data);

                            //开始处理入库
                            JSONArray offers = retObj.getJSONArray("data");
                            if (null == offers || offers.isEmpty()) {
                                addLog(null, BUSINESS_NAME_CRAWL, "成功->第" + reqCount + "次请求数据接口返回未空", data, queryRouteVo);
                            } else {
                                parseData(queryRouteVo, baseShippingCompany, container, offers, fromPort, toPort, productInfoList, productContainerList, productFeeItemList, existMap, proxy);
                            }
                            break;
                        } else {
                            addLog(null, BUSINESS_NAME_CRAWL, "鉴权失败->第" + reqCount + "次请求数据接口", bodyJson, queryRouteVo);
                            continue;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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

        int index = 1;
        for (Object o : offers) {
            JSONObject item = (JSONObject) o;
            JSONArray freightInfos = item.getJSONArray("freightInfos");
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
                productInfo.setId(redisService.generateIdCommon("product_info"));
                productInfoList.add(productInfo);

                productInfoMapper.insertSelective(productInfo);

                addLog(true, BUSINESS_NAME_CRAWL, "第" + index + "条product_info完成入库", null, queryRouteVo);

                delData(queryRouteVo);

                ProductContainer productContainer = new ProductContainer();
                productContainer.setContainerType(containerType);
                productContainer.setProductId(productInfo.getId());
                productContainer.setSpotId(productInfo.getSpotId());

//                BigDecimal totalPrice = item.getBigDecimal("totalPrice");
//                BigDecimal totalPricePremium = item.getBigDecimal("totalPricePremium");
//                productContainer.setSellingPrice(totalPrice.add(totalPricePremium));
//                productContainer.setCost(freightInfo.getBigDecimal("price"));
                String currency = item.getJSONArray("currencies").getString(0);
                productContainer.setFeeCurrency(parseCurrentCy(currency));
                productContainer.setCreateTime(new Date());
                productContainer.setUpdateTime(new Date());
                productContainer.setDeleted(false);
                productContainer.setTenantId(0L);
                productContainer.setShippingCompanyId(productInfo.getShippingCompanyId());
                productContainerList.add(productContainer);
//                productContainerMapper.insertSelective(productContainer);
                addLog(true, BUSINESS_NAME_CRAWL, "第" + index + "条product_container完成入库", null, queryRouteVo);

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
                    confirmValue(productFeeItem, basicOceanFreightCharge, productContainer);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray originCharges = freightInfo.getJSONArray("originCharges");
                for (Object oc : originCharges) {
                    JSONObject originCharge = (JSONObject) oc;
                    confirmValue(productFeeItem, originCharge, productContainer);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray premiumCharges = freightInfo.getJSONArray("premiumCharges");
                for (Object oc : premiumCharges) {
                    JSONObject premiumCharge = (JSONObject) oc;
                    confirmValue(productFeeItem, premiumCharge, productContainer);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray freightCharges = freightInfo.getJSONArray("freightCharges");
                for (Object oc : freightCharges) {
                    JSONObject freightCharge = (JSONObject) oc;
                    confirmValue(productFeeItem, freightCharge, productContainer);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray destinationCharges = freightInfo.getJSONArray("destinationCharges");
                for (Object dc : destinationCharges) {
                    JSONObject destinationCharge = (JSONObject) dc;
                    confirmValue(productFeeItem, destinationCharge, productContainer);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                getFreeFee(queryRouteVo, productFeeItemList, productFeeItem, departureStart.getString("departureLoc"), departureEnd.getString("arrivalLoc"), departureEnd.getString("arrivalDateEstimated"), container, freightInfo.getString("spotRateOfferingId"), proxy);

                productFeeItemMapper.batchInsert(productFeeItemList);

                productContainer.setCost(productContainer.getSellingPrice());
                productContainerMapper.insertSelective(productContainer);
                productFeeItemMapper.batchInsert(productFeeItemList);
                addLog(true, BUSINESS_NAME_CRAWL, "第" + index + "条product_fee_item完成入库", null, queryRouteVo);

                customDao.executeSql("update crawl_request_status set use_time=" + System.currentTimeMillis() + "-start_time where spot_id='" + queryRouteVo.getSpotId() + "' and host_code='" + queryRouteVo.getHostCode() + "' and (use_time is null or use_time='')");
            } else {
                addLog(true, BUSINESS_NAME_CRAWL, "第" + index + "条product_info为售罄,忽略", null, queryRouteVo);
            }
            index++;
        }

    }

    private void confirmValue(ProductFeeItem productFeeItem, JSONObject surcharge, ProductContainer productContainer) {
        String chargeTarget = surcharge.getString("chargeTarget");
        if ("ORIGIN".equalsIgnoreCase(chargeTarget)) {
            productFeeItem.setFeeCostType(2);
        } else if ("DESTINATION".equalsIgnoreCase(chargeTarget)) {
            productFeeItem.setFeeCostType(5);
        } else if ("FREIGHT".equalsIgnoreCase(chargeTarget)) {
            //基本海运费
            productFeeItem.setFeeCostType(1); //6-1
        }
        String currency = surcharge.getString("chargeCurrency");
        int cy = parseCurrentCy(currency);
        productFeeItem.setFeeCurrency(cy);

        String chargeCode = surcharge.getString("chargeCode");
        if ("DOC".equalsIgnoreCase(chargeCode)) {
            productFeeItem.setPriceComputeType(1);
            productFeeItem.setFeeUnit(1);
        } else {
            productFeeItem.setPriceComputeType(0);
            productFeeItem.setFeeUnit(0);
        }

        int costType = productFeeItem.getFeeCostType();
        if (costType == 1 || costType == 2) {
            BigDecimal sellingPrice = productContainer.getSellingPrice();
            if (null != sellingPrice && sellingPrice.longValue() > 0) {
                productContainer.setSellingPrice(productContainer.getSellingPrice().add(surcharge.getBigDecimal("chargeAmount")));
            } else {
                productContainer.setSellingPrice(surcharge.getBigDecimal("chargeAmount"));
            }
        }

        productFeeItem.setPrice(surcharge.getBigDecimal("chargeAmount"));
        productFeeItem.setFeeCnName(surcharge.getString("chargeName"));
        productFeeItem.setFeeEnName(surcharge.getString("chargeName"));
    }

    private Map<String, String> getHeader(QueryRouteVo queryRouteVo) {
        JSONObject tokenBean = getToken(queryRouteVo);
        Map<String, String> header = new HashMap<>(3);
        tokenBean.keySet().stream().forEach(key -> {
            header.put(key, tokenBean.getString(key).replaceAll("\"", "\\\\\""));
        });
        return header;
    }

    private String getCommodityCode(QueryRouteVo queryRouteVo, BasePort fromPort, BasePort toPort) {
        String commodityCode = null;
        int reqCount = 0;
        while (null == commodityCode && reqCount < Constant.MAX_REQ_COUNT) {
            try {
                Map<String, String> header = getHeader(queryRouteVo);
                String proxy = MyProxyUtil.getProxy();
                addLog(null, BUSINESS_NAME_ORDER, "获取代理ip: " + proxy, null, queryRouteVo);
                String proxyIp = MyProxyUtil.getProxyIp(proxy);
                String proxyPort = MyProxyUtil.getProxyPort(proxy);

                Map<String, Object> fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "POST");
                fillData.put("api", "https://ecomm.one-line.com/api/v1/quotation/commodity");

                fillData.put("header", header);

                fillData.put("jsonParam", FreeMakerUtil.createByTemplate("real_oneQuery_step1.ftl", fillData));
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次发起请求-getCommodityCode", jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                if (null != jsonObject && null != jsonObject.getJSONObject("data") && jsonObject.getBoolean("succ")) {
                    commodityCode = jsonObject.getJSONObject("data").getJSONArray("data").getJSONObject(0).getString("commodityCode");
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次请求-getCommodityCode成功", bodyJson, queryRouteVo);
                    break;
                }
                throw new RuntimeException("第" + (reqCount + 1) + "次获取getCommodityCode失败: " + bodyJson);
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次请求-getCommodityCode出错", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                reqCount++;
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        if (StringUtils.isEmpty(commodityCode)) {
            throw new RuntimeException("getCommodityCode为空");
        }
        return commodityCode;
    }

    /**
     * 获取标准免费期
     *
     * @return
     */
    public String getFreeFee(QueryRouteVo queryRouteVo, List<ProductFeeItem> productFeeItemList, ProductFeeItem productFeeItem, String fromCode, String toCode, String arriveTime, ContainerDist containerDist, String spotRateOfferingId, String proxy) {
        int reqCount = 0;
        while (reqCount < Constant.MAX_REQ_COUNT) {
            reqCount++;
            try {
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
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount) + "次发起请求-标准免费期信息", jsonParam, queryRouteVo);
                    HttpResp resp = HttpUtil.postBody("https://ecomm.one-line.com/api/v1/quotation/demurrage-detention-info", getHeader(queryRouteVo), JSONObject.parseObject(jsonParam).toJSONString(), proxy);
                    String bodyJson = resp.getBodyJson();
                    JSONObject jsonObject = JSONObject.parseObject(bodyJson);

                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount) + "次请求-标准免费期信息成功", jsonParam, queryRouteVo);

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
                    productFeeItem.setFeeUnit(0);
                    productFeeItem.setFeeCostType(6);

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
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount) + "次请求-标准免费期信息出错出错", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        return null;
    }

}
