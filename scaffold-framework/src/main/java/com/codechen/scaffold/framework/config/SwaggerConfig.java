package com.codechen.scaffold.framework.config;

import com.codechen.scaffold.common.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：Java陈序员
 * @date 2023-03-22 16:06
 * @description swagger 配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.codechen.library.api.web"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getParameterList());
    }

    /** 构建 Api 作者信息 */
    private ApiInfo apiInfo() {

        Contact author = new Contact("Java陈序员", "https://github.com/chenyl8848", "1063415880@qq.com");
        return new ApiInfo(
                "SpringBoot开发脚手架API文档",
                "SpringBoot开发脚手架API文档",
                "1.0.0",
                "",
                author,
                "",
                "",
                new ArrayList()
        );
    }

    /**
     * 添加head参数配置
     */
    private List<RequestParameter> getParameterList() {
        RequestParameterBuilder requestParameterBuilder = new RequestParameterBuilder();
        List<RequestParameter> requestParameterList = new ArrayList<RequestParameter>();
        requestParameterBuilder.name(CommonConstant.TOKEN_KEY)
                .description("Token 令牌")
                .in(ParameterType.HEADER)
                .required(true)
                .build();
        requestParameterList.add(requestParameterBuilder.build());

        return requestParameterList;
    }
}
