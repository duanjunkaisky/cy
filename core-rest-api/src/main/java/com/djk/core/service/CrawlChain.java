package com.djk.core.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.djk.core.config.Constant;
import com.djk.core.config.SpringUtil;
import com.djk.core.dao.CustomDao;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.mapper.CrawlRequestLogMapper;
import com.djk.core.mapper.CrawlRequestStatusMapper;
import com.djk.core.model.*;
import com.djk.core.mq.ConsumerPull;
import com.djk.core.vo.QueryRouteVo;
import com.google.common.util.concurrent.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
public class CrawlChain
{
    public static ListeningExecutorService EXECUTOR_SERVICE = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(99));

    @Autowired
    CrawlRequestStatusMapper requestStatusMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    CrawlMetadataWebsiteConfigMapper crawlMetadataWebsiteConfigMapper;

    @Autowired
    CustomDao customDao;

    @Value("${redis.database}")
    public String REDIS_DATABASE;

    @Autowired
    RedisService redisService;

    @Async("asyncServiceExecutor")
    public void doBusiness(QueryRouteVo queryRouteVo)
    {

        EXECUTOR_SERVICE.submit(() -> {
            CrawlService crawlService = null;
            String str = queryRouteVo.getHostCode() + " -> ";
            try {
                try {
                    crawlService = (CrawlService) SpringUtil.getBean(queryRouteVo.getBeanName());
                } catch (Exception e) {
                    log.error("---> " + queryRouteVo.getSpotId() + " - " + queryRouteVo.getLogId() + " - " + queryRouteVo.getHostCode() + "未提供服务");
                }
                if (null == crawlService) {
                    throw new RuntimeException(queryRouteVo.getHostCode() + "未提供服务");
                } else {
                    BaseShippingCompany baseShippingCompany = crawlService.getShipCompany(queryRouteVo.getHostCode());
                    BasePort fromPort = crawlService.getFromPort(queryRouteVo);
                    BasePort toPort = crawlService.getToPort(queryRouteVo);

                    crawlService.flagDelData(queryRouteVo, baseShippingCompany.getId());

                    CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                    CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();

                    log.info("开始绑定本次请求token账号");
                    crawlService.setTokenAccount(queryRouteVo);
                    log.info("本次请求token账号: " + queryRouteVo.getAccountName());

                    crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                    requestStatus.setStatus(Constant.CRAWL_STATUS.RUNNING.ordinal());
                    requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);

                    str += crawlService.queryData(baseShippingCompany, fromPort, toPort, queryRouteVo);

                    crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                    requestStatus = new CrawlRequestStatus();
                    requestStatus.setEndTime(System.currentTimeMillis());
                    requestStatus.setStatus(Constant.CRAWL_STATUS.SUCCESS.ordinal());
                    requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);

                    addLog(null, BUSINESS_NAME_CRAWL, "爬取结束", null, queryRouteVo);
                    crawlService.delData(queryRouteVo);

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
                }
                return str;
            } catch (Exception e) {
                addLog(null, BUSINESS_NAME_CRAWL, "爬取出错", ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e), queryRouteVo);
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
                CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
                crawlRequestStatusExample.createCriteria().andSpotIdEqualTo(String.valueOf(queryRouteVo.getSpotId())).andHostCodeEqualTo(queryRouteVo.getHostCode());
                CrawlRequestStatus requestStatus = new CrawlRequestStatus();
                requestStatus.setStatus(Constant.CRAWL_STATUS.ERROR.ordinal());
                requestStatus.setMsg(Constant.ERROR_MSG_CRAWL);
                requestStatus.setStackTrace(ExceptionUtil.stacktraceToString(e));
                requestStatusMapper.updateByExampleSelective(requestStatus, crawlRequestStatusExample);
            } finally {
                String accountName = (String) redisService.get(REDIS_DATABASE + ":tmp:token-busy:" + queryRouteVo.getUniqueId());
                redisService.del(REDIS_DATABASE + ":tmp:token-busy:" + queryRouteVo.getUniqueId());
                redisService.del(REDIS_DATABASE + ":tmp:token-busy:" + accountName);
                ConsumerPull.currentJobs.remove(queryRouteVo.getSpotId() + queryRouteVo.getHostCode());
            }
            return "---> " + queryRouteVo.getSpotId() + " - " + queryRouteVo.getHostCode() + " - " + queryRouteVo.getLogId() + " -> 0";
        });
    }

    @Autowired
    CrawlRequestLogMapper logMapper;

    public void addLog(Boolean addDataId, String businessName, String stepName, String msg, QueryRouteVo queryRouteVo)
    {
        if (StringUtils.isEmpty(msg)) {
            log.info(getLogPrefix(queryRouteVo.getSpotId(), queryRouteVo.getHostCode()) + stepName);
        } else {
            log.info(getLogPrefix(queryRouteVo.getSpotId(), queryRouteVo.getHostCode()) + stepName + "\n" + msg);
        }

        Long lastTimePoint = (Long) redisService.get(REDIS_DATABASE + ":tmp:lastTimePoint:" + queryRouteVo.getUniqueId());
        long timePoint = System.currentTimeMillis();
        CrawlRequestLog requestLog = new CrawlRequestLog();
        requestLog.setLogId(queryRouteVo.getLogId());
        requestLog.setHostCode(queryRouteVo.getHostCode());
        requestLog.setMsg(msg);
        requestLog.setSpotId(queryRouteVo.getSpotId());
        requestLog.setBusinessName(businessName);
        requestLog.setTimePoint(timePoint);
        redisService.set(REDIS_DATABASE + ":tmp:lastTimePoint:" + queryRouteVo.getUniqueId(), timePoint, 360L);
        if (null != lastTimePoint) {
            requestLog.setOffsetTime(timePoint - lastTimePoint);
        }
        if (null != addDataId && addDataId) {
            Long dataId = redisService.generateId(REDIS_DATABASE + ":tmp:log-dataId:" + queryRouteVo.getUniqueId(), 360L);
            requestLog.setDataId(dataId);
        }
        requestLog.setFromPort(queryRouteVo.getDeparturePortEn());
        requestLog.setToPort(queryRouteVo.getDestinationPortEn());
        requestLog.setStepName(stepName);
        Long aLong = redisService.generateId(REDIS_DATABASE + ":tmp:log-step-num:" + queryRouteVo.getUniqueId(), 360L);
        requestLog.setStepNum(aLong);
        logMapper.insertSelective(requestLog);
    }

    public String getLogPrefix(String spotId, String hostCode)
    {
        return (StringUtils.isEmpty(spotId) ? "" : (spotId + " - ")) + hostCode + " - ";
    }

}
