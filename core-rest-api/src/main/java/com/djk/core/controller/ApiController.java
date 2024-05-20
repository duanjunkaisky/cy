package com.djk.core.controller;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

            rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(JSONObject.toJSONString(queryRouteVo)).build());
            log.info("推送到消息->\n topic: " + topic + ",\n " + JSONObject.toJSONString(queryRouteVo));
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
                    List<String> portCodeList = new ArrayList<>();
                    String fromPort = item.getFromPort();
                    String toPort = item.getToPort();
                    portCodeList.add(fromPort);
                    portCodeList.add(toPort);
                    BasePortExample basePortExample = new BasePortExample();
                    basePortExample.createCriteria().andPortCodeIn(portCodeList).andStatusEqualTo((byte) 0);
                    List<BasePort> basePorts = basePortMapper.selectByExample(basePortExample);
                    Map<String, String> ret = new HashMap<>(6);
                    ret.put("id", String.valueOf(item.getId()));
                    for (int i = 0; i < basePorts.size(); i++) {
                        BasePort basePort = basePorts.get(i);
                        String key = "from";
                        if (toPort.equalsIgnoreCase(basePort.getPortCode())) {
                            key = "to";
                        }
                        ret.put(key + "PortName", basePort.getCountryCode().equalsIgnoreCase("CN") ? basePort.getPortNameZh() : basePort.getPortNameEn());
                        ret.put(key + "PortCountryName", basePort.getCountryCode().equalsIgnoreCase("CN") ? basePort.getCountryNameZh() : basePort.getCountryNameEn());
                        ret.put(key + "PortQueryId", basePort.getCoscoCode());
                    }
                    return ret;
                }).filter(item -> {
                    Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_DATABASE + ":tmp:cosco:query_status_id:" + item.get("id"), 1, ConsumerPull.FREE_TIME, TimeUnit.MILLISECONDS);
                    if (aBoolean) {
                        CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                        requestStatus.setId(Long.parseLong(item.get("id")));
                        requestStatus.setStatus(Constant.CRAWL_STATUS.RUNNING.ordinal());
                        requestStatusMapper.updateByPrimaryKeySelective(requestStatus);
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());

                Map<String, String> stringStringMap = collect.get(0);
                return CommonResult.success(stringStringMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }

    @RequestMapping(value = "/insertCoscoData", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult insertCoscoData(@RequestBody JSONObject jsonObject)
    {
        //需要增加一个字段：是否是最后一条数据,然后更新本次爬取结束的状态
        log.info(JSONObject.toJSONString(jsonObject));
        return CommonResult.success("操作成功");
    }

    private static String getHostCode(String beanName)
    {
        String str = beanName.toLowerCase();
        return str.replaceAll("crawlservicefro", "").replaceAll("impl", "");
    }

}
