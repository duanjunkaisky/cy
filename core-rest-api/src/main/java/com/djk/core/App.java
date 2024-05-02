package com.djk.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author duanjunkai
 * @date 2024/04/24
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@RefreshScope
@EnableScheduling
@MapperScan("com.djk.core.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class App {

    public static void main(String[] args) {
        System.setProperty("com.rocketmq.sendMsgWithVipChannel", "false");
        System.setProperty("druid.mysql.usePingMethod", "false");
        SpringApplication.run(App.class, args);
    }
}

