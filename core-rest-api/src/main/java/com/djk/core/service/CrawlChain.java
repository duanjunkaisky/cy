package com.djk.core.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONObject;
import com.djk.core.config.Constant;
import com.djk.core.config.SpringUtil;
import com.djk.core.mapper.CrawlRequestStatusMapper;
import com.djk.core.model.CrawlRequestStatus;
import com.djk.core.model.CrawlRequestStatusExample;
import com.djk.core.mq.ConsumerPull;
import com.djk.core.vo.QueryRouteVo;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Component
@Data
@ConfigurationProperties(prefix = "crawl")
@Slf4j
public class CrawlChain
{
    private List<String> target;

    public static ListeningExecutorService EXECUTOR_SERVICE = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    @Autowired
    CrawlRequestStatusMapper requestStatusMapper;

    @Async("asyncServiceExecutor")
    public void doBusiness(QueryRouteVo queryRouteVo) throws ExecutionException, InterruptedException
    {
        List<ListenableFuture<String>> futureList = new ArrayList<>();
        for (String beanName : target) {
            String hostCode = getHostCode(beanName);
            futureList.add(EXECUTOR_SERVICE.submit(() -> {
                CrawlService crawlService = (CrawlService) SpringUtil.getBean(beanName);
                try {
                    CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                    requestStatus.setRequestId(String.valueOf(queryRouteVo.getRequestId()));
                    requestStatus.setRequestParams(JSONObject.toJSONString(queryRouteVo));
                    requestStatus.setFromProt(queryRouteVo.getDeparturePortEn());
                    requestStatus.setToPort(queryRouteVo.getDestinationPortEn());
                    requestStatus.setStatus(Constant.CRAWL_STATUS.RUNNING.ordinal());
                    requestStatus.setHostCode(hostCode);
                    requestStatusMapper.insertSelective(requestStatus);

                    crawlService.queryData(queryRouteVo, hostCode);
                } catch (Exception e) {
                    log.error(ExceptionUtil.getMessage(e));
                    log.error(ExceptionUtil.stacktraceToString(e));
                    CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
                    crawlRequestStatusExample.createCriteria().andRequestIdEqualTo(String.valueOf(queryRouteVo.getRequestId())).andHostCodeEqualTo(hostCode);
                    CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                    requestStatus.setStatus(Constant.CRAWL_STATUS.ERROR.ordinal());
                    requestStatus.setMsg(ExceptionUtil.stacktraceToString(e));
                    requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);
                }
                return beanName;
            }));
        }
        ListenableFuture<List<String>> listListenableFuture = Futures.allAsList(Lists.newArrayList(futureList));
        List<String> implNameList = listListenableFuture.get();
        implNameList.forEach(item -> {
            log.info("---> " + queryRouteVo.getRequestId() + "-" + getHostCode(item) + " 爬取完成!");
            CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
            crawlRequestStatusExample.createCriteria().andRequestIdEqualTo(String.valueOf(queryRouteVo.getRequestId())).andHostCodeEqualTo(getHostCode(item)).andStatusEqualTo(Constant.CRAWL_STATUS.RUNNING.ordinal());
            CrawlRequestStatus requestStatus = new CrawlRequestStatus();
            requestStatus.setStatus(Constant.CRAWL_STATUS.SUCCESS.ordinal());
            requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);
        });
        ConsumerPull.currentJobs.remove(String.valueOf(queryRouteVo.getRequestId()));

        log.info("---> " + queryRouteVo.getRequestId() + "本地请求爬取结束!");
    }

    public static String getHostCode(String beanName)
    {
        String str = beanName.toLowerCase();
        return str.replaceAll("crawlservicefro", "").replaceAll("impl", "");
    }

}
