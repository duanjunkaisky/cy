package com.djk.core.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.djk.core.api.CommonResult;
import com.djk.core.service.CrawlOrderMskService;
import com.djk.core.vo.QueryRouteVo;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.djk.core.config.Constant.BUSINESS_NAME_ORDER;

/**
 * @author duanjunkai
 */
@Controller
@Slf4j
@Api(tags = "QuicklyOrderController", description = "抢单接口")
@RequestMapping("/crawl/order")
@Data
public class QuicklyOrderController
{
    @Autowired
    CrawlOrderMskService orderMskService;

    @RequestMapping(value = "/msk", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult mskOrder(@RequestBody QueryRouteVo queryRouteVo)
    {
        queryRouteVo.setUniqueId(orderMskService.getUniqueId());
        queryRouteVo.setLogId(orderMskService.getLogId());
        queryRouteVo.setHostCode("msk");
        orderMskService.addLog(null, BUSINESS_NAME_ORDER, "收到抢单请求", null, queryRouteVo);

        log.info("开始绑定本次请求token账号");
        orderMskService.setTokenAccount(queryRouteVo);
        log.info("本次请求token账号: " + queryRouteVo.getAccountName());

        try {
            orderMskService.createOrder(queryRouteVo);
        } catch (Exception e) {
            orderMskService.addLog(null, BUSINESS_NAME_ORDER, e.getMessage(), ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
        } finally {
            orderMskService.returnAccount(queryRouteVo);
        }
        orderMskService.addLog(null, BUSINESS_NAME_ORDER, "抢单结束", null, queryRouteVo);
        log.info("本次抢单执行完成: " + queryRouteVo.getLogId());
        return CommonResult.success("");
    }

    @RequestMapping(value = "/cosco", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult coscoOrder(@RequestBody QueryRouteVo queryRouteVo)
    {
        return CommonResult.success("");
    }

}
