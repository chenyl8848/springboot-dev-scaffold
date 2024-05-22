package com.codechen.scaffold.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author：Java陈序员
 * @date 2023-07-13 16:35
 * @description 限流注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RequestLimit {

    /**
     * 资源的 key 唯一
     * 作用：不同的接口，不同的限流规则
     *
     * @return
     */
    String key() default "";

    /**
     * 令牌桶的数量，即 1s 内最多的访问限制次数
     *
     * @return
     */
    double permitsPerSecond();

    /**
     * 获取令牌的最大超时时间
     *
     * @return
     */
    long timeout();

    /**
     * 获取令牌的最大超时时间单位，默认：毫秒
     *
     * @return
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 获取令牌失败的文字提示
     *
     * @return
     */
    String message() default "系统操作繁忙，请稍后重试";
}
