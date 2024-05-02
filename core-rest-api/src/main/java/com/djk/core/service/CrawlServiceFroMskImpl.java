package com.djk.core.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.djk.core.mapper.*;
import com.djk.core.model.*;
import com.djk.core.utils.FreeMakerUtil;
import com.djk.core.utils.HttpResp;
import com.djk.core.utils.HttpUtil;
import com.djk.core.vo.ContainerDist;
import com.djk.core.vo.QueryRouteVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
public class CrawlServiceFroMskImpl extends BaseSimpleCrawlService implements CrawlService
{
    private final int WEEK_STEP = 2;
    private static final int MAX_REQ_COUNT = 3;

    private static int reqCount = 0;
    private static int tokenIndex = 0;
    private static String sensorData;
    private static String userAgent;

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
        containerList.add(ContainerDist.builder().containerCode("22G1").containerSize("20").containerType("DRY").build());
        containerList.add(ContainerDist.builder().containerCode("42G1").containerSize("40").containerType("DRY").build());
        containerList.add(ContainerDist.builder().containerCode("45G1").containerSize("40").containerType("HDRY").build());
    }

    @Override
    @Transactional
    public String queryData(QueryRouteVo queryRouteVo)
    {
        BasePort fromPort = getFromPort(queryRouteVo);
        BasePort toPort = getToPort(queryRouteVo);
        JSONObject portInfoFrom = getPortInfo(queryRouteVo.getDeparturePortEn(), fromPort.getCountryCode());
        JSONObject portInfoTo = getPortInfo(queryRouteVo.getDestinationPortEn(), toPort.getCountryCode());

        BaseShippingCompanyExample baseShippingCompanyExample = new BaseShippingCompanyExample();
        baseShippingCompanyExample.createCriteria().andEnAbbreviationEqualTo("MSK");
        List<BaseShippingCompany> baseShippingCompanies = shippingCompanyMapper.selectByExample(baseShippingCompanyExample);
        if (null == baseShippingCompanies || baseShippingCompanies.isEmpty()) {
            throw new RuntimeException("base_ship_company未配置MSK信息");
        }
        BaseShippingCompany baseShippingCompany = baseShippingCompanies.get(0);


        Map<String, CrawlProductInfo> existMap = new HashMap<>();
        List<CrawlProductInfo> productInfoList = new ArrayList<>();
        List<CrawlProductContainer> productContainerList = new ArrayList<>();
        List<CrawlProductFeeItem> productFeeItemList = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DAY_OF_YEAR, 2);
        Date queryTime = instance.getTime();

        for (ContainerDist container : containerList) {
            boolean hasMore = true;
            int page = 1;
            while (hasMore) {
                if (reqCount >= MAX_REQ_COUNT) {
                    reqCount = 0;
                    throw new RuntimeException("爬取重试次数大于" + MAX_REQ_COUNT);
                }
                try {
                    Map<String, String> header = getRemoteSensorData();
                    Map<String, Object> data = new HashMap<>(1);
                    data.put("fromPortId", portInfoFrom.getString("maerskGeoLocationId"));
                    data.put("fromPortCode", portInfoFrom.getString("maerskRkstCode"));
                    data.put("fromPortCountry", fromPort.getCountryCode());
                    data.put("fromPortFullName", portInfoFrom.getString("maerskRkstCode") + " (" + portInfoFrom.getString("maerskRkstCode") + "), " + portInfoFrom.getString("maerskRkstCode"));

                    data.put("toPortId", portInfoTo.getString("maerskGeoLocationId"));
                    data.put("toPortCode", portInfoTo.getString("maerskRkstCode"));
                    data.put("toPortCountry", toPort.getCountryCode());
                    data.put("toPortFullName", portInfoTo.getString("maerskRkstCode") + " (" + portInfoTo.getString("maerskRkstCode") + "), " + portInfoTo.getString("maerskRkstCode"));

                    data.put("containerCode", container.getContainerCode());
                    data.put("containerSize", container.getContainerSize());
                    data.put("containerType", container.getContainerType());

                    data.put("queryDate", format.format(queryTime));
                    data.put("weekOffset", (page - 1) * WEEK_STEP);
                    String jsonParam = FreeMakerUtil.createByTemplate("mskQuery.ftl", data);
                    HttpResp resp = HttpUtil.postBody("https://api.maersk.com/productoffer/v2/productoffers", header, jsonParam);
                    Response response = resp.getResponse();
                    String bodyJson = resp.getBodyJson();
                    if (response.code() != 200) {
                        if (response.code() == 403) {
                            sensorData = null;
                        } else if (response.code() == 401) {
                            tokenIndex++;
                        }
                        continue;
                    }

                    JSONObject retObj = JSONObject.parseObject(bodyJson);
                    hasMore = retObj.getBoolean("loadMore");
                    page++;

                    //开始处理入库
                    JSONArray offers = retObj.getJSONArray("offers");
                    parseData(baseShippingCompany, container, offers, fromPort, toPort, productInfoList, productContainerList, productFeeItemList, existMap);
                    reqCount = 0;
                } catch (Exception e) {
                    reqCount = 0;
                    throw new RuntimeException("爬取数据出错", e);
                }
            }
            reqCount = 0;
        }

        if (!productInfoList.isEmpty()) {
            String spotId = productInfoList.get(0).getSpotId();
            CrawlProductInfoExample crawlProductInfoExample = new CrawlProductInfoExample();
            crawlProductInfoExample.createCriteria().andSpotIdEqualTo(spotId);
            productInfoMapper.deleteByExample(crawlProductInfoExample);

            CrawlProductContainerExample crawlProductContainerExample = new CrawlProductContainerExample();
            crawlProductContainerExample.createCriteria().andSpotIdEqualTo(spotId);
            productContainerMapper.deleteByExample(crawlProductContainerExample);

            CrawlProductFeeItemExample crawlProductFeeItemExample = new CrawlProductFeeItemExample();
            crawlProductFeeItemExample.createCriteria().andSpotIdEqualTo(spotId);
            productFeeItemMapper.deleteByExample(crawlProductFeeItemExample);

            productInfoMapper.batchInsert(productInfoList);
            productContainerMapper.batchInsert(productContainerList);
            productFeeItemMapper.batchInsert(productFeeItemList);

        }
        return null;
    }

    private void parseData(BaseShippingCompany baseShippingCompany, ContainerDist container, JSONArray offers, BasePort fromPort, BasePort toPort, List<CrawlProductInfo> productInfoList, List<CrawlProductContainer> productContainerList, List<CrawlProductFeeItem> productFeeItemList, Map<String, CrawlProductInfo> existMap) throws ParseException
    {
        int containerType = computeContainerType(container.getContainerCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Object o : offers) {
            JSONObject item = (JSONObject) o;
            JSONObject productOffer = item.getJSONObject("productOffer");
            if (!StringUtils.isEmpty(productOffer)) {
                JSONObject routeScheduleWithPrices = item.getJSONObject("routeScheduleWithPrices");
                JSONObject routeScheduleFull = routeScheduleWithPrices.getJSONObject("routeScheduleFull");
                JSONObject price = routeScheduleWithPrices.getJSONObject("price");
                JSONObject fromLocation = routeScheduleFull.getJSONObject("fromLocation");
                JSONObject toLocation = routeScheduleFull.getJSONObject("toLocation");
                JSONArray scheduleDetails = routeScheduleFull.getJSONArray("scheduleDetails");
                CrawlProductInfo productInfo = new CrawlProductInfo();
                productInfo.setDeparturePortZh(fromPort.getPortNameZh());
                productInfo.setDeparturePortEn(productOffer.getString("originCityName"));
                productInfo.setDestinationPortZh(toPort.getPortNameZh());
                productInfo.setDestinationPortEn(productOffer.getString("destinationCityName"));
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
                productInfo.setDistance(String.valueOf(diffDays));
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

                String spotIdStr = productInfo.getDeparturePortEn()
                        + productInfo.getDestinationPortEn()
                        + productInfo.getShippingCompanyId();
                String spotId = DigestUtils.md5DigestAsHex(spotIdStr.getBytes());
                productInfo.setSpotId(spotId);

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
                productContainer.setSellingPrice(price.getBigDecimal("total"));
                productContainer.setCost(price.getBigDecimal("total"));
                productContainer.setCreateTime(new Date());
                productContainer.setUpdateTime(new Date());
                productContainer.setDeleted(false);
                productContainer.setTenantId(0L);
                productContainerList.add(productContainer);

                CrawlProductFeeItem productFeeItem = new CrawlProductFeeItem();
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

                JSONArray pricesPerContainer = price.getJSONArray("prices_per_container");
                for (Object ppc : pricesPerContainer) {
                    JSONObject jsonObject = (JSONObject) ppc;
                    JSONObject bas = jsonObject.getJSONObject("bas");
                    confirmValue(productFeeItem, bas);
                    productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), CrawlProductFeeItem.class));

                    JSONArray surcharges = jsonObject.getJSONArray("surcharges_per_container");
                    for (Object sc : surcharges) {
                        JSONObject surcharge = (JSONObject) sc;
                        confirmValue(productFeeItem, surcharge);
                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), CrawlProductFeeItem.class));
                    }
                }

                JSONArray penaltyFees = routeScheduleWithPrices.getJSONArray("penaltyFees");
                for (Object pf : penaltyFees) {
                    JSONObject penaltyFee = (JSONObject) pf;
                    String currency = penaltyFee.getString("currency");
                    if ("USD".equalsIgnoreCase(currency)) {
                        productFeeItem.setFeeCurrency(2);
                    } else if ("CNY".equalsIgnoreCase(currency)) {
                        productFeeItem.setFeeCurrency(1);
                    } else if ("EUR".equalsIgnoreCase(currency)) {
                        productFeeItem.setFeeCurrency(3);
                    }
                    productFeeItem.setPriceComputeType(1);
                    productFeeItem.setFeeCostType(3);

                    JSONArray charges = penaltyFee.getJSONArray("charges");
                    for (Object cg : charges) {
                        JSONObject charge = (JSONObject) cg;
                        productFeeItem.setPrice(charge.getBigDecimal("chargeFee"));
                        productFeeItem.setFeeCnName(charge.getString("displayName"));
                        productFeeItem.setFeeEnName(charge.getString("displayName"));
                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), CrawlProductFeeItem.class));
                    }
                }

                JSONArray importDnDConditions = routeScheduleWithPrices.getJSONArray("importDnDConditions");
                for (Object idc : importDnDConditions) {
                    JSONObject importDnDCondition = (JSONObject) idc;
                    String chargeType = importDnDCondition.getString("chargeType");
                    String freetimeStartEvent = importDnDCondition.getString("freetimeStartEvent");
                    JSONArray chargePerDiemAfterFreetime = importDnDCondition.getJSONArray("chargePerDiemAfterFreetime");
                    for (Object cdaf : chargePerDiemAfterFreetime) {
                        JSONObject cpa = (JSONObject) cdaf;
                        String chargePerDiem = cpa.getString("chargePerDiem");
                        String currencyOfCharge = cpa.getString("currencyOfCharge");
                        String startDayOfCharge = cpa.getString("startDayOfCharge");
                        String endDayOfCharge = cpa.getString("endDayOfCharge");

                        if ("USD".equalsIgnoreCase(currencyOfCharge)) {
                            productFeeItem.setFeeCurrency(2);
                        } else if ("CNY".equalsIgnoreCase(currencyOfCharge)) {
                            productFeeItem.setFeeCurrency(1);
                        } else if ("EUR".equalsIgnoreCase(currencyOfCharge)) {
                            productFeeItem.setFeeCurrency(3);
                        }
                        productFeeItem.setPriceComputeType(0);
                        productFeeItem.setFeeCostType(3);

                        productFeeItem.setPrice(new BigDecimal(chargePerDiem));
                        productFeeItem.setFeeCnName(chargeType + " " + freetimeStartEvent + "(" + startDayOfCharge + (StringUtils.isEmpty(endDayOfCharge) ? "+" : ("-" + endDayOfCharge)) + ")");
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
                        productFeeItemList.add(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), CrawlProductFeeItem.class));
                    }
                }
            }
        }

    }

    private void confirmValue(CrawlProductFeeItem productFeeItem, JSONObject surcharge)
    {
        String ratetypecode = surcharge.getString("ratetypecode");
        if ("Origin".equalsIgnoreCase(ratetypecode)) {
            productFeeItem.setFeeCostType(1);
        } else if ("Destination".equalsIgnoreCase(ratetypecode)) {
            productFeeItem.setFeeCostType(2);
        } else if ("Freight".equalsIgnoreCase(ratetypecode)) {
            //运费
            productFeeItem.setFeeCostType(3);
        }
        String currency = surcharge.getString("currency");
        if ("USD".equalsIgnoreCase(currency)) {
            productFeeItem.setFeeCurrency(2);
        } else if ("CNY".equalsIgnoreCase(currency)) {
            productFeeItem.setFeeCurrency(1);
        } else if ("EUR".equalsIgnoreCase(currency)) {
            productFeeItem.setFeeCurrency(3);
        }
        String ratebasis = surcharge.getString("ratebasis");
        if ("PER_DOC".equalsIgnoreCase(ratebasis)) {
            productFeeItem.setPriceComputeType(1);
        } else if ("PER_CONTAINER".equalsIgnoreCase(ratebasis)) {
            productFeeItem.setPriceComputeType(0);
        }
        productFeeItem.setPrice(surcharge.getBigDecimal("rate"));
        productFeeItem.setFeeCnName(surcharge.getString("chargedescription"));
        productFeeItem.setFeeEnName(surcharge.getString("chargedescription"));
    }

    private int computeContainerType(String code)
    {
        if ("22G1".equals(code)) {
            return 1;
        } else if ("42G1".equals(code)) {
            return 2;
        } else if ("45G1".equals(code)) {
            return 3;
        }
        throw new RuntimeException("箱型解析出错");
    }

    public Map<String, String> getRemoteSensorData()
    {
        reqCount++;
        CrawlMetadataWebsiteConfigExample crawlMetadataWebsiteConfigExample = new CrawlMetadataWebsiteConfigExample();
        crawlMetadataWebsiteConfigExample.createCriteria().andHostCodeEqualTo("msk");
        List<CrawlMetadataWebsiteConfig> crawlMetadataWebsiteConfigs = crawlMetadataWebsiteConfigMapper.selectByExampleWithBLOBs(crawlMetadataWebsiteConfigExample);

        if (tokenIndex > crawlMetadataWebsiteConfigs.size() - 1) {
            tokenIndex = 0;
        }
        CrawlMetadataWebsiteConfig crawlMetadataWebsiteConfig = crawlMetadataWebsiteConfigs.get(tokenIndex);
        String token = crawlMetadataWebsiteConfig.getToken();

        Map<String, String> header = new HashMap<>(4);

        JSONObject tokenBean = JSONObject.parseObject(token);
        if (StringUtils.isEmpty(sensorData)) {
            String abck = tokenBean.getString("_abck");
            String bmsz = tokenBean.getString("bm_sz");
            Map<String, String> sensorDataParams = new HashMap<>(4);
            sensorDataParams.put("appid", "eyqq4t1ubp4fbjklkrguol6zcc8o5jp5");
            sensorDataParams.put("siteUrl", "https://www.maersk.com.cn/book");
            sensorDataParams.put("abck", abck);
            sensorDataParams.put("bmsz", bmsz);
            HttpResp resp = HttpUtil.postBody("http://api.zjdanli.com/akamai/v2/sensorData", null, JSONObject.toJSONString(sensorDataParams));
            JSONObject retObj = JSONObject.parseObject(resp.getBodyJson());
            userAgent = retObj.getString("ua");
            sensorData = retObj.getString("sensorData");
            sensorData = Base64.getEncoder().encodeToString(sensorData.getBytes());
        }
        String str = tokenBean.getString("akamai-bm-telemetry");
        String start = str.split("sensor_data=")[0];
        String akaSign = start + "sensor_data=" + sensorData;

        header.put("Authorization", tokenBean.getString("authorization"));
        header.put("Content-Type", "application/json");
        header.put("User-Agent", userAgent);
        header.put("Consumer-Key", tokenBean.getString("consumer-key"));
        header.put("Akamai-Bm-Telemetry", akaSign);

        return header;
    }

    public JSONObject getPortInfo(String portCodeEn, String countryCode)
    {
        String api = "https://api.maersk.com.cn/synergy/reference-data/geography/locations?cityName=" + portCodeEn + "&pageSize=30&sort=cityName&type=city";
        Map<String, String> header = new HashMap<>(3);
        header.put("Consumer-Key", "Q09VmiYvj4ifBOa72Z0ekxkq9tLZCVYI");
        //header不能有host和useragent
        header.put("Host", "del");
        header.put("user-agent", "del");
        try {
            HttpResp resp = HttpUtil.get(api, header);
            String bodyJson = resp.getBodyJson();
            JSONArray arr = JSONArray.parseArray(bodyJson);
            for (Object o : arr) {
                JSONObject item = (JSONObject) o;
                if (item.getString("localityName").equalsIgnoreCase(portCodeEn) && item.getString("countryCode").equalsIgnoreCase(countryCode)) {
                    return item;
                }
            }
        } catch (Exception e) {
            log.error("查询msk港口代码出错,\n" + api + "\n" + JSONObject.toJSONString(header));
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
        }
        throw new RuntimeException("msk网站未查询到港口代码信息");
    }

}
