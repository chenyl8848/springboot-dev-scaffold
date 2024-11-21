package com.codechen.scaffold.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author：Java陈序员
 * @date：2024-11-1 16:55
 * @description：主启动类
 */
@SpringBootApplication
@EnableConfigurationProperties
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class);
    }
}
