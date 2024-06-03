package com.codechen.scaffold.service;

/**
 * @author：Java陈序员
 * @date：2024/6/3 14:44
 * @description：
 */

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobTest extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Quart Job Test");
        System.out.println(jobExecutionContext);
    }
}
