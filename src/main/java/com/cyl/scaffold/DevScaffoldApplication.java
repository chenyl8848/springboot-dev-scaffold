package com.cyl.scaffold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author cyl
 * @date 2023-03-22 15:40
 * @description 脚手架主启动类
 */
// 开启重试
@EnableRetry
// 开启缓存
@EnableCaching
@SpringBootApplication
public class DevScaffoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevScaffoldApplication.class, args);
    }
}
