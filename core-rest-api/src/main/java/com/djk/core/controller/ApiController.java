package com.djk.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.djk.core.api.CommonResult;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.service.*;
import com.djk.core.vo.QueryRouteVo;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    CrawlMetadataWebsiteConfigMapper metadataWebsiteConfigMapper;

    @Autowired
    CrawlServiceFroCmaImpl crawlService;

    @Autowired
    CrawlChain crawlChain;

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

    private static String getHostCode(String beanName)
    {
        String str = beanName.toLowerCase();
        return str.replaceAll("crawlservicefro", "").replaceAll("impl", "");
    }

}
