package com.zhyyt.nursery.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j (Swagger) API文档配置
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("智慧托育综合应用平台 API")
                        .description("智慧托育综合应用平台接口文档")
                        .version("1.0.0")
                        .contact(new Contact().name("nursery").email("dev@nursery.com")));
    }
}
