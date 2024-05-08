package com.djk.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.djk.core.api.CommonResult;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.service.*;
import com.djk.core.vo.QueryRouteVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author duanjunkai
 * @date 2024/04/24
 */
@Controller
@Slf4j
@Api(tags = "ApiController", description = "查询航班")
@RequestMapping("/")
public class ApiController {

    @Value("${rocketmq.producer.topic}")
    private String topic;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Autowired
    CrawlMetadataWebsiteConfigMapper metadataWebsiteConfigMapper;

    @Autowired
    CrawlServiceFroCmaImpl crawlService;

    /**
     * @param queryRouteVo
     * @return {@link CommonResult}
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult query(@RequestBody QueryRouteVo queryRouteVo) {
        if (StringUtils.isEmpty(queryRouteVo.getDeparturePortEn())
                || StringUtils.isEmpty(queryRouteVo.getDestinationPortEn())
                || StringUtils.isEmpty(queryRouteVo.getDepartureDate())) {
            return CommonResult.failed("参数不能为空");
        }
        queryRouteVo.setStartTime(System.currentTimeMillis());
        queryRouteVo.setSpotId(crawlService.createSpotId(queryRouteVo.getDeparturePortEn(), queryRouteVo.getDestinationPortEn()));
        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(JSONObject.toJSONString(queryRouteVo)).build());
        String msgId = sendResult.getMsgId();
        log.info("推送到消息->\n topic: " + topic + "\n 消息id: " + msgId + ",\n " + JSONObject.toJSONString(queryRouteVo));
        JSONObject retObj = new JSONObject();
        retObj.put("spot_id", queryRouteVo.getSpotId());
        retObj.put("useage", "客户端可通过该 spot_id 适时获取爬取进度等信息, host_code -> [msk cma one ...]");
        retObj.put("查询数量", "select count(1) from product_info p where p.spot_id = '" + queryRouteVo.getSpotId() + "' and p.estimated_departure_date >= '" + queryRouteVo.getDepartureDate() + "' and shipping_company_id = 1");
        retObj.put("查询状态", "select p.* from crawl_request_status p where p.spot_id = '" + queryRouteVo.getSpotId() + "' and host_code = 'msk'");
        return CommonResult.success(retObj, "操作成功");
    }

    @RequestMapping(value = "/productNumber", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult productNumber() {
        String productNumber = crawlService.getProductNumber();
        return CommonResult.success(productNumber, "操作成功");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult test(@RequestBody QueryRouteVo queryRouteVo) {
        crawlService.queryData(queryRouteVo, queryRouteVo.getHostCode());
//        crawlService.setHostCode(queryRouteVo.getHostCode());
//        crawlService.getRequestToken(new HashMap<String,String>());

        return CommonResult.success("操作成功");
    }

}
