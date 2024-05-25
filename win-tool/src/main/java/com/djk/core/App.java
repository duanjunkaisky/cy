package com.djk.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author duanjunkai
 * @date 2024/05/25
 */
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class App
{

    public static void main(String[] args)
    {
        System.setProperty("com.rocketmq.sendMsgWithVipChannel", "false");
        System.setProperty("druid.mysql.usePingMethod", "false");
        SpringApplication.run(App.class, args);
    }
}