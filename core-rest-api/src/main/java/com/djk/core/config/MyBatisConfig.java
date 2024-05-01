package com.djk.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 *
 * @author duanjunkai
 * @date 2024/05/01
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.djk.core.mapper","com.djk.core.dao"})
public class MyBatisConfig {
}
