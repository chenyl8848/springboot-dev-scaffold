package com.codechen.scaffold.core.config;

import com.codechen.scaffold.core.filter.CorsFilter;
import com.codechen.scaffold.core.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author：Java陈序员
 * @date 2023-03-28 16:38
 * @description 过滤器配置类
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registerCorsFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CorsFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean registerLogFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(Integer.MIN_VALUE + 1);
        return filterRegistrationBean;
    }
}
