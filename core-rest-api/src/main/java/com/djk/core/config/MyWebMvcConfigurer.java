package com.djk.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer
{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**").addResourceLocations("classpath*:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath*:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath*:/META-INF/resources/webjars/");
    }

    /**
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域的请求域名
                .allowedOrigins("*")
                //是否允许cookie
                .allowCredentials(true)
                //设置允许请求方式
                .allowedMethods("*")
                //设置允许的header属性
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(36000);
    }
}
