package com.codechen.scaffold.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author cyl
 * @date 2023-03-22 16:07
 * @description MyBatisPlus 配置类
 */
@Configuration
@MapperScan(basePackages = {"com.codechen.scaffold.mapper"})
public class MyBatisPlusConfig {
}
