package com.djk.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Configuration
public class SpringUtil implements ApplicationContextAware
{
    private static Logger logger = LoggerFactory.getLogger(SpringUtil.class);
    private static ApplicationContext applicationContext;

    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        logger.info("ApplicationContext配置成功,applicationContext对象：" + SpringUtil.applicationContext);
    }

    /**
     * @return {@link ApplicationContext}
     */
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    /**
     * @param name
     * @return {@link Object}
     */
    public static Object getBean(String name)
    {
        return getApplicationContext().getBean(name);
    }

    /**
     * @param clazz
     * @return {@link T}
     */
    public static <T> T getBean(Class<T> clazz)
    {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @param name
     * @param clazz
     * @return {@link T}
     */
    public static <T> T getBean(String name, Class<T> clazz)
    {
        return getApplicationContext().getBean(name, clazz);
    }

}
