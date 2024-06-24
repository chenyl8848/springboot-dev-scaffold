package com.codechen.scaffold.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobExecutionContextImpl;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author：Java陈序员
 * @date：2024/6/24 10:32
 * @description：
 */
@Component
@Slf4j
public class JobTest extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("JobTest:{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("username"));
    }
}
