package com.codechen.scaffold.service;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author：Java陈序员
 * @date：2024/6/3 14:45
 * @description：
 */
//@Component
public class QuartzJobTestConfig {

    @Bean
    public JobDetail quartzTestJobDetail() {
        return JobBuilder.newJob(QuartzJobTest.class)
                .withIdentity(QuartzJobTest.class.getSimpleName())
                .storeDurably()
                .usingJobData("data", "test")
                .build();
    }

    @Bean
    public Trigger quartzTestTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(QuartzJobTest.class.getSimpleName())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                .build();
    }
}
