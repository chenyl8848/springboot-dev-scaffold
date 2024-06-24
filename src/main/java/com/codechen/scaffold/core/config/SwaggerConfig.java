package com.codechen.scaffold.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.Example;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author：Java陈序员
 * @date 2023-03-22 16:06
 * @description swagger 配置类
 */
@Configuration
// 开启 swagger
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    @Value("${platform.config.auth.tokenKey}")
    private String tokenKey;

    /**
     * 文档信息
     *
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
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.cyl.scaffold.controller"))
//                .paths(PathSelectors.any())
//                .build();

        // 定义全局请求参数
        ArrayList<RequestParameter> paramsList = new ArrayList<>();

        RequestParameterBuilder requestParameterBuilder = new RequestParameterBuilder();

        RequestParameter requestParameter = requestParameterBuilder.name(tokenKey)
                .description("用户token")
                .in("header")
                .required(false)
                .build();
        paramsList.add(requestParameter);

        return new Docket(DocumentationType.OAS_30)
                .enable(true)
                .groupName("webApi")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.codechen.scaffold.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(paramsList);
    }
}
