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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.djk.core.config.Constant.BUSINESS_NAME_CRAWL;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
@Data
public class CrawlServiceFroMskImpl extends BaseSimpleCrawlService implements CrawlService
{
    private final int WEEK_STEP = 2;

    private static int reqCount = 0;

    public static final String DANLI_ACCESS_KEY = "eyqq4t1ubp4fbjklkrguol6zcc8o5jp5";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisService redisService;

    @Value("${redis.database}")
    public String REDIS_DATABASE;

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
        containerList.add(ContainerDist.builder().flag("1").containerCode("22G1").containerSize("20").containerType("DRY").build());
        containerList.add(ContainerDist.builder().flag("2").containerCode("42G1").containerSize("40").containerType("DRY").build());
        containerList.add(ContainerDist.builder().flag("3").containerCode("45G1").containerSize("40").containerType("HDRY").build());
    }

    @Override
    public String queryData(BaseShippingCompany baseShippingCompany, BasePort fromPort, BasePort toPort, QueryRouteVo queryRouteVo)
    {
        String hostCode = queryRouteVo.getHostCode();
        this.setHostCode(hostCode);
        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 开始爬取数据, ip: ");

        List<ProductInfo> productInfoList = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DAY_OF_YEAR, 2);
        Date queryTime = instance.getTime();

        List<ContainerDist> realList = containerList.stream().filter(i -> i.getFlag().equalsIgnoreCase(queryRouteVo.getContainerType())).collect(Collectors.toList());
        boolean hasMore = true;
        int page = 1;
        redisService.del(REDIS_DATABASE + ":MSK:sensorData");
        redisService.del(REDIS_DATABASE + ":tmp:get-sensorData-api");
        while (hasMore) {
            hasMore = getDataPerPage(queryRouteVo, fromPort, toPort, baseShippingCompany, productInfoList, format, queryTime, realList.get(0), page);
            page++;
        }
        return String.valueOf(productInfoList.size());
    }

    private boolean getDataPerPage(QueryRouteVo queryRouteVo, BasePort fromPort, BasePort toPort, BaseShippingCompany baseShippingCompany, List<ProductInfo> productInfoList, SimpleDateFormat format, Date queryTime, ContainerDist container, int page)
    {
        reqCount = 0;
        String hostCode = queryRouteVo.getHostCode();
        while (reqCount < Constant.MAX_REQ_COUNT) {
            try {
                addLog(null, BUSINESS_NAME_CRAWL, "组织请求参数", null, queryRouteVo);
                reqCount++;
                String proxy = MyProxyUtil.getProxy();
                String proxyIp = null;
                String proxyPort = null;
                if (!StringUtils.isEmpty(proxy) && proxy.split(":").length == 2) {
                    String[] split = proxy.split(":");
                    proxyIp = split[0];
                    proxyPort = split[1];
                } else {
                    log.error("获取代理失败!");
                    break;
                }
                addLog(null, BUSINESS_NAME_CRAWL, "获取代理ip: " + proxy, null, queryRouteVo);

                Map<String, String> header = getRemoteSensorData(queryRouteVo);
                Map<String, Object> fillData = new HashMap<>(1);
                fillData.put("fromPortId", fromPort.getMskCode());
                fillData.put("fromPortCode", fromPort.getMskRkstCode());
                fillData.put("fromPortCountry", fromPort.getCountryCode());
                fillData.put("fromPortFullName", checkPortName(fromPort.getPortCode()) + " (" + checkPortName(fromPort.getStateEn()) + "), " + checkPortName(fromPort.getCountryNameEn()));

                fillData.put("toPortId", toPort.getMskCode());
                fillData.put("toPortCode", toPort.getMskRkstCode());
                fillData.put("toPortCountry", toPort.getCountryCode());
                fillData.put("toPortFullName", checkPortName(toPort.getPortCode()) + " (" + checkPortName(toPort.getStateEn()) + "), " + checkPortName(toPort.getCountryNameEn()));

                fillData.put("customerCode", header.get("customerCode"));
                header.remove("customerCode");
                fillData.put("containerCode", container.getContainerCode());
                fillData.put("containerSize", container.getContainerSize());
                fillData.put("containerType", container.getContainerType());

                fillData.put("queryDate", format.format(queryTime));
                fillData.put("weekOffset", (page - 1) * WEEK_STEP);
                String jsonParamInner = FreeMakerUtil.createByTemplate("real_mskQuery.ftl", fillData);

                fillData = new HashMap<>(1);
                fillData.put("appId", DANLI_ACCESS_KEY);
                fillData.put("method", "POST");
                fillData.put("api", "https://api.maersk.com/productoffer/v2/productoffers");
                fillData.put("telemetry", header.get("Akamai-Bm-Telemetry"));
                fillData.put("authorization", header.get("Authorization"));
                fillData.put("consumerKey", header.get("Consumer-Key"));
                fillData.put("jsonParam", jsonParamInner.replaceAll("\"", "\\\\\"").replaceAll("\n", "").replaceAll(" ", "").replaceAll("\t", ""));
                fillData.put("timeOut", 15);
                fillData.put("ip", proxyIp);
                fillData.put("port", proxyPort);
                fillData.put("password", MyProxyUtil.PROXY_USERNAME + ":" + MyProxyUtil.PROXY_PASSWORD);
                String jsonParam = FreeMakerUtil.createByTemplate("danli_req.ftl", fillData);

                log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + page + "页," + reqCount + "次发起请求, \n" + "header: " + JSONObject.toJSONString(header) + "\nbody: " + JSONObject.toJSONString(JSONObject.parseObject(jsonParam)));

                addLog(null, BUSINESS_NAME_CRAWL, "请求数据接口-分页:" + page, "第" + reqCount + "次请求", queryRouteVo);
//                HttpResp resp = HttpUtil.postBody("http://localhost:8899/py/proxy", null, jsonParam, null);
                HttpResp resp = HttpUtil.postBody("http://api.zjdanli.com/akamai/tls/proxy", null, jsonParam, null);
                String bodyJson = resp.getBodyJson();
                try {
                    if (bodyJson.contains("Customer Segment limit for customer code")) {
                        addLog(null, BUSINESS_NAME_CRAWL, "发生错误->请求数据接口-分页:" + page, "第" + reqCount + "次请求: " + "Customer Segment limit for customer code", queryRouteVo);
                        continue;
                    } else {
                        JSONObject jsonObject = JSONObject.parseObject(bodyJson);
//                        if (jsonObject.getIntValue("code") == 200) {
                        if (jsonObject.getBoolean("succ")) {
                            addLog(null, BUSINESS_NAME_CRAWL, "成功->请求数据接口-分页:" + page, "第" + reqCount + "次请求", queryRouteVo);
                            String data = jsonObject.getString("data");
                            JSONObject retObj = JSONObject.parseObject(data);
                            boolean hasMore = retObj.getBoolean("loadMore");
                            log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + page + "页," + reqCount + "次发起请求返回成功, hasMore: " + hasMore);

                            JSONArray offers = retObj.getJSONArray("offers");
                            parseData(queryRouteVo, baseShippingCompany, container, offers, fromPort, toPort, productInfoList, page);

                            return hasMore;
                        } else {
                            addLog(null, BUSINESS_NAME_CRAWL, "鉴权失败->请求数据接口-分页:" + page, "第" + reqCount + "次请求: " + "发起请求鉴权失败", queryRouteVo);
                            redisService.del(REDIS_DATABASE + ":MSK:sensorData");
                            redisService.del(REDIS_DATABASE + ":tmp:get-sensorData-api");
                            log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + page + "页," + reqCount + "次发起请求鉴权失败\n");
                            continue;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                redisService.del(REDIS_DATABASE + ":MSK:sensorData");
                redisService.del(REDIS_DATABASE + ":tmp:get-sensorData-api");

                addLog(null, BUSINESS_NAME_CRAWL, "鉴权失败->请求数据接口-分页:" + page, "第" + reqCount + "次请求:\n" + ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);

                log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 第" + page + "页," + reqCount + "次发起请求出错");
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        }
        return false;
    }

    private void parseData(QueryRouteVo queryRouteVo, BaseShippingCompany baseShippingCompany, ContainerDist container, JSONArray offers, BasePort fromPort, BasePort toPort, List<ProductInfo> productInfoList, int page) throws ParseException
    {
        int containerType = computeContainerType(container.getContainerCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int index = 1;
        for (Object o : offers) {
            List<ProductFeeItem> productFeeItemList = new ArrayList<>();
            JSONObject item = (JSONObject) o;
            JSONObject productOffer = item.getJSONObject("productOffer");
            if (!StringUtils.isEmpty(productOffer)) {
                JSONObject routeScheduleWithPrices = item.getJSONObject("routeScheduleWithPrices");
                JSONObject routeScheduleFull = routeScheduleWithPrices.getJSONObject("routeScheduleFull");
                JSONObject price = routeScheduleWithPrices.getJSONObject("price");
                JSONObject fromLocation = routeScheduleFull.getJSONObject("fromLocation");
                JSONObject toLocation = routeScheduleFull.getJSONObject("toLocation");
                JSONArray scheduleDetails = routeScheduleFull.getJSONArray("scheduleDetails");
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
                String departureDateStr = fromLocation.getString("date") + " " + fromLocation.getString("time");
                productInfo.setEstimatedDepartureDate(sdf.parse(departureDateStr));
                //scheduleDetails有多个就是中转，只有一个就是直达
                productInfo.setRoute(scheduleDetails.size() == 1 ? 1 : 2);
                productInfo.setCourse(fromPort.getPortNameZh() + " - " + toPort.getPortNameZh());
                String arrivalDateStr = toLocation.getString("date") + " " + toLocation.getString("time");
                productInfo.setArrivalTime(sdf.parse(arrivalDateStr));
                for (Object sd : scheduleDetails) {
                    JSONObject schedule = (JSONObject) sd;
                    JSONArray deadlines = schedule.getJSONArray("deadlines");
                    if (null != deadlines && deadlines.size() > 0) {
                        JSONObject deadlineObj = deadlines.getJSONObject(0);
                        String deadline = deadlineObj.getString("deadline");
                        productInfo.setProductExpiryDate(sdf.parse(deadline));
                    }
                }
                JSONObject vessel = routeScheduleFull.getJSONObject("vessel");
                productInfo.setShipName(vessel.getString("name"));
                productInfo.setVoyageNumber(routeScheduleFull.getString("voyageNumber"));
                long diff = Math.abs(productInfo.getArrivalTime().getTime() - productInfo.getEstimatedDepartureDate().getTime());
                long diffDays = diff / (24 * 60 * 60 * 1000);
                productInfo.setDistance(diff % (24 * 60 * 60 * 1000) == 0 ? String.valueOf(diffDays) : String.valueOf(diffDays + 1));
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
                productInfo.setId(Generator.nextId());
                productInfoList.add(productInfo);
                productInfoMapper.insertSelective(productInfo);
                addLog(true, BUSINESS_NAME_CRAWL, "第" + page + "页,第" + index + "条product_info完成入库", null, queryRouteVo);

                delData(queryRouteVo);

                ProductContainer productContainer = new ProductContainer();
                productContainer.setContainerType(containerType);
                productContainer.setProductId(productInfo.getId());
                productContainer.setSpotId(productInfo.getSpotId());
//                productContainer.setSellingPrice(price.getBigDecimal("total"));
//                productContainer.setCost(price.getBigDecimal("total"));
                productContainer.setFeeCurrency(parseCurrentCy(price.getString("totalPriceCurrency")));
                productContainer.setCreateTime(new Date());
                productContainer.setUpdateTime(new Date());
                productContainer.setDeleted(false);
                productContainer.setTenantId(0L);
                productContainer.setShippingCompanyId(productInfo.getShippingCompanyId());
                //运费重新在fee中计算
                addLog(true, BUSINESS_NAME_CRAWL, "第" + page + "页,第" + index + "条product_container完成入库", null, queryRouteVo);

                ProductFeeItem productFeeItem = new ProductFeeItem();
                productFeeItem.setShippingCompanyId(productInfo.getShippingCompanyId());
                productFeeItem.setContainerType(productContainer.getContainerType());
                productFeeItem.setSpotId(productInfo.getSpotId());
                productFeeItem.setProductId(productInfo.getId());
                //1箱；2柜
                productFeeItem.setFeeUnit(1);
                productFeeItem.setFeeSource(1);
                productFeeItem.setCreateTime(new Date());
                productFeeItem.setUpdateTime(new Date());
                productFeeItem.setDeleted(false);
                productFeeItem.setTenantId(0L);

                JSONArray surchargesPerDocs = price.getJSONArray("surcharges_per_doc");
                for (int i = 0; i < surchargesPerDocs.size(); i++) {
                    JSONObject jsonObject = surchargesPerDocs.getJSONObject(i);
                    confirmValue(productFeeItem, jsonObject, productContainer);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }

                JSONArray pricesPerContainer = price.getJSONArray("prices_per_container");
                for (Object ppc : pricesPerContainer) {
                    JSONObject jsonObject = (JSONObject) ppc;
                    JSONObject bas = jsonObject.getJSONObject("bas");
                    confirmValue(productFeeItem, bas, productContainer);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

                    JSONArray surcharges = jsonObject.getJSONArray("surcharges_per_container");
                    for (Object sc : surcharges) {
                        JSONObject surcharge = (JSONObject) sc;
                        confirmValue(productFeeItem, surcharge, productContainer);
                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }
                }

                JSONArray penaltyFees = routeScheduleWithPrices.getJSONArray("penaltyFees");
                for (Object pf : penaltyFees) {
                    JSONObject penaltyFee = (JSONObject) pf;
                    String currency = penaltyFee.getString("currency");

                    int cy = parseCurrentCy(currency);
                    productFeeItem.setFeeCurrency(cy);

                    productFeeItem.setPriceComputeType(1);
                    //亏仓费
                    productFeeItem.setFeeCostType(4);

                    JSONArray charges = penaltyFee.getJSONArray("charges");
                    for (Object cg : charges) {
                        JSONObject charge = (JSONObject) cg;
                        productFeeItem.setPrice(charge.getBigDecimal("chargeFee"));
                        productFeeItem.setFeeCnName(charge.getString("displayName"));
                        productFeeItem.setFeeEnName(charge.getString("displayName"));
                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }
                }

                JSONArray importDnDConditions = routeScheduleWithPrices.getJSONArray("importDnDConditions");
                for (Object idc : importDnDConditions) {
                    JSONObject importDnDCondition = (JSONObject) idc;
                    String chargeType = importDnDCondition.getString("chargeType");
                    String freetimeStartEvent = importDnDCondition.getString("freetimeStartEvent");

                    productFeeItem.setPriceComputeType(0);
                    //免用箱
                    productFeeItem.setFeeCostType(6);

                    productFeeItem.setPrice(new BigDecimal(0));
                    productFeeItem.setFeeCnName(chargeType + " " + freetimeStartEvent + "(1-" + importDnDCondition.getString("freetimeGrantInDays") + ")");
                    productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));

                    JSONArray chargePerDiemAfterFreetime = importDnDCondition.getJSONArray("chargePerDiemAfterFreetime");
                    for (Object cdaf : chargePerDiemAfterFreetime) {
                        JSONObject cpa = (JSONObject) cdaf;
                        String chargePerDiem = cpa.getString("chargePerDiem");
                        String currencyOfCharge = cpa.getString("currencyOfCharge");
                        String startDayOfCharge = cpa.getString("startDayOfCharge");
                        String endDayOfCharge = cpa.getString("endDayOfCharge");

                        int cy = parseCurrentCy(currencyOfCharge);
                        productFeeItem.setFeeCurrency(cy);

                        productFeeItem.setPrice(new BigDecimal(chargePerDiem));
                        productFeeItem.setFeeCnName(chargeType + " " + freetimeStartEvent + "(" + startDayOfCharge + (StringUtils.isEmpty(endDayOfCharge) ? "+" : ("-" + endDayOfCharge)) + ")");
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }
                }

                productContainer.setCost(productContainer.getSellingPrice());
                productContainerMapper.insertSelective(productContainer);
                productFeeItemMapper.batchInsert(productFeeItemList);
                addLog(true, BUSINESS_NAME_CRAWL, "第" + page + "页,第" + index + "条product_fee_item完成入库", null, queryRouteVo);

                customDao.executeSql("update crawl_request_status set use_time=" + System.currentTimeMillis() + "-start_time where spot_id='" + queryRouteVo.getSpotId() + "' and host_code='" + queryRouteVo.getHostCode() + "' and (use_time is null or use_time='')");
            } else {
                addLog(true, BUSINESS_NAME_CRAWL, "第" + page + "页,第" + index + "条product_info为售罄,忽略", null, queryRouteVo);
            }
            index++;
        }

    }

    private void confirmValue(ProductFeeItem productFeeItem, JSONObject surcharge, ProductContainer productContainer)
    {
        BigDecimal price = surcharge.getBigDecimal("rate");
        String ratetypecode = surcharge.getString("ratetypecode");
        if ("Origin".equalsIgnoreCase(ratetypecode)) {
            productFeeItem.setFeeCostType(2);
        } else if ("Destination".equalsIgnoreCase(ratetypecode)) {
            productFeeItem.setFeeCostType(5);
        } else if ("Freight".equalsIgnoreCase(ratetypecode)) {
            //基础运费
            productFeeItem.setFeeCostType(1);
            BigDecimal sellingPrice = productContainer.getSellingPrice();
            if (null != sellingPrice && sellingPrice.longValue() > 0) {
                productContainer.setSellingPrice(productContainer.getSellingPrice().add(price));
            } else {
                productContainer.setSellingPrice(price);
            }
        }
        String currency = surcharge.getString("currency");

        int cy = parseCurrentCy(currency);
        productFeeItem.setFeeCurrency(cy);

        String ratebasis = surcharge.getString("ratebasis");
        if ("PER_DOC".equalsIgnoreCase(ratebasis)) {
            productFeeItem.setPriceComputeType(1);
        } else {
            productFeeItem.setPriceComputeType(0);
        }
        productFeeItem.setPrice(price);
        productFeeItem.setFeeCnName(surcharge.getString("chargedescription"));
        productFeeItem.setFeeEnName(surcharge.getString("chargedescription"));
    }

    public Map<String, String> getRemoteSensorData(QueryRouteVo queryRouteVo)
    {
        JSONObject tokenBean = getToken(queryRouteVo);

        String userAgent = null;
        Map<String, String> header = new HashMap<>(4);
        String sensorData = (String) redisService.get(REDIS_DATABASE + ":MSK:sensorData");
        if (StringUtils.isEmpty(sensorData)) {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_DATABASE + ":tmp:get-sensorData-api", 1, 60L, TimeUnit.SECONDS);
            if (aBoolean) {
                addLog(null, BUSINESS_NAME_CRAWL, "开始获取akamai指纹", null, queryRouteVo);
                String abck = tokenBean.getString("_abck");
                String bmsz = tokenBean.getString("bm_sz");
                Map<String, String> sensorDataParams = new HashMap<>(4);
                sensorDataParams.put("appid", "eyqq4t1ubp4fbjklkrguol6zcc8o5jp5");
                sensorDataParams.put("siteUrl", "https://www.maersk.com.cn/book");
                sensorDataParams.put("abck", abck);
                sensorDataParams.put("bmsz", bmsz);
                Long aLong = redisService.generateId("sensorData_count");
                HttpResp resp = HttpUtil.postBody("http://api.zjdanli.com/akamai/v2/sensorData", null, JSONObject.toJSONString(sensorDataParams), null);
                JSONObject retObj = JSONObject.parseObject(resp.getBodyJson());
                userAgent = retObj.getString("ua");
                sensorData = retObj.getString("sensorData");
                addLog(null, BUSINESS_NAME_CRAWL, "成功得到akamai指纹", sensorData, queryRouteVo);
                sensorData = Base64.getEncoder().encodeToString(sensorData.getBytes());
                log.info(getLogPrefix(queryRouteVo.getSpotId(), this.getHostCode()) + " - 第" + aLong + "次获取sensorData:\n" + sensorData);
                redisService.set(REDIS_DATABASE + ":MSK:sensorData", sensorData, 300L);
            } else {
                long startTime = System.currentTimeMillis();
                while (StringUtils.isEmpty(sensorData) && System.currentTimeMillis() - startTime < 20 * 1000) {
                    sensorData = (String) redisService.get(REDIS_DATABASE + ":MSK:sensorData");
                }
            }
        }
        String str = tokenBean.getString("akamai-bm-telemetry");
        String start = str.split("sensor_data=")[0];
        String akaSign = start + "sensor_data=" + sensorData;

        header.put("Authorization", tokenBean.getString("authorization"));
        header.put("Content-Type", "application/json");
        if (!StringUtils.isEmpty(userAgent)) {
            header.put("User-Agent", userAgent);
        }
        header.put("Consumer-Key", tokenBean.getString("consumer-key"));
        header.put("Akamai-Bm-Telemetry", akaSign);
        header.put("customerCode", tokenBean.getString("customerCode"));

        return header;
    }

}
