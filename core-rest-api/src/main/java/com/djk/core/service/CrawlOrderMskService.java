package com.djk.core.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.djk.core.config.Constant;
import com.djk.core.model.BasePort;
import com.djk.core.model.BaseShippingCompany;
import com.djk.core.utils.FreeMakerUtil;
import com.djk.core.utils.HttpResp;
import com.djk.core.utils.HttpUtil;
import com.djk.core.utils.MyProxyUtil;
import com.djk.core.vo.ContainerDist;
import com.djk.core.vo.QueryRouteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.djk.core.config.Constant.BUSINESS_NAME_ORDER;

/**
 * @author duanjunkai
 */
@Service
@Slf4j
public class CrawlOrderMskService extends BaseSimpleCrawlService
{
    public static final String consumerKey = "xXIXujnYnqne1yiEnRfIAY7UMAWsbh7J";

    public static List<ContainerDist> containerList = new ArrayList<>(3);

    static {
        containerList.add(ContainerDist.builder().flag("1").containerCode("22G1").containerSize("20").containerType("DRY").build());
        containerList.add(ContainerDist.builder().flag("2").containerCode("42G1").containerSize("40").containerType("DRY").build());
        containerList.add(ContainerDist.builder().flag("3").containerCode("45G1").containerSize("40").containerType("HDRY").build());
    }

    @Override
    public String queryData(BaseShippingCompany shippingCompany, BasePort fromPort, BasePort toPort, QueryRouteVo queryRouteVo) throws Exception
    {
        throw new RuntimeException("抢单服务");
    }

    public void createOrder(QueryRouteVo queryRouteVo)
    {
        BaseShippingCompany baseShippingCompany = getShipCompany(queryRouteVo.getHostCode());
        List<ContainerDist> realList = containerList.stream().filter(i -> i.getFlag().equalsIgnoreCase(queryRouteVo.getContainerType())).collect(Collectors.toList());
        BasePort fromPort = getFromPort(queryRouteVo);
        BasePort toPort = getToPort(queryRouteVo);

        //获取correlationId
        addLog(null, BUSINESS_NAME_ORDER, "开始请求获取编码", null, queryRouteVo);
        String correlationId = getCorrelationId(queryRouteVo, fromPort, toPort, realList.get(0));
        addLog(null, BUSINESS_NAME_ORDER, "获取编码获取成功", correlationId, queryRouteVo);

        addLog(null, BUSINESS_NAME_ORDER, "开始请求nacInfo授权", null, queryRouteVo);
        nacInfo(queryRouteVo, correlationId);
        addLog(null, BUSINESS_NAME_ORDER, "nacInfo授权成功", correlationId, queryRouteVo);

        //获取航班列表
        addLog(null, BUSINESS_NAME_ORDER, "开始获取匹配航班信息", null, queryRouteVo);
        String routeScheduleId = getRouteByList(queryRouteVo, correlationId);
        addLog(null, BUSINESS_NAME_ORDER, "航班信息成功", routeScheduleId, queryRouteVo);

        JSONObject retObj = checkOrder(queryRouteVo, correlationId, routeScheduleId, realList.get(0));
        log.info(retObj.toJSONString());


    }

    private String getCorrelationId(QueryRouteVo queryRouteVo, BasePort fromPort, BasePort toPort, ContainerDist container)
    {
        Map<String, String> header = getRemoteSensorData(queryRouteVo);
        Map<String, Object> fillData = new HashMap<>(1);
        fillData.put("fromPortId", fromPort.getMskCode());
        fillData.put("fromPortCode", fromPort.getMskRkstCode());
        fillData.put("fromPortCountryCode", fromPort.getCountryCode());
        fillData.put("toPortId", toPort.getMskCode());
        fillData.put("toPortCode", toPort.getMskRkstCode());
        fillData.put("toPortCountryCode", toPort.getCountryCode());
        fillData.put("departureDate", queryRouteVo.getDepartureDate());
        fillData.put("containerCode", container.getContainerCode());
        fillData.put("containerSize", container.getContainerSize());
        fillData.put("containerType", container.getContainerType());
        fillData.put("cargoWeight", "18000");
        fillData.put("containerHeight", "8 6");
        String jsonParamInner = FreeMakerUtil.createByTemplate("msk_order_1.ftl", fillData);

        String correlationId = null;
        int reqCount = 0;
        while (null == correlationId && reqCount < Constant.MAX_REQ_COUNT) {
            try {
                String proxy = MyProxyUtil.getProxy();
                addLog(null, BUSINESS_NAME_ORDER, "获取代理ip: " + proxy, null, queryRouteVo);
                String proxyIp = MyProxyUtil.getProxyIp(proxy);
                String proxyPort = MyProxyUtil.getProxyPort(proxy);

                fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "POST");
                fillData.put("api", "https://api.maersk.com/v2/departures");

                fillData.put("header", header);

                fillData.put("jsonParam", jsonParamInner);
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次发起请求-getCorrelationId", jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                if (null != jsonObject && null != jsonObject.getJSONObject("data") && jsonObject.getBoolean("succ")) {
                    correlationId = jsonObject.getJSONObject("data").getString("correlationId");
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次请求-getCorrelationId成功", bodyJson, queryRouteVo);
                    break;
                }
                throw new RuntimeException("第" + (reqCount + 1) + "次获取correlationId失败: " + bodyJson);
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次请求-getCorrelationId出错", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                reqCount++;
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        if (StringUtils.isEmpty(correlationId)) {
            throw new RuntimeException("correlationId为空");
        }
        return correlationId;
    }

    private String getRouteByList(QueryRouteVo queryRouteVo, String correlationId)
    {
        int reqCount = 0;
        int page = 1;
        boolean hasMore = true;
        while (hasMore && reqCount < Constant.MAX_REQ_COUNT) {
            try {
                Map<String, String> header = getRemoteSensorData(queryRouteVo);

                String proxy = MyProxyUtil.getProxy();
                addLog(null, BUSINESS_NAME_ORDER, "获取代理ip: " + proxy, null, queryRouteVo);
                String proxyIp = MyProxyUtil.getProxyIp(proxy);
                String proxyPort = MyProxyUtil.getProxyPort(proxy);

                Map<String, Object> fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "GET");
                fillData.put("api", "https://api.maersk.com/v2/departures/" + correlationId + "?batch=" + page);

                fillData.put("header", header);

                fillData.put("jsonParam", JSONObject.toJSONString(new JSONObject()));
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次发起请求-获取航班,page:" + page, jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                if (null != jsonObject && null != jsonObject.getJSONObject("data") && jsonObject.getBoolean("succ")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次获取航班成功,page:" + page, bodyJson, queryRouteVo);

                    if (page == 1) {
                        validate(queryRouteVo, correlationId, page);
                    }

                    String routeScheduleId = getRouteStatus(queryRouteVo, correlationId, page);
                    if (!StringUtils.isEmpty(routeScheduleId)) {
                        return routeScheduleId;
                    }

                    hasMore = data.getBoolean("moreRoutesAvailable");
                    page++;
                    if (hasMore) {
                        //请求下一页之前先需要校验
                        validate(queryRouteVo, correlationId, page);
                    }
                    reqCount = 0;
                } else {
                    throw new RuntimeException("第" + (reqCount + 1) + "次获取航班失败,page:" + page + "\n" + bodyJson);
                }
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次获取航班失败,page:" + page, ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                reqCount++;
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        throw new RuntimeException("没有获取到符合条件的航班");
    }

    private String getRouteStatus(QueryRouteVo queryRouteVo, String correlationId, int page)
    {
        int reqCount = 0;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= 10000) {
            try {
                Map<String, String> header = getRemoteSensorData(queryRouteVo);

                String proxy = MyProxyUtil.getProxy();
                addLog(null, BUSINESS_NAME_ORDER, "获取代理ip: " + proxy, null, queryRouteVo);
                String proxyIp = MyProxyUtil.getProxyIp(proxy);
                String proxyPort = MyProxyUtil.getProxyPort(proxy);

                Map<String, Object> fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "GET");
                fillData.put("api", "https://api.maersk.com/v2/departures/" + correlationId + "/validated?batch=" + page);

                header.put("Correlationid", correlationId);

                fillData.put("header", header);

                fillData.put("jsonParam", JSONObject.toJSONString(new JSONObject()));
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次发起请求-航班是否可订状态,page:" + page, jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                if (null != jsonObject && null != jsonObject.getJSONObject("data") && jsonObject.getBoolean("succ")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次获取航班状态成功,page:" + page, bodyJson, queryRouteVo);
                    JSONArray validatedRouteSchedules = data.getJSONArray("validatedRouteSchedules");
                    for (int i = 0; i < validatedRouteSchedules.size(); i++) {
                        JSONObject validatedRouteSchedule = (JSONObject) validatedRouteSchedules.get(i);
                        JSONObject routeScheduleAvailability = validatedRouteSchedule.getJSONObject("routeScheduleAvailability");
                        String availabilityStatus = routeScheduleAvailability.getString("availabilityStatus");
                        if ("AVAILABLE".equalsIgnoreCase(availabilityStatus)) {
                            return validatedRouteSchedule.getString("routeScheduleId");
                        }
                    }
                    return null;
                } else {
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次获取航班状态失败,page:" + page, bodyJson, queryRouteVo);
                    reqCount++;
                }
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次获取航班状态失败,page:" + page, ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                reqCount++;
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        return null;
    }

    private void nacInfo(QueryRouteVo queryRouteVo, String correlationId)
    {
        int reqCount = 0;
        while (reqCount < Constant.MAX_REQ_COUNT) {
            try {
                Map<String, String> header = getRemoteSensorData(queryRouteVo);

                String proxy = MyProxyUtil.getProxy();
                addLog(null, BUSINESS_NAME_ORDER, "获取代理ip: " + proxy, null, queryRouteVo);
                String proxyIp = MyProxyUtil.getProxyIp(proxy);
                String proxyPort = MyProxyUtil.getProxyPort(proxy);

                Map<String, Object> fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "PUT");
                fillData.put("api", "https://api.maersk.com/v2/departures/" + correlationId + "/nacInfo");

                header.put("Correlationid", correlationId);
                fillData.put("header", header);

                String json = "{\"nacIndicator\":false,\"nacRoleName\":\"Named Account Customer\",\"nacRoleCode\":45,\"nacAvailable\":false,\"nacHistoryAvailable\":false,\"historicalNacSelected\":false}";
                fillData.put("jsonParam", JSONObject.parseObject(json).toJSONString());
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次发起请求-nacInfo授权", jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                if (null != jsonObject && jsonObject.getBoolean("succ")) {
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次请求-nacInfo授权成功", bodyJson, queryRouteVo);
                    return;
                } else {
                    throw new RuntimeException("第" + (reqCount + 1) + "次请求-nacInfo授权失败" + "\n" + bodyJson);
                }
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次请求-nacInfo授权失败", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                reqCount++;
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        throw new RuntimeException(correlationId + "-> nacInfo授权失败");
    }

    private void validate(QueryRouteVo queryRouteVo, String correlationId, int page)
    {
        int reqCount = 0;
        while (reqCount < Constant.MAX_REQ_COUNT) {
            try {
                Map<String, String> header = getRemoteSensorData(queryRouteVo);

                String proxy = MyProxyUtil.getProxy();
                addLog(null, BUSINESS_NAME_ORDER, "获取代理ip: " + proxy, null, queryRouteVo);
                String proxyIp = MyProxyUtil.getProxyIp(proxy);
                String proxyPort = MyProxyUtil.getProxyPort(proxy);

                Map<String, Object> fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "PUT");
                fillData.put("api", "https://api.maersk.com/v2/departures/" + correlationId + "/batch/" + page + "/validate");

                header.remove("Content-Type");
                fillData.put("header", header);

                fillData.put("jsonParam", JSONObject.toJSONString(new JSONObject()));
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次发起请求-预校验,page:" + page, jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                if (null != jsonObject && jsonObject.getBoolean("succ")) {
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次请求-预校验成功,page:" + page, bodyJson, queryRouteVo);
                    TimeUnit.SECONDS.sleep(1L);
                    return;
                } else {
                    throw new RuntimeException("第" + (reqCount + 1) + "次请求-预校验失败,page:" + page + "\n" + bodyJson);
                }
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次请求-预校验失败,page:" + page, ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                reqCount++;
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        throw new RuntimeException(correlationId + "/" + page + "-> 校验失败");
    }

    private Map<String, String> bookingOptions(QueryRouteVo queryRouteVo, String correlationId, String routeScheduleId)
    {
        int reqCount = 0;
        while (reqCount < Constant.MAX_REQ_COUNT) {
            try {
                Map<String, String> header = getRemoteSensorData(queryRouteVo);

                String proxy = MyProxyUtil.getProxy();
                addLog(null, BUSINESS_NAME_ORDER, "获取代理ip: " + proxy, null, queryRouteVo);
                String proxyIp = MyProxyUtil.getProxyIp(proxy);
                String proxyPort = MyProxyUtil.getProxyPort(proxy);

                Map<String, Object> fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "POST");
                fillData.put("api", "https://api.maersk.com/booking-request/" + correlationId + "/booking-options");

                header.put("Correlationid", correlationId);
                header.put("application-id", "SSIB");
                header.remove("Akamai-Bm-Telemetry");
                fillData.put("header", header);

                String paramJson = "{\"routeScheduleId\":\"" + routeScheduleId + "\",\"productCode\":\"MaerskSpot\",\"isSpotOffer\":true,\"isRollableOptionSelected\":false,\"gcssDeliveryAgreementType\":\"2\",\"isFlexHubOptionSelected\":false,\"retrieveVas\":true,\"retrieveCustomVas\":true,\"retrieveFacility\":true}";
                fillData.put("jsonParam", paramJson);
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次发起请求-bookingOptions", jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                if (null != jsonObject && null != jsonObject.getJSONObject("data") && jsonObject.getBoolean("succ")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次bookingOptions成功", bodyJson, queryRouteVo);
                    JSONArray jsonArray = data.getJSONObject("facilityDetails").getJSONArray("carrierFacilityDetails");
                    Map<String, String> ret = new HashMap<>(8);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject item = (JSONObject) jsonArray.get(i);
                        JSONArray availablePickupDates = item.getJSONArray("availablePickupDates");
                        if (null != availablePickupDates) {
                            ret.put("maerskSiteReferenceId1", item.getString("facilityGeoId"));
                            ret.put("cityGeoId1", item.getString("cityGeoId"));
                            ret.put("siteGeoId1", item.getString("facilityGeoId"));
                            ret.put("haulageStartTimestampLocal1", availablePickupDates.getString(0) + "T07:00:00");
                            ret.put("haulageStartTimestampLocal2", availablePickupDates.getString(availablePickupDates.size() - 1) + "T15:00:00");
                        }
                        if (i == jsonArray.size() - 1) {
                            ret.put("maerskSiteReferenceId2", item.getString("facilityGeoId"));
                            ret.put("cityGeoId2", item.getString("cityGeoId"));
                            ret.put("siteGeoId2", item.getString("facilityGeoId"));
                        }
                    }
                    return ret;
                } else {
                    reqCount++;
                }
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次bookingOptions失败", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                reqCount++;
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        throw new RuntimeException("没有获取到符合条件的航班");
    }

    private JSONObject checkOrder(QueryRouteVo queryRouteVo, String correlationId, String routeScheduleId, ContainerDist container)
    {
        Map<String, String> bookingOptionMap = bookingOptions(queryRouteVo, correlationId, routeScheduleId);
        int reqCount = 0;
        while (reqCount < Constant.MAX_REQ_COUNT) {
            try {
                Map<String, String> header = getRemoteSensorData(queryRouteVo);

                String proxy = MyProxyUtil.getProxy();
                addLog(null, BUSINESS_NAME_ORDER, "获取代理ip: " + proxy, null, queryRouteVo);
                String proxyIp = MyProxyUtil.getProxyIp(proxy);
                String proxyPort = MyProxyUtil.getProxyPort(proxy);

                Map<String, Object> fillData = new HashMap<>(1);
                fillData.put("routeScheduleId", routeScheduleId);
                fillData.put("containerCode", container.getContainerCode());
                fillData.put("containerSize", container.getContainerSize());
                fillData.put("containerType", container.getContainerType());
                fillData.put("cargoWeight", "18000");
                fillData.put("containerHeight", "8 6");

                fillData.putAll(bookingOptionMap);

                String innerJsonParam = FreeMakerUtil.createByTemplate("msk_order_2.ftl", fillData);

                fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "POST");
                fillData.put("api", "https://api.maersk.com/booking-request/" + correlationId + "/additionalDetails");

                header.put("Correlationid", correlationId);
                header.remove("Akamai-Bm-Telemetry");
                fillData.put("header", header);

                fillData.put("jsonParam", JSONObject.parseObject(innerJsonParam).toJSONString());
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次发起请求-下单检查", jsonParam, queryRouteVo);
                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                JSONObject jsonObject = JSONObject.parseObject(bodyJson);
                if (null != jsonObject && null != jsonObject.getJSONObject("data") && jsonObject.getBoolean("succ")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次下单检查成功", bodyJson, queryRouteVo);
                    return data;
                } else {
                    reqCount++;
                }
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_ORDER, "第" + (reqCount + 1) + "次下单检查失败", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                reqCount++;
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        throw new RuntimeException("没有获取到符合条件的航班");
    }

    public Map<String, String> getRemoteSensorData(QueryRouteVo queryRouteVo)
    {
        JSONObject tokenBean = getToken(queryRouteVo);
        Map<String, String> header = new HashMap<>(4);

        redisService.del(REDIS_DATABASE + ":MSK:sensorData");
        redisService.del(REDIS_DATABASE + ":tmp:get-sensorData-api");
        header.put("Authorization", tokenBean.getString("authorization"));
        header.put("Content-Type", "application/json");
        header.put("Akamai-Bm-Telemetry", tokenBean.getString("akamai-bm-telemetry"));
        header.put("Origin", "https://www.maersk.com.cn/");
        header.put("Consumer-Key", consumerKey);
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");
        return header;
    }


}
