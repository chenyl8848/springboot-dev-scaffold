package com.cyl.scaffold.controller;

import com.cyl.scaffold.core.annotation.RequestLimit;
import com.cyl.scaffold.core.constant.ResultCodeEnum;
import com.cyl.scaffold.core.entity.Result;
import com.google.common.util.concurrent.RateLimiter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author cyl
 * @date 2023-07-14 15:19
 * @description 限流控制器
 */
@Api(tags = "限流")
@RestController
@RequestMapping("/limit")
@Slf4j
public class RequestLimitController {

    /**
     * 表示 1s 内允许两个请求
     */
    private final RateLimiter rateLimiter = RateLimiter.create(2.0);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/common")
    @ApiOperation(value = "普通方式限流")
    public Result commonLimit() {

        boolean tryAcquire = rateLimiter.tryAcquire();
        if (!tryAcquire) {
            log.warn("获取令牌桶失败，时间:{}", LocalDateTime.now().format(dateTimeFormatter));
            return Result.fail(500, "系统操作繁忙，请稍后再试");
        }

        log.info("获取令牌桶成功，时间:{}", LocalDateTime.now().format(dateTimeFormatter));
        return Result.success(null);
    }

    @GetMapping("/annotaion")
    @ApiOperation(value = "注解方式限流")
    @RequestLimit(key = "/limit/annotation", permitsPerSecond = 2.0, timeout = 0)
    public Result annotationLimit() {

        return Result.success(null);
    }
}
