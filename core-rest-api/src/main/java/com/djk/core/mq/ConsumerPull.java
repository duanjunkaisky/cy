package com.djk.core.mq;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.djk.core.mapper.CrawlRequestStatusMapper;
import com.djk.core.model.CrawlRequestStatus;
import com.djk.core.model.CrawlRequestStatusExample;
import com.djk.core.service.CrawlChain;
import com.djk.core.vo.QueryRouteVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.*;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author duanjunkai
 * @date 2024/05/02
 */
@Component
@Slf4j
public class ConsumerPull implements CommandLineRunner
{

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.group}")
    private String group;

    @Value("${rocketmq.producer.topic}")
    private String topic;

    @Value("${rocketmq.producer.mqMax}")
    private int mqMax;

    @Value("${rocketmq.maxCrawlTime}")
    private int maxCrawlTime;

    @Value("${rocketmq.maxCrawlCount}")
    private int maxCrawlCount;

    @Value("${rocketmq.pull}")
    private boolean isPull;

    @Autowired
    CrawlChain crawlChain;

    @Autowired
    CrawlRequestStatusMapper requestStatusMapper;

    public static ConcurrentHashMap<String, QueryRouteVo> currentJobs = new ConcurrentHashMap<>();

    /**
     * @throws MQClientException
     */
    public void consumeMessage() throws MQClientException
    {
        // 1. 实例化对象
        final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService(group);
        // 2. 设置NameServer
        scheduleService.getDefaultMQPullConsumer().setNamesrvAddr(namesrvAddr);
        // 3. 设置消费组为集群模式
        scheduleService.setMessageModel(MessageModel.CLUSTERING);
        scheduleService.getDefaultMQPullConsumer().setInstanceName("consumerPull");
        // 4. 注册拉取回调函数
        scheduleService.registerPullTaskCallback(topic, new PullTaskCallback()
        {
            @Override
            public void doPullTask(MessageQueue mq, PullTaskContext context)
            {
                if (isPull) {
                    // 5.从上下文中获取MQPullConsumer对象，此处其实就是DefaultMQPullConsumer。
                    MQPullConsumer consumer = context.getPullConsumer();
                    try {
                        // 6.获取该消费组的该队列的消费进度
                        long offset = consumer.fetchConsumeOffset(mq, false);
                        if (offset < 0) {
                            offset = 0;
                        }
                        // 7.拉取消息，pull()方法在DefaultMQPullConsumer有具体介绍
                        PullResult pullResult = consumer.pull(mq, "*", offset, mqMax);
                        switch (pullResult.getPullStatus()) {
                            case FOUND:
                                for (int i = 0; i < pullResult.getMsgFoundList().size(); i++) {
                                    for (String key : currentJobs.keySet()) {
                                        QueryRouteVo job = currentJobs.get(key);
                                        if ((new Date()).getTime() - job.getStartTime() > maxCrawlTime) {
                                            log.info("爬取超过[" + maxCrawlTime / 1000 + "秒] -> \n" + JSONObject.toJSONString(job));
                                            currentJobs.remove(key);
                                            break;
                                        }
                                    }
                                    synchronized (currentJobs) {
                                        if (currentJobs.keySet().size() < maxCrawlCount) {
                                            MessageExt message = pullResult.getMsgFoundList().get(i);
                                            QueryRouteVo queryRouteVo = JSON.parseObject(message.getBody(), QueryRouteVo.class);

                                            CrawlRequestStatusExample crawlRequestStatusExample = new CrawlRequestStatusExample();
                                            crawlRequestStatusExample.createCriteria().andRequestIdEqualTo(String.valueOf(queryRouteVo.getRequestId()));
                                            List<CrawlRequestStatus> crawlRequestStatuses = requestStatusMapper.selectByExample(crawlRequestStatusExample);
                                            if (null == crawlRequestStatuses || crawlRequestStatuses.isEmpty()) {
                                                log.info("消费消息,开始爬取: \n " + JSONObject.toJSONString(queryRouteVo));
                                                currentJobs.put(String.valueOf(queryRouteVo.getRequestId()), queryRouteVo);
                                                crawlChain.doBusiness(queryRouteVo);
                                            }
                                        } else {
                                            i--;
                                            Thread.sleep(10);
                                        }
                                    }
                                }
                                break;
                            case NO_NEW_MSG:
                                Thread.sleep(10);
                                break;
                            default:
                                break;
                        }
                        // 8.更新消费组该队列消费进度
                        consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());
                        // 9.设置下次拉取消息时间间隔，单位毫秒
                        context.setPullNextDelayTimeMillis(10);
                    } catch (Exception e) {
                        log.error("拉去消息异常");
                        log.error(ExceptionUtil.getMessage(e));
                        log.error(ExceptionUtil.stacktraceToString(e));
                    }
                }
            }
        });
        scheduleService.start();
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception
    {
        consumeMessage();
    }

    /**
     * 每天12点触发
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void delRequestStatus()
    {

    }

}
