package com.djk.core.service;

import com.djk.core.config.SpringUtil;
import com.djk.core.vo.QueryRouteVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    private static final int CORE_THREAD_COUNT = 10;
    private static final int MAX_THREAD_COUNT = 15;
    private static ArrayBlockingQueue queue = new ArrayBlockingQueue<>(MAX_THREAD_COUNT * 2);
    public static ExecutorService POOL = new ThreadPoolExecutor(
            CORE_THREAD_COUNT,
            MAX_THREAD_COUNT,
            10,
            TimeUnit.SECONDS,
            queue,
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public void doBusiness(QueryRouteVo queryRouteVo)
    {
        if (queue.size() <= MAX_THREAD_COUNT) {
            for (String beanName : target) {
                POOL.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        CrawlService crawlService = (CrawlService) SpringUtil.getBean(beanName);
                        crawlService.queryData(queryRouteVo);
                    }
                });
            }
        }
        log.info("爬数线程数大于" + MAX_THREAD_COUNT + ", 跳过");
    }

//    @Scheduled(cron = "0/2 * * * * ?")
//    public void aaa()
//    {
//        System.out.println(queue.size());
//    }
}
