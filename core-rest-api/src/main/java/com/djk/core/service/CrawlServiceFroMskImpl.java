package com.djk.core.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.djk.core.mapper.BasePortMapper;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.model.BasePort;
import com.djk.core.model.CrawlMetadataWebsiteConfig;
import com.djk.core.model.CrawlMetadataWebsiteConfigExample;
import com.djk.core.model.CrawlProductInfo;
import com.djk.core.utils.FreeMakerUtil;
import com.djk.core.utils.HttpResp;
import com.djk.core.utils.HttpUtil;
import com.djk.core.vo.ContainerDist;
import com.djk.core.vo.QueryRouteVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private static final int MAX_REQ_COUNT = 1;

    private static int reqCount = 0;
    private static int tokenIndex = 0;
    private static String sensorData;
    private static String userAgent;

    @Autowired
    BasePortMapper basePortMapper;

    @Autowired
    CrawlMetadataWebsiteConfigMapper crawlMetadataWebsiteConfigMapper;

    public static List<ContainerDist> containerList = new ArrayList<>(3);

    static {
        containerList.add(ContainerDist.builder().containerCode("22G1").containerSize("20").containerType("DRY").build());
        containerList.add(ContainerDist.builder().containerCode("42G1").containerSize("40").containerType("DRY").build());
        containerList.add(ContainerDist.builder().containerCode("45G1").containerSize("40").containerType("HDRY").build());
    }

    @Override
    public String queryData(QueryRouteVo queryRouteVo)
    {
        BasePort fromPort = getFromPort(queryRouteVo);
        BasePort toPort = getToPort(queryRouteVo);
        JSONObject portInfoFrom = getPortInfo(queryRouteVo.getDeparturePortEn(), fromPort.getCountryCode());
        JSONObject portInfoTo = getPortInfo(queryRouteVo.getDestinationPortEn(), toPort.getCountryCode());

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
                    data.put("fromPortId", portInfoFrom.getStr("maerskGeoLocationId"));
                    data.put("fromPortCode", portInfoFrom.getStr("maerskRkstCode"));
                    data.put("fromPortCountry", fromPort.getCountryCode());
                    data.put("fromPortFullName", portInfoFrom.getStr("maerskRkstCode") + " (" + portInfoFrom.getStr("maerskRkstCode") + "), " + portInfoFrom.getStr("maerskRkstCode"));

                    data.put("toPortId", portInfoTo.getStr("maerskGeoLocationId"));
                    data.put("toPortCode", portInfoTo.getStr("maerskRkstCode"));
                    data.put("toPortCountry", toPort.getCountryCode());
                    data.put("toPortFullName", portInfoTo.getStr("maerskRkstCode") + " (" + portInfoTo.getStr("maerskRkstCode") + "), " + portInfoTo.getStr("maerskRkstCode"));

                    data.put("containerCode", container.getContainerCode());
                    data.put("containerSize", container.getContainerSize());
                    data.put("containerType", container.getContainerType());

                    data.put("queryDate", queryRouteVo.getDepartureDate());
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

                    JSONObject retObj = JSONUtil.parseObj(bodyJson);
                    hasMore = retObj.getBool("loadMore");
                    page++;

                    //开始处理入库
                    JSONArray offers = retObj.getJSONArray("offers");
                    for (Object o : offers) {
                        JSONObject item = (JSONObject) o;
                        JSONObject productOffer = (JSONObject) item.get("productOffer");
                        if (!StringUtils.isEmpty(productOffer)) {
                            CrawlProductInfo productInfo = new CrawlProductInfo();
//                        productInfo
//                        productOffer
                        }
                    }
                } catch (Exception e) {
                    reqCount = 0;
                    throw new RuntimeException("爬取数据出错", e);
                }
            }
            reqCount = 0;
        }
        return null;
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

        JSONObject tokenBean = JSONUtil.parseObj(token);
        if (StringUtils.isEmpty(sensorData)) {
            String abck = tokenBean.getStr("_abck");
            String bmsz = tokenBean.getStr("bm_sz");
            Map<String, String> sensorDataParams = new HashMap<>(4);
            sensorDataParams.put("appid", "eyqq4t1ubp4fbjklkrguol6zcc8o5jp5");
            sensorDataParams.put("siteUrl", "https://www.maersk.com.cn/book");
            sensorDataParams.put("abck", abck);
            sensorDataParams.put("bmsz", bmsz);
            HttpResp resp = HttpUtil.postBody("http://api.zjdanli.com/akamai/v2/sensorData", null, JSONUtil.toJsonStr(sensorDataParams));
            JSONObject retObj = JSONUtil.parseObj(resp.getBodyJson());
            userAgent = retObj.getStr("ua");
            sensorData = retObj.getStr("sensorData");
            sensorData = Base64.getEncoder().encodeToString(sensorData.getBytes());
        }
        String str = tokenBean.getStr("akamai-bm-telemetry");
        String start = str.split("sensor_data=")[0];
        String akaSign = start + "sensor_data=" + sensorData;

        header.put("Authorization", tokenBean.getStr("authorization"));
        header.put("Content-Type", "application/json");
        header.put("User-Agent", userAgent);
        header.put("Consumer-Key", tokenBean.getStr("consumer-key"));
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
            JSONArray arr = JSONUtil.parseArray(bodyJson);
            for (Object o : arr) {
                JSONObject item = (JSONObject) o;
                if (item.getStr("localityName").equalsIgnoreCase(portCodeEn) && item.getStr("countryCode").equalsIgnoreCase(countryCode)) {
                    return item;
                }
            }
        } catch (Exception e) {
            log.error("查询msk港口代码出错,\n" + api + "\n" + JSONUtil.toJsonStr(header));
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
        }
        throw new RuntimeException("msk网站未查询到港口代码信息");
    }

}
