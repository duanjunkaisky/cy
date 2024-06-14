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
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.djk.core.config.Constant.BUSINESS_NAME_CRAWL;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
@Data
public class CrawlServiceFroCmaImpl extends BaseSimpleCrawlService implements CrawlService {
    private static int reqCount = 0;

    public static final long SLEEP_REQUEST_TIME = 200L;

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

    @Autowired
    CrawlRequestStatusMapper requestStatusMapper;

    public static List<ContainerDist> containerList = new ArrayList<>(3);

    static {
        containerList.add(ContainerDist.builder().flag("1").containerCode("20ST").containerType("22G1").build());
        containerList.add(ContainerDist.builder().flag("2").containerCode("40ST").containerType("42G1").build());
        containerList.add(ContainerDist.builder().flag("3").containerCode("45HC").containerType("45G1").build());
    }

    @Override
    public String queryData(BaseShippingCompany baseShippingCompany, BasePort fromPort, BasePort toPort, QueryRouteVo queryRouteVo) {
        String hostCode = queryRouteVo.getHostCode();
        this.setHostCode(hostCode);

        if (StringUtils.isEmpty(fromPort.getCmaCode()) || StringUtils.isEmpty(toPort.getCmaCode())) {
            throw new RuntimeException("base_port未配置cma_code");
        }

        List<ProductInfo> productInfoList = new ArrayList<>();

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
                    Map<String, String> header = getHeader(queryRouteVo);
                    getRequestToken(header);
                    String cookie = header.get("Cookie");
                    String partnerCode = parsePartnerCode(cookie);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar instance = Calendar.getInstance();
                    instance.setTime(new Date());
                    instance.add(Calendar.DAY_OF_YEAR, 2);
                    Date queryTime = instance.getTime();

                    Map<String, Object> fillData = new HashMap<>(1);
                    fillData.put("fromPortCode", fromPort.getCmaCode());
                    fillData.put("toPortCode", toPort.getCmaCode());
                    fillData.put("containerCode", container.getContainerCode());
                    fillData.put("partnerCode", partnerCode);
                    fillData.put("queryDate", format.format(queryTime));
                    String innerJsonParam = FreeMakerUtil.createByTemplate("real_cmaQuery.ftl", fillData);

                    fillData = new HashMap<>(1);
                    fillData.put("appId", DANLI_ACCESS_KEY);
                    fillData.put("method", "POST");
                    fillData.put("api", "https://www.cma-cgm.com/ebusiness/pricing/getbestoffer");

                    header.put("content-type", "application/json");
                    header.put("Origin", "https://www.cma-cgm.com");
                    fillData.put("header", header);

                    fillData.put("jsonParam", innerJsonParam);
                    fillData.put("timeOut", 15);
                    fillData.put("ip", proxyIp);
                    fillData.put("port", proxyPort);
                    fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                    String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                    addLog(null, BUSINESS_NAME_CRAWL, "发起请求->开始第" + reqCount + "次请求数据接口", jsonParam, queryRouteVo);
                    HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);

//                    TimeUnit.MILLISECONDS.sleep(SLEEP_REQUEST_TIME);
                    bodyJson = resp.getBodyJson();
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                        if (jsonObject.getBoolean("succ")) {
                            String data = jsonObject.getString("data");
                            addLog(null, BUSINESS_NAME_CRAWL, "成功->第" + reqCount + "次请求数据接口", data, queryRouteVo);
                            JSONObject retObj = JSONObject.parseObject(data);
                            JSONArray quoteLineAndRoutingHeaders = retObj.getJSONArray("quoteLineAndRoutingHeaders");
                            if (null == quoteLineAndRoutingHeaders) {
                                addLog(null, BUSINESS_NAME_CRAWL, "失败->第" + reqCount + "次请求数据接口: quoteLineAndRoutingHeaders 为空", bodyJson, queryRouteVo);
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
                            parseData(queryRouteVo, baseShippingCompany, container, offers, fromPort, toPort, productInfoList);
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
                    addLog(null, BUSINESS_NAME_CRAWL, "第" + reqCount + "次发起请求出错", bodyJson + "\n" + ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                    break;
                }
            }
            reqCount = 0;
        }

        return String.valueOf(productInfoList.size());
    }


    private void parseData(QueryRouteVo queryRouteVo, BaseShippingCompany baseShippingCompany, ContainerDist container, JSONArray offers, BasePort fromPort, BasePort toPort, List<ProductInfo> productInfoList) throws ParseException {
        int containerType = computeContainerType(container.getContainerType());

        int index = 1;
        for (Object o : offers) {
            List<ProductFeeItem> productFeeItemList = new ArrayList<>();
            JSONObject item = (JSONObject) o;
            JSONArray routingDetails = item.getJSONArray("RoutingDetails");
            JSONObject departureStart = routingDetails.getJSONObject(0);
            JSONObject departureEnd = routingDetails.getJSONObject(routingDetails.size() - 1);
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

            productInfo.setEstimatedDepartureDate(parseDate(departureStart.getString("DepartureGmtDate"), 0));
            //scheduleDetails有多个就是中转，只有一个就是直达
            productInfo.setRoute(routingDetails.size() == 1 ? 1 : 2);
            productInfo.setCourse(fromPort.getPortNameZh() + " - " + toPort.getPortNameZh());
            productInfo.setArrivalTime(parseDate(departureEnd.getString("ArrivalGmtDate"), 1000 * 60 * 60 * 8));
            productInfo.setProductExpiryDate(parseDate(item.getString("BookingCutoffDate"), 1000 * 60 * 60 * 8));

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

            productInfo.setSpotId(queryRouteVo.getSpotId());

            productInfo.setId(redisService.generateIdCommon("product_info"));

            String key = productInfo.getDeparturePortEn()
                    + productInfo.getDestinationPortEn()
                    + productInfo.getShippingCompanyId()
                    + productInfo.getEstimatedDepartureDate()
                    + productInfo.getShipName()
                    + productInfo.getVoyageNumber();
            String spotPk = DigestUtils.md5DigestAsHex(key.getBytes());
            productInfo.setSpotPk(spotPk);

            productInfoList.add(productInfo);

            productInfoMapper.insertSelective(productInfo);
            addLog(true, BUSINESS_NAME_CRAWL, "第" + index + "条product_info完成入库", null, queryRouteVo);

            delData(queryRouteVo);

            JSONObject fee = item.getJSONObject("fee");
            JSONObject chargeDetail = fee.getJSONArray("ChargeDetails").getJSONObject(0);

            ProductContainer productContainer = new ProductContainer();
            productContainer.setContainerType(containerType);
            productContainer.setProductId(productInfo.getId());
            productContainer.setSpotId(productInfo.getSpotId());
//            productContainer.setSellingPrice(chargeDetail.getJSONObject("TotalCharge").getBigDecimal("Amount"));
//            productContainer.setCost(productContainer.getSellingPrice());
            String currency = chargeDetail.getJSONObject("TotalCharge").getJSONObject("Currency").getString("Code");
            productContainer.setFeeCurrency(parseCurrentCy(currency));
            productContainer.setCreateTime(new Date());
            productContainer.setUpdateTime(new Date());
            productContainer.setDeleted(false);
            productContainer.setTenantId(0L);
            productContainer.setShippingCompanyId(productInfo.getShippingCompanyId());
//            productContainerMapper.insertSelective(productContainer);
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

            JSONObject oceanFreight = chargeDetail.getJSONObject("OceanFreight");
            //基础航运费
            parseFeeIntoList(productFeeItemList, productFeeItem, oceanFreight, 1, productContainer); // 6->1

            JSONArray prepaidChargeDetails = chargeDetail.getJSONArray("PrepaidChargeDetails");
            for (Object pcd : prepaidChargeDetails) {
                JSONObject prepaidChargeDetail = (JSONObject) pcd;
                parseFeeIntoList(productFeeItemList, productFeeItem, prepaidChargeDetail, 5, productContainer); //1->5
            }

            JSONObject blChargeDetail = fee.getJSONObject("BlChargeDetail");
            if (null != blChargeDetail) {
                JSONArray prepaidChargeDetails2 = blChargeDetail.getJSONArray("PrepaidChargeDetails");
                for (Object pcd : prepaidChargeDetails2) {
                    JSONObject prepaidChargeDetail = (JSONObject) pcd;
                    parseFeeIntoList(productFeeItemList, productFeeItem, prepaidChargeDetail, 5, productContainer);//1->5
                }
            }

            JSONArray prepaidChargeDetails3 = chargeDetail.getJSONArray("FreightChargeDetails");
            if (null != prepaidChargeDetails3) {
                for (Object pcd : prepaidChargeDetails3) {
                    JSONObject prepaidChargeDetail = (JSONObject) pcd;
                    parseFeeIntoList(productFeeItemList, productFeeItem, prepaidChargeDetail, 2, productContainer); //1->2
                }
            }

            JSONArray collectChargeDetails = chargeDetail.getJSONArray("CollectChargeDetails");
            if (null != collectChargeDetails) {
                for (Object ccd : collectChargeDetails) {
                    JSONObject collectChargeDetail = (JSONObject) ccd;
                    parseFeeIntoList(productFeeItemList, productFeeItem, collectChargeDetail, 2, productContainer);
                }
            }

            getFreeFee(queryRouteVo, productFeeItemList, productFeeItem, item.getString("traceId"), item.getString("OfferId"), index);

            getOtherFee3(queryRouteVo, productFeeItemList, productFeeItem, item.getString("traceId"), item.getString("OfferId"), index);

            productFeeItemMapper.batchInsert(productFeeItemList);

            productContainer.setCost(productContainer.getSellingPrice());
            productContainerMapper.insertSelective(productContainer);
            productFeeItemMapper.batchInsert(productFeeItemList);

            addLog(true, BUSINESS_NAME_CRAWL, "第" + index + "条product_fee_item完成入库", null, queryRouteVo);
            customDao.executeSql("update crawl_request_status set use_time=" + System.currentTimeMillis() + "-start_time where spot_id='" + queryRouteVo.getSpotId() + "' and host_code='" + queryRouteVo.getHostCode() + "' and (use_time is null or use_time='')");
            index++;
        }

    }

    private void parseFeeIntoList(List<ProductFeeItem> productFeeItemList, ProductFeeItem productFeeItem, JSONObject feeItem, int costType, ProductContainer productContainer) {
        JSONObject price = feeItem.getJSONObject("Price");
        BigDecimal amount = price.getBigDecimal("Amount");
        String currency = price.getJSONObject("Currency").getString("Code");
        String chargeName = feeItem.getString("ChargeName");

        productFeeItem.setFeeCostType(costType);

        productFeeItem.setFeeCurrency(parseCurrentCy(currency));
        if ("Export Declaration Surcharge".equalsIgnoreCase(chargeName)) {
            productFeeItem.setPriceComputeType(1);
            productFeeItem.setFeeUnit(1);
        } else {
            productFeeItem.setPriceComputeType(0);
            productFeeItem.setFeeUnit(0);
        }

        productFeeItem.setPrice(amount);
        productFeeItem.setFeeCnName(StringUtils.isEmpty(chargeName) ? "Bunker surcharge NOS/Ocean Carrier-Intl Ship & port Facility Security" : chargeName);
        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());

        if (costType == 1 || costType == 2) {
            BigDecimal sellingPrice = productContainer.getSellingPrice();
            if (null != sellingPrice && sellingPrice.longValue() > 0) {
                productContainer.setSellingPrice(productContainer.getSellingPrice().add(amount));
            } else {
                productContainer.setSellingPrice(amount);
            }
        }

        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
    }

    private Map<String, String> getHeader(QueryRouteVo queryRouteVo) {
        JSONObject tokenBean = getToken(queryRouteVo);
        Map<String, String> header = new HashMap<>(3);
        tokenBean.keySet().stream().forEach(key -> {
            header.put(key, tokenBean.getString(key));
        });
        return header;
    }

    private JSONObject getProductStatus(QueryRouteVo queryRouteVo, String loggedId, int solutionNumber, int ScheduleNumber, String offerId, String traceId) {
        int count = 0;
        while (count < Constant.MAX_REQ_COUNT) {
            count++;
            try {
                TimeUnit.MILLISECONDS.sleep(SLEEP_REQUEST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, String> header = getHeader(queryRouteVo);
            getRequestToken(header);
            String jsonParam = "{\n" +
                    "  \"LoggedId\": \"" + loggedId + "\",\n" +
                    "  \"NextDepartureSolutionNumber\": " + solutionNumber + ",\n" +
                    "  \"NextDepartureScheduleNumber\": " + ScheduleNumber + ",\n" +
                    "  \"DigitalAllocationsDisplay\": \"infoOnly\",\n" +
                    "  \"OfferId\": \"" + offerId + "\",\n" +
                    "  \"TraceId\": \"" + traceId + "\"\n" +
                    "}";
            try {
                addLog(null, BUSINESS_NAME_CRAWL, "开始发起请求, 第" + count + "次获取售罄+费目信息", jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("https://www.cma-cgm.com/ebusiness/pricing/getAllocationAndChargeDetails", header, JSONObject.parseObject(jsonParam).toJSONString(), MyProxyUtil.getProxy());
                Response response = resp.getResponse();
                String bodyJson = resp.getBodyJson();
                JSONObject retObj = JSONObject.parseObject(bodyJson);
                if (response.code() != 200 && response.code() != 201) {
                    addLog(null, BUSINESS_NAME_CRAWL, "第" + count + "次获取售罄+费目信息失败", bodyJson, queryRouteVo);
                } else {
                    addLog(null, BUSINESS_NAME_CRAWL, "第" + count + "次获取售罄+费目信息成功", bodyJson, queryRouteVo);
                    Boolean aBoolean = retObj.getJSONObject("AllocationResponse").getBoolean("HasAllocation");
                    if (aBoolean) {
                        return retObj;
                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
                addLog(null, BUSINESS_NAME_CRAWL, "第" + count + "次获取售罄+费目信息出错", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
            }
        }
        return null;
    }

    public void getFreeFee(QueryRouteVo queryRouteVo, List<ProductFeeItem> productFeeItemList, ProductFeeItem productFeeItem, String traceId, String offerId, int dataIndex) {
        int count = 0;
        while (count < Constant.MAX_REQ_COUNT) {
            count++;
            try {
                TimeUnit.MILLISECONDS.sleep(SLEEP_REQUEST_TIME);
                Map<String, String> header = getHeader(queryRouteVo);
                getRequestToken(header);
                String api = "https://www.cma-cgm.com/ebusiness/pricing/getDetentionDemurrageNextDeparture/" + traceId + "/" + offerId;
                addLog(null, BUSINESS_NAME_CRAWL, "开始发起请求, 第" + dataIndex + "条数据第" + count + "次获取免箱费信息", api, queryRouteVo);
                HttpResp resp = HttpUtil.get(api, header, MyProxyUtil.getProxy());
                Response response = resp.getResponse();
                String bodyJson = resp.getBodyJson();
                JSONObject retObj = JSONObject.parseObject(bodyJson);
                if (response.code() != 200 && response.code() != 201) {
                    addLog(null, BUSINESS_NAME_CRAWL, "第" + dataIndex + "条数据第" + count + "次获取免箱费信息失败", bodyJson, queryRouteVo);
                } else {
                    addLog(null, BUSINESS_NAME_CRAWL, "第" + dataIndex + "条数据第" + count + "次获取免箱费信息成功", bodyJson, queryRouteVo);
                    JSONArray exportDetentionDemurrageDetails = retObj.getJSONArray("ExportDetentionDemurrageDetails");
                    for (Object edd : exportDetentionDemurrageDetails) {
                        JSONObject exportDetentionDemurrageDetail = (JSONObject) edd;
                        productFeeItem.setFeeCostType(6);
                        productFeeItem.setFeeCurrency(2);
                        productFeeItem.setPriceComputeType(0);
                        productFeeItem.setFeeUnit(0);
                        productFeeItem.setPrice(new BigDecimal(0));
                        productFeeItem.setFeeCnName("ExportDetentionDemurrage " + exportDetentionDemurrageDetail.getJSONObject("TariffType").getString("TariffName") + " " + exportDetentionDemurrageDetail.getString("NoOfFreeDays"));
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());

                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }

                    JSONArray importDetentionDemurrageDetails = retObj.getJSONArray("ImportDetentionDemurrageDetails");
                    for (Object idd : importDetentionDemurrageDetails) {
                        JSONObject importDetentionDemurrageDetail = (JSONObject) idd;
                        productFeeItem.setFeeCostType(6);
                        productFeeItem.setFeeCurrency(2);
                        productFeeItem.setPriceComputeType(0);
                        productFeeItem.setFeeUnit(0);
                        productFeeItem.setPrice(new BigDecimal(0));
                        productFeeItem.setFeeCnName("ImportDetentionDemurrage " + importDetentionDemurrageDetail.getJSONObject("TariffType").getString("TariffName") + " " + importDetentionDemurrageDetail.getString("NoOfFreeDays"));
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());

                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }
                    break;
                }
            } catch (Exception e) {
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
                addLog(null, BUSINESS_NAME_CRAWL, "第" + dataIndex + "条数据第" + count + "次获取免箱费信息出错", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
            }
        }

    }

    public void getOtherFee3(QueryRouteVo queryRouteVo, List<ProductFeeItem> productFeeItemList, ProductFeeItem productFeeItem, String traceId, String offerId, int dataIndex) {
        int count = 0;
        while (count < Constant.MAX_REQ_COUNT) {
            count++;
            try {
                TimeUnit.MILLISECONDS.sleep(SLEEP_REQUEST_TIME);
                Map<String, String> header = getHeader(queryRouteVo);
                getRequestToken(header);
                String api = "https://www.cma-cgm.com/ebusiness/pricing/cancellationfees/" + traceId + "/" + offerId;

                addLog(null, BUSINESS_NAME_CRAWL, "开始发起请求, 第" + dataIndex + "条数据第" + count + "次获取取消订单费用", api, queryRouteVo);

                HttpResp resp = HttpUtil.get(api, header, MyProxyUtil.getProxy());
                Response response = resp.getResponse();
                String bodyJson = resp.getBodyJson();
                JSONArray array = JSONObject.parseArray(bodyJson);
                if (response.code() != 200 && response.code() != 201) {
                    addLog(null, BUSINESS_NAME_CRAWL, "第" + dataIndex + "条数据第" + count + "次获取取消订单费用失败", bodyJson, queryRouteVo);
                } else {
                    addLog(null, BUSINESS_NAME_CRAWL, "第" + dataIndex + "条数据第" + count + "次获取取消订单费用成功", bodyJson, queryRouteVo);

                    for (Object o : array) {
                        JSONObject item = (JSONObject) o;
                        String chargeName = item.getJSONObject("Charge").getString("ChargeName");

                        productFeeItem.setFeeCostType(3);
                        productFeeItem.setFeeCurrency(parseCurrentCy(item.getJSONObject("Currency").getString("Code")));
                        productFeeItem.setPriceComputeType(0);
                        productFeeItem.setFeeUnit(0);
                        productFeeItem.setPrice(item.getBigDecimal("Amount"));
                        productFeeItem.setFeeCnName(chargeName);
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());

                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }
                    break;
                }
            } catch (Exception e) {
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
                addLog(null, BUSINESS_NAME_CRAWL, "第" + dataIndex + "条数据第" + count + "次获取取消订单费用信息出错", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
            }
        }

    }

    public void getRequestToken(Map<String, String> header) {
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        header.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
//        int count = 0;
//        while (count < Constant.MAX_REQ_COUNT) {
//            count++;
//            try {
//                TimeUnit.MILLISECONDS.sleep(SLEEP_REQUEST_TIME);
//                String api = "https://www.cma-cgm.com/ebusiness/pricing/instant-Quoting";
//                HttpResp resp = HttpUtil.get(api, getHeader(), null);
//                Response response = resp.getResponse();
//                String bodyJson = resp.getBodyJson();
//
//                Pattern pattern = Pattern.compile("input name=\"__RequestVerificationToken\" type=\"hidden\" value=\"[a-zA-Z0-9-_~!@#$%^&*+_\\(\\),\\./]+");
//                Matcher matcher = pattern.matcher(bodyJson);
//
//                if (matcher.find()) {
//                    String group = matcher.group();
//                    String requestPageToken = group.replace("input name=\"__RequestVerificationToken\" type=\"hidden\" value=\"", "");
//                    header.put("__RequestVerificationToken", requestPageToken);
//                }
//                String cookie = header.get("Cookie");
//                String newCookie = "";
//                String[] split = cookie.split(";");
//                List<String> headers = response.headers("set-cookie");
//                for (int i = 0; i < split.length; i++) {
//                    String s = split[i];
//                    String key = s.split("=")[0];
//                    boolean exist = false;
//                    for (int j = 0; j < headers.size(); j++) {
//                        String nowValue = headers.get(j);
//                        if (key.equalsIgnoreCase(nowValue.split("=")[0])) {
//                            newCookie += nowValue + ";";
//                            exist = true;
//                            break;
//                        }
//                    }
//                    if (!exist) {
//                        newCookie += s + ";";
//                    }
//                }
//                header.put("Cookie", newCookie);
//            } catch (Exception e) {
//                log.error("获取__RequestToken出错");
//                log.error(ExceptionUtil.getMessage(e));
//                log.error(ExceptionUtil.stacktraceToString(e));
//            }
//        }

    }

    public Date parseDate(String dateString, long gmt) {
        try {
            String pattern = "\\d+(\\.\\d+)?";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(dateString);

            if (m.find()) {
                return new Date(Long.parseLong(m.group()) - gmt);
            }
        } catch (Exception e) {
            log.error("解析时间出错: 时间 -> " + dateString);
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
        }
        return null;
    }

    public String parsePartnerCode(String cookie) {
        String[] split = cookie.split(";");
        List<String> strings = Arrays.asList(split);
        List<String> partnerCode = strings.stream().filter(i -> i.contains("partnerCode")).collect(Collectors.toList());
        String s1 = partnerCode.get(0);
        String decode = null;
        try {
            decode = URLDecoder.decode(s1, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Pattern pattern = Pattern.compile("\"partnerCode\"[, ]+\"[0-9]+\"");
        Matcher matcher = pattern.matcher(decode);

        if (matcher.find()) {
            return matcher.group().split(",")[1].replaceAll("\"", "");
        }
        throw new RuntimeException("get partnerCode failed");
    }

}
