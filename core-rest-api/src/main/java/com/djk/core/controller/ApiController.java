package com.djk.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.djk.core.api.CommonResult;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.service.CrawlServiceFroMskImpl;
import com.djk.core.service.Generator;
import com.djk.core.vo.QueryRouteVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author duanjunkai
 * @date 2024/04/24
 */
@Controller
@Slf4j
@Api(tags = "ApiController", description = "查询航班")
@RequestMapping("/")
public class ApiController
{

    @Value("${rocketmq.producer.topic}")
    private String topic;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Autowired
    CrawlMetadataWebsiteConfigMapper metadataWebsiteConfigMapper;

    @Autowired
    CrawlServiceFroMskImpl crawlServiceFroMsk;

    /**
     * @param queryRouteVo
     * @return {@link CommonResult}
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult query(@RequestBody QueryRouteVo queryRouteVo)
    {
        long requestId = Generator.nextId();
        queryRouteVo.setStartTime(System.currentTimeMillis());
        queryRouteVo.setSpotId(crawlServiceFroMsk.createSpotId(queryRouteVo.getDeparturePortEn(), queryRouteVo.getDestinationPortEn()));
        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(JSONObject.toJSONString(queryRouteVo)).build());
        String msgId = sendResult.getMsgId();
        log.info("推送到消息->\n topic: " + topic + "\n 消息id: " + msgId + ",\n " + JSONObject.toJSONString(queryRouteVo));
        JSONObject retObj = new JSONObject();
        retObj.put("requestId", requestId);
        retObj.put("useage", "客户端可通过该requestId适时获取爬取进度等信息");
        retObj.put("sql", "客户端可通过该requestId适时获取爬取进度等信息");
        return CommonResult.success(retObj, "操作成功");
    }

    @RequestMapping(value = "/productNumber", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult productNumber()
    {
        String productNumber = crawlServiceFroMsk.getProductNumber();
        return CommonResult.success(productNumber, "操作成功");
    }

}
