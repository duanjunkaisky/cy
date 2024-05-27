package com.djk.core.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.djk.core.config.Constant;
import com.djk.core.config.SpringUtil;
import com.djk.core.dao.CustomDao;
import com.djk.core.mapper.CrawlRequestStatusMapper;
import com.djk.core.model.CrawlRequestStatus;
import com.djk.core.model.CrawlRequestStatusExample;
import com.djk.core.mq.ConsumerPull;
import com.djk.core.vo.QueryRouteVo;
import com.google.common.util.concurrent.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Component
@Data
@Slf4j
@ConfigurationProperties(prefix = "crawl")
public class CrawlChain {
    public static ListeningExecutorService EXECUTOR_SERVICE = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    @Autowired
    CrawlRequestStatusMapper requestStatusMapper;

    private List<String> target;

    @Autowired
    CustomDao customDao;

    @Value("${redis.database}")
    public String REDIS_DATABASE;

    @Autowired
    RedisService redisService;

    @Async("asyncServiceExecutor")
    public void doBusiness(QueryRouteVo queryRouteVo) {
        List<ListenableFuture<String>> futureList = new ArrayList<>();

        futureList.add(EXECUTOR_SERVICE.submit(() -> {
            CrawlService crawlService = (CrawlService) SpringUtil.getBean(queryRouteVo.getBeanName());
            try {
                CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                requestStatus.setStatus(Constant.CRAWL_STATUS.RUNNING.ordinal());
                requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);

                String str = queryRouteVo.getHostCode() + " -> " + crawlService.queryData(queryRouteVo, queryRouteVo.getHostCode());

                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                requestStatus = new CrawlRequestStatus();
                requestStatus.setEndTime(System.currentTimeMillis());
                requestStatus.setStatus(Constant.CRAWL_STATUS.SUCCESS.ordinal());
                requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);

                log.info("---> " + queryRouteVo.getSpotId() + " - " + str);

                crawlRequestStatusExample = new CrawlRequestStatusExample();
                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(queryRouteVo.getSpotId());
                List<CrawlRequestStatus> crawlRequestStatuses = requestStatusMapper.selectByExample(crawlRequestStatusExample);
                if (null != crawlRequestStatuses && !crawlRequestStatuses.isEmpty()) {
                    List<CrawlRequestStatus> mergeList = crawlRequestStatuses.stream()
                            .filter(item -> item.getStatus() <= Constant.CRAWL_STATUS.RUNNING.ordinal())
                            .collect(Collectors.toList());
                    if (null == mergeList || mergeList.isEmpty()) {
                        log.info("---> " + queryRouteVo.getSpotId() + " - 本次请求爬取结束!");
                    }
                }
                return str;
            } catch (Exception e) {
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
                CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                requestStatus.setStatus(Constant.CRAWL_STATUS.ERROR.ordinal());
                requestStatus.setMsg(ExceptionUtil.stacktraceToString(e));
                requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);
            } finally {
                ConsumerPull.currentJobs.remove(queryRouteVo.getSpotId() + queryRouteVo.getHostCode());
            }
            return "---> " + queryRouteVo.getSpotId() + " - " + queryRouteVo.getHostCode() + " -> 0";
        }));
    }

}
