package com.djk.core.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.djk.core.api.CommonResult;
import com.djk.core.config.Constant;
import com.djk.core.mapper.BasePortMapper;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.mapper.CrawlRequestStatusMapper;
import com.djk.core.model.BasePort;
import com.djk.core.model.BasePortExample;
import com.djk.core.model.CrawlRequestStatus;
import com.djk.core.model.CrawlRequestStatusExample;
import com.djk.core.mq.ConsumerPull;
import com.djk.core.service.*;
import com.djk.core.utils.FreeMakerUtil;
import com.djk.core.vo.QueryRouteVo;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author duanjunkai
 * @date 2024/04/24
 */
@Controller
@Slf4j
@Api(tags = "ApiController", description = "查询航班")
@RequestMapping("/")
@Data
@ConfigurationProperties(prefix = "crawl")
public class ApiController
{

    @Value("${rocketmq.producer.topic}")
    private String topic;

    @Value("${rocketmq.maxCrawlTime}")
    private int maxCrawlTime;

    private List<String> target;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.database}")
    public String REDIS_DATABASE;

    @Autowired
    CrawlMetadataWebsiteConfigMapper metadataWebsiteConfigMapper;

    @Autowired
    CrawlServiceFroCmaImpl crawlService;

    @Autowired
    CrawlChain crawlChain;

    @Autowired
    CrawlRequestStatusMapper requestStatusMapper;

    @Autowired
    BasePortMapper basePortMapper;

    /**
     * @param queryRouteVo
     * @return {@link CommonResult}
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult query(@RequestBody QueryRouteVo queryRouteVo)
    {
        if (StringUtils.isEmpty(queryRouteVo.getDeparturePortEn())
                || StringUtils.isEmpty(queryRouteVo.getDestinationPortEn())
                || StringUtils.isEmpty(queryRouteVo.getDepartureCountryCode())
                || StringUtils.isEmpty(queryRouteVo.getDestinationCountryCode())
                || StringUtils.isEmpty(queryRouteVo.getDepartureDate())
                || StringUtils.isEmpty(queryRouteVo.getContainerType())) {
            return CommonResult.failed("参数不能为空");
        }

        queryRouteVo.setStartTime(System.currentTimeMillis());
        queryRouteVo.setSpotId(crawlService.createSpotId(queryRouteVo.getDeparturePortEn(), queryRouteVo.getDestinationPortEn()));
        for (String beanName : target) {
            queryRouteVo.setBeanName(beanName);
            String hostCode = getHostCode(beanName);
            queryRouteVo.setHostCode(hostCode);

            CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
            crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(queryRouteVo.getSpotId()).andHostCodeEqualTo(queryRouteVo.getHostCode());
            List<CrawlRequestStatus> crawlRequestStatuses = requestStatusMapper.selectByExample(crawlRequestStatusExample);
            CrawlRequestStatus requestStatus = new CrawlRequestStatus();
            if (null != crawlRequestStatuses && !crawlRequestStatuses.isEmpty()) {
                requestStatus.setId(crawlRequestStatuses.get(0).getId());
                requestStatus.setStatus(Constant.CRAWL_STATUS.WAITING.ordinal());
                requestStatusMapper.updateByPrimaryKeySelective(requestStatus);
            } else {
                requestStatus.setSpotId(String.valueOf(queryRouteVo.getSpotId()));
                requestStatus.setRequestParams(JSONObject.toJSONString(queryRouteVo));
                requestStatus.setStartTime(queryRouteVo.getStartTime());
                requestStatus.setFromPort(queryRouteVo.getDeparturePortEn());
                requestStatus.setToPort(queryRouteVo.getDestinationPortEn());
                requestStatus.setStatus(Constant.CRAWL_STATUS.WAITING.ordinal());
                requestStatus.setHostCode(queryRouteVo.getHostCode());
                requestStatusMapper.insertSelective(requestStatus);
            }

            if (!"cosco".equalsIgnoreCase(queryRouteVo.getHostCode())) {
                rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(JSONObject.toJSONString(queryRouteVo)).build());
                log.info("推送到消息->\n topic: " + topic + ",\n " + JSONObject.toJSONString(queryRouteVo));
            }


        }

        JSONObject retObj = new JSONObject();
        retObj.put("spot_id", queryRouteVo.getSpotId());
        return CommonResult.success(retObj, "操作成功");
    }

    @RequestMapping(value = "/productNumber", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult productNumber()
    {
        String productNumber = crawlService.getProductNumber();
        return CommonResult.success(productNumber, "操作成功");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult test(@RequestBody QueryRouteVo queryRouteVo)
    {
        try {
            queryRouteVo.setStartTime(System.currentTimeMillis());
            queryRouteVo.setSpotId(crawlService.createSpotId(queryRouteVo.getDeparturePortEn(), queryRouteVo.getDestinationPortEn()));
            crawlChain.doBusiness(queryRouteVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success("操作成功");
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getToken(@RequestBody QueryRouteVo queryRouteVo)
    {
        try {
            JSONObject token = crawlService.getToken(queryRouteVo.getHostCode(), 1);
            return CommonResult.success(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success("操作成功");
    }

    @RequestMapping(value = "/getCoscoParam", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getCoscoParam()
    {
        try {
            CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
            crawlRequestStatusExample.createCriteria().andHostCodeEqualTo("cosco").andStatusEqualTo(Constant.CRAWL_STATUS.WAITING.ordinal());
            List<CrawlRequestStatus> crawlRequestStatuses = requestStatusMapper.selectByExample(crawlRequestStatusExample);
            if (null != crawlRequestStatuses && !crawlRequestStatuses.isEmpty()) {
                List<Map<String, String>> collect = crawlRequestStatuses.stream().map(item -> {
                    String requestParams = item.getRequestParams();

                    Map<String, String> ret = new HashMap<>(6);
                    ret.put("id", String.valueOf(item.getId()));
                    ret.put("maxCrawlTime", String.valueOf(maxCrawlTime));
                    QueryRouteVo queryRouteVo = JSONObject.parseObject(requestParams, QueryRouteVo.class);
                    BasePortExample basePortExample = new BasePortExample();
                    basePortExample.createCriteria().andPortCodeEqualTo(queryRouteVo.getDeparturePortEn()).andCountryCodeEqualTo(queryRouteVo.getDepartureCountryCode()).andStatusEqualTo((byte) 0);
                    List<BasePort> basePorts = basePortMapper.selectByExample(basePortExample);
                    BasePort basePort = basePorts.get(0);
                    ret.put("fromPortName", checkPortName(basePort.getCountryCode().equalsIgnoreCase("CN") ? basePort.getCityZh() : basePort.getCityEn()));
                    ret.put("fromPortCountryName", checkPortName(basePort.getCountryCode().equalsIgnoreCase("CN") ? basePort.getCountryNameZh() : basePort.getCountryNameEn()));
                    ret.put("fromPortQueryId", basePort.getCoscoCode());
                    basePortExample = new BasePortExample();
                    basePortExample.createCriteria().andPortCodeEqualTo(queryRouteVo.getDestinationPortEn()).andCountryCodeEqualTo(queryRouteVo.getDestinationCountryCode()).andStatusEqualTo((byte) 0);
                    basePorts = basePortMapper.selectByExample(basePortExample);
                    basePort = basePorts.get(0);
                    ret.put("toPortName", checkPortName(basePort.getCountryCode().equalsIgnoreCase("CN") ? basePort.getCityZh() : basePort.getCityEn()));
                    ret.put("toPortCountryName", checkPortName(basePort.getCountryCode().equalsIgnoreCase("CN") ? basePort.getCountryNameZh() : basePort.getCountryNameEn()));
                    ret.put("toPortQueryId", basePort.getCoscoCode());
                    return ret;
                }).filter(item -> {
                    if (StringUtils.isEmpty(item.get("fromPortQueryId")) || StringUtils.isEmpty(item.get("toPortQueryId"))) {
                        CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                        requestStatus.setId(Long.parseLong(item.get("id")));
                        requestStatus.setStatus(Constant.CRAWL_STATUS.ERROR.ordinal());
                        requestStatus.setMsg("base_port表未配置coscoCode");
                        requestStatusMapper.updateByPrimaryKeySelective(requestStatus);
                        return false;
                    }
                    return true;
                }).collect(Collectors.toList());
                for (Map<String, String> item : collect) {
                    long id = Long.parseLong(item.get("id"));
                    Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_DATABASE + ":tmp:cosco:query_status_id:" + id, 1, maxCrawlTime, TimeUnit.MILLISECONDS);
                    if (aBoolean) {
                        CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                        requestStatus.setId(id);
                        requestStatus.setStatus(Constant.CRAWL_STATUS.RUNNING.ordinal());
                        requestStatusMapper.updateByPrimaryKeySelective(requestStatus);
                        return CommonResult.success(item);
                    }
                }
                return CommonResult.success(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }

    private String checkPortName(String portName)
    {
        String now = "";
        portName = portName.replaceAll("市", "");
        String[] arr = portName.toLowerCase().split(" ");
        for (int i = 0; i < arr.length; i++) {
            String substring = arr[i].substring(0, 1);
            now += substring.toUpperCase() + arr[i].substring(1) + " ";
        }
        return now.trim();
    }

    @RequestMapping(value = "/insertCoscoData", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult insertCoscoData(@RequestBody JSONObject jsonObject)
    {
        //需要增加一个字段：是否是最后一条数据,然后更新本次爬取结束的状态
        log.info(JSONObject.toJSONString(jsonObject));
        return CommonResult.success("操作成功");
    }

    @RequestMapping(value = "/updateCoscoStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateCoscoStatus(@RequestBody JSONObject jsonObject)
    {
        Long id = jsonObject.getLong("id");
        CrawlRequestStatus requestStatus = new CrawlRequestStatus();
        requestStatus.setId(id);
        requestStatus.setStatus(Constant.CRAWL_STATUS.SUCCESS.ordinal());
        requestStatusMapper.updateByPrimaryKeySelective(requestStatus);
        return CommonResult.success("操作成功");
    }

    @RequestMapping(value = "/initPort", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult initPort()
    {
        String byTemplate = FreeMakerUtil.createByTemplate("1.ftl", null);
        JSONArray array = JSONArray.parseArray(byTemplate);
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = array.getJSONObject(i);
            BasePort basePort = new BasePort();
            basePort.setPortNameEn(o.getString("enName"));
            basePort.setPortNameZh(o.getString("name"));
            basePort.setPortCode(basePort.getPortNameEn().split(",")[0]);
            basePort.setCountryNameEn(o.getString("countryNameEn"));
            basePort.setCountryNameZh(o.getString("countryName"));
            basePort.setCountryCode(o.getString("countryCode"));
            basePort.setProvinceEn(o.getString("stateNameEn"));
            basePort.setProvinceZh(o.getString("stateName"));
            basePort.setStateEn(o.getString("stateNameEn"));
            basePort.setStateZh(o.getString("stateName"));
            basePort.setCityEn(o.getString("cityNameEn"));
            basePort.setCityZh(o.getString("cityName"));
            basePort.setPortType(2);
            basePort.setRegion(34L);
            basePort.setArea(35L);
            basePort.setStatus((byte) 0);
            basePort.setNotes(o.getString("remark"));
            basePort.setCoscoCode(o.getString("coscoCode"));
            basePort.setOoclCode(o.getString("ooclCode"));
            basePort.setEmcCode(o.getString("emcCode"));
            basePort.setMskCode(o.getString("mskCode"));
            basePort.setMskRkstCode(o.getString("mskRkstCode"));
            basePort.setMccCode(o.getString("mccCode"));
            basePort.setMccRkstCode(o.getString("mccRkstCode"));
            basePort.setOneCode(o.getString("oneCode"));
            basePort.setMscCode(o.getString("mscCode"));
            basePort.setMscLocalCode(o.getString("mscLocalCode"));
            basePort.setZimCode(o.getString("zimCode"));
            basePort.setHmmCode(o.getString("hmmCode"));
            basePort.setCmaCode(o.getString("cmaCode"));
            basePort.setCncCode(o.getString("cncCode"));
            basePort.setHplCode(o.getString("hplCode"));
            basePort.setHplPostalCode(o.getString("hplPostalCode"));
            basePort.setCreateTime(new Date());
            basePort.setUpdateTime(new Date());
            basePort.setTenantId(0L);
            basePort.setDeleted(false);
            basePortMapper.insertSelective(basePort);
        }
        return CommonResult.success("操作成功");
    }

    private static String getHostCode(String beanName)
    {
        String str = beanName.toLowerCase();
        return str.replaceAll("crawlservicefro", "").replaceAll("impl", "");
    }

}
