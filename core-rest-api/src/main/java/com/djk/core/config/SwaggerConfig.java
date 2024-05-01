package com.djk.core.config;

import com.djk.core.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.djk.core")
                .title("后台系统")
                .description("后台相关接口文档")
                .contactName("djk")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
