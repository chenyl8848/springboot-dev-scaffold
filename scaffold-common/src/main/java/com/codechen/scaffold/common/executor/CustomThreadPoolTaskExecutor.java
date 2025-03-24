package com.codechen.scaffold.common.executor;

import com.codechen.scaffold.common.util.ThreadMdcUtil;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author：Java陈序员
 * @date：2025-3-11 11:17
 * @description：自定义线程池
 */
public class CustomThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    public CustomThreadPoolTaskExecutor() {
        super();
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
