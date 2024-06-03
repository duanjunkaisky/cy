package com.djk.core.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.djk.core.config.Constant;
import com.djk.core.config.SpringUtil;
import com.djk.core.dao.CustomDao;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.mapper.CrawlRequestStatusMapper;
import com.djk.core.model.*;
import com.djk.core.mq.ConsumerPull;
import com.djk.core.vo.QueryRouteVo;
import com.google.common.util.concurrent.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.djk.core.config.Constant.BUSINESS_NAME_CRAWL;

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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private List<String> target;

    @Autowired
    CrawlMetadataWebsiteConfigMapper crawlMetadataWebsiteConfigMapper;

    @Autowired
    CustomDao customDao;

    @Value("${redis.database}")
    public String REDIS_DATABASE;

    @Autowired
    RedisService redisService;

    @Async("asyncServiceExecutor")
    public void doBusiness(QueryRouteVo queryRouteVo) {
        CrawlService crawlService = (CrawlService) SpringUtil.getBean(queryRouteVo.getBeanName());

        BaseShippingCompany baseShippingCompany = crawlService.getShipCompany(queryRouteVo.getHostCode());
        BasePort fromPort = crawlService.getFromPort(queryRouteVo);
        BasePort toPort = crawlService.getToPort(queryRouteVo);

        crawlService.flagDelData(queryRouteVo, baseShippingCompany.getId());

        EXECUTOR_SERVICE.submit(() -> {
            try {
                CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();

                log.info("开始设置token-ip");
                crawlService.setTokenIp(queryRouteVo);
                log.info("成功获取token-ip: " + queryRouteVo.getTokenIp());

                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                requestStatus.setStatus(Constant.CRAWL_STATUS.RUNNING.ordinal());
                requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);

                String str = queryRouteVo.getHostCode() + " -> " + crawlService.queryData(baseShippingCompany, fromPort, toPort, queryRouteVo);

                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                requestStatus = new CrawlRequestStatus();
                requestStatus.setEndTime(System.currentTimeMillis());
                requestStatus.setStatus(Constant.CRAWL_STATUS.SUCCESS.ordinal());
                requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);

                crawlService.addLog(null, BUSINESS_NAME_CRAWL, "爬取结束", null, queryRouteVo);

                log.info("---> " + queryRouteVo.getSpotId() + " - " + queryRouteVo.getLogId() + " - " + str);

                crawlRequestStatusExample = new CrawlRequestStatusExample();
                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(queryRouteVo.getSpotId());
                List<CrawlRequestStatus> crawlRequestStatuses = requestStatusMapper.selectByExample(crawlRequestStatusExample);
                if (null != crawlRequestStatuses && !crawlRequestStatuses.isEmpty()) {
                    List<CrawlRequestStatus> mergeList = crawlRequestStatuses.stream()
                            .filter(item -> item.getStatus() <= Constant.CRAWL_STATUS.RUNNING.ordinal())
                            .collect(Collectors.toList());
                    if (null == mergeList || mergeList.isEmpty()) {
                        log.info("---> " + queryRouteVo.getSpotId() + " - 本次请求爬取结束! - " + queryRouteVo.getLogId());
                    }
                }
                return str;
            } catch (Exception e) {
                crawlService.addLog(null, BUSINESS_NAME_CRAWL, "爬取出错", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
                CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                requestStatus.setStatus(Constant.CRAWL_STATUS.ERROR.ordinal());
                requestStatus.setMsg(ExceptionUtil.stacktraceToString(e));
                requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);
            } finally {
                String tokenIp = (String) redisService.get(REDIS_DATABASE + ":tmp:token-busy:" + queryRouteVo.getLogId());
                redisService.del(REDIS_DATABASE + ":tmp:token-busy:" + queryRouteVo.getLogId());
                redisService.del(REDIS_DATABASE + ":tmp:token-busy:" + tokenIp);

                ConsumerPull.currentJobs.remove(queryRouteVo.getSpotId() + queryRouteVo.getHostCode());
            }
            return "---> " + queryRouteVo.getSpotId() + " - " + queryRouteVo.getHostCode() + " - " + queryRouteVo.getLogId() + " -> 0";
        });
    }

}
