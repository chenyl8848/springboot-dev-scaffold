package com.codechen.scaffold.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author：Java陈序员
 * @date 2023-03-22 15:40
 * @description 脚手架主启动类
 */
// 开启重试
@EnableRetry
// 开启缓存
@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = {"com.codechen.scaffold"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
