package com.codechen.scaffold.common.util;

import com.codechen.scaffold.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author：Java陈序员
 * @date：2025-3-11 11:20
 * @description：线程上下文工具类
 */
public class ThreadMdcUtil {

    /**
     * 生成唯一标识
     *
     * @return
     */
    public static String generateTraceId() {
        SnowflakeIdUtil snowflakeIdUtil = new SnowflakeIdUtil(0, 0);
        return snowflakeIdUtil.nextId() + "";
    }

    private static void setTraceIdIfAbsent() {
        if (StringUtils.isBlank(MDC.get(CommonConstant.TRACE_ID_KEY))) {
            MDC.put(CommonConstant.TRACE_ID_KEY, generateTraceId());
        }
    }

    /**
     * 用于父线程向线程池提交任务时，将自身 MDC 中的数据复制给子线程
     *
     * @param callable
     * @param context
     * @param <T>
     * @return
     */
    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }

            setTraceIdIfAbsent();

            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * 用于父线程向线程池提交任务时，将自身 MDC 中的数据复制给子线程
     *
     * @param runnable
     * @param context
     * @return
     */
    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }

            setTraceIdIfAbsent();

            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
