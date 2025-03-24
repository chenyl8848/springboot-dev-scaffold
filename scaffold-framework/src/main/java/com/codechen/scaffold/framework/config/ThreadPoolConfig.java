package com.codechen.scaffold.framework.config;

import com.codechen.scaffold.common.executor.CustomThreadPoolTaskExecutor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author：Java陈序员
 * @date：2025-3-11 11:39
 * @description：线程池配置类
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "codechen.executor")
public class ThreadPoolConfig {

    /** 核心线程数 */
    private Integer corePoolSize = Runtime.getRuntime().availableProcessors();

    /** 最大线程数 */
    private Integer maxPoolSize = Runtime.getRuntime().availableProcessors() * 2;

    /** 缓冲队列大小 */
    private Integer queueCapacity = 500;

    /** 允许线程的空闲时间 */
    private Integer keepAlive = 60;

    private String threadPrefixName = "custom-thread-pool-";

    @Bean("taskExecutor")
    public CustomThreadPoolTaskExecutor customThreadPoolTaskExecutor() {
        // 使用自定义线程池，避免 TraceId 丢失
        CustomThreadPoolTaskExecutor taskExecutor = new CustomThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(corePoolSize);
        // 最大线程数
        taskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        // 缓冲队列
        taskExecutor.setQueueCapacity(queueCapacity);
        // 允许线程的空闲时间
        taskExecutor.setKeepAliveSeconds(keepAlive);
        // 拒绝策略
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程池名称前缀
        taskExecutor.setThreadNamePrefix(threadPrefixName);
        // 初始化启动线程池
        taskExecutor.initialize();

        return taskExecutor;
    }
}
