package com.codechen.scaffold.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author cyl
 * @date 2023-03-22 16:06
 * @description swagger 配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 文档信息
     * @return
     */
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot 开发脚手架")
                .description("SpringBoot 快速开发脚手架")
                .contact(new Contact("Java陈序员", "https://github.com/chenyl8848", "1063415880@qq.com"))
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket docket(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.codechen.scaffold.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
