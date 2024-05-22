package com.codechen.scaffold.core.aspect;

import com.alibaba.fastjson.JSON;
import com.codechen.scaffold.core.annotation.RequestLimit;
import com.codechen.scaffold.core.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author：Java陈序员
 * @date 2023-07-13 16:36
 * @description 限流切面
 */
@Component
@Aspect
public class RequestLimitAspect {

    /**
     * 定义一个 map 用来缓存不同资源的限流规则
     */
    private final Map<String, RateLimiter> rateLimiterMap = Maps.newConcurrentMap();

    /**
     * guava 缓存
     */
    private final Cache<String, RateLimiter> cache = CacheBuilder.newBuilder().build();

    @Pointcut("@annotation(com.codechen.scaffold.core.annotation.RequestLimit)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取 RequestLimit 注解
        RequestLimit requestLimit = method.getAnnotation(RequestLimit.class);

        if (Objects.nonNull(requestLimit)) {
            // 限流注解不为空
            // 获取唯一 key
            String key = requestLimit.key();

            RateLimiter rateLimiter = null;
//            if (!rateLimiterMap.containsKey(key)) {
//                // map 中没有 key
//                rateLimiter = RateLimiter.create(requestLimit.permitsPerSecond());
//                rateLimiterMap.put(key, rateLimiter);
//            }

//            rateLimiter = rateLimiterMap.get(key);

            if (!cache.asMap().containsKey(key)) {
                rateLimiter = RateLimiter.create(requestLimit.permitsPerSecond());
                cache.put(key, rateLimiter);
            }
            rateLimiter = cache.getIfPresent(key);

            boolean tryAcquire = rateLimiter.tryAcquire(requestLimit.timeout(), requestLimit.timeunit());

            if (!tryAcquire) {
                // 没有获取到令牌
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                String result = JSON.toJSONString(Result.fail(500, requestLimit.message()));

                // todo
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.println(new ObjectMapper().writeValueAsString(result));

                return null;
            }
        }

        return joinPoint.proceed();
    }
}
