package com.codechen.scaffold.service.impl;

import com.codechen.scaffold.core.exception.GlobalException;
import com.codechen.scaffold.domain.request.QuartzJobDetailRequest;
import com.codechen.scaffold.domain.request.QuartzJobRequest;
import com.codechen.scaffold.domain.vo.QuartzJobTriggerVo;
import com.codechen.scaffold.domain.vo.QuartzJobVo;
import com.codechen.scaffold.service.IQuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.TriggerUtils;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author：Java陈序员
 * @date：2024/6/3 15:53
 * @description：定时任务接口实现类
 */
@Service
@Slf4j
public class QuartzJobServiceImpl implements IQuartzJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void addJob(QuartzJobRequest request) throws SchedulerException {

        String jobClazz = request.getJobClazz();
        String jobName = request.getJobName();
        String jobGroup = request.getJobGroup();
        String cron = request.getCron();
        String description = request.getDescription();
        Map<String, Object> jobData = request.getJobData();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        if (isJobExist(jobKey)) {
            throw new GlobalException("任务已存在!");
        }

        Class<? extends Job> jobClass = null;
        try {
            jobClass = (Class<? extends Job>) Class.forName(jobClazz);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new GlobalException("找不到任务类：" + jobClazz);
        }

        JobDataMap jobDataMap = new JobDataMap();
        if (Objects.nonNull(jobData)) {
            jobData.forEach(jobDataMap::put);
        }

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .setJobData(jobDataMap)
                .build();

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

        String triggerId = jobKey.getName() + jobKey.getGroup();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder)
                .withIdentity(triggerId)
                .withDescription(description)
                .build();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);

        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }


    @Override
    public void updateJob(QuartzJobRequest request) throws SchedulerException {
        String jobName = request.getJobName();
        String jobGroup = request.getJobGroup();
        String cron = request.getCron();
        String description = request.getDescription();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        String triggerId = jobKey.getName() + jobKey.getGroup();
        if (!isJobExist(jobKey)) {
            throw new GlobalException("任务不存在!");
        }

        TriggerKey triggerKey = TriggerKey.triggerKey(triggerId);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder)
                .withIdentity(triggerId)
                .withDescription(description)
                .build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }

    @Override
    public void deleteJob(QuartzJobDetailRequest request) throws SchedulerException {
        String jobName = request.getJobName();
        String jobGroup = request.getJobGroup();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        if (!isJobExist(jobKey)) {
            throw new GlobalException("任务不存在!");
        }

        scheduler.pauseJob(jobKey);
        scheduler.deleteJob(jobKey);
    }

    @Override
    public void pauseJob(QuartzJobDetailRequest request) throws SchedulerException {
        String jobName = request.getJobName();
        String jobGroup = request.getJobGroup();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        if (!isJobExist(jobKey)) {
            throw new GlobalException("任务不存在!");
        }

        scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeJob(QuartzJobDetailRequest request) throws SchedulerException {
        String jobName = request.getJobName();
        String jobGroup = request.getJobGroup();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        if (!isJobExist(jobKey)) {
            throw new GlobalException("任务不存在!");
        }

        scheduler.resumeJob(jobKey);
    }

    @Override
    public QuartzJobVo jobDetail(QuartzJobDetailRequest request) throws SchedulerException {
        String jobName = request.getJobName();
        String jobGroup = request.getJobGroup();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        return getQuartzJobVoByJobKey(jobKey);
    }

    @Override
    public List<QuartzJobVo> jobList() throws SchedulerException {
        GroupMatcher<JobKey> groupMatcher = GroupMatcher.anyGroup();
        List<QuartzJobVo> jobList = Lists.newArrayList();
        for (JobKey jobKey : scheduler.getJobKeys(groupMatcher)) {
            QuartzJobVo quartzJobVo = getQuartzJobVoByJobKey(jobKey);
            jobList.add(quartzJobVo);
        }

        return jobList;
    }

    private boolean isJobExist(JobKey jobKey) throws SchedulerException {
        if (scheduler.checkExists(jobKey)) {
            return true;
        } else {
            return false;
        }
    }

    private QuartzJobVo getQuartzJobVoByJobKey(JobKey jobKey) throws SchedulerException {
        QuartzJobVo quartzJobVo = new QuartzJobVo();

        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        List<Trigger> triggerList = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

        quartzJobVo.setJobClazz(jobDetail.getJobClass().toString());
        quartzJobVo.setJobName(jobKey.getName());
        quartzJobVo.setJobGroup(jobKey.getGroup());
        quartzJobVo.setJobData(jobDetail.getJobDataMap());
        quartzJobVo.setDescription(jobDetail.getDescription());

        List<QuartzJobTriggerVo> triggerVoList = triggerList.stream().map(trigger -> {
            QuartzJobTriggerVo triggerVo = new QuartzJobTriggerVo();

            triggerVo.setTriggerName(trigger.getKey().getName());
            triggerVo.setTriggerGroup(trigger.getKey().getGroup());
            triggerVo.setDescription(trigger.getDescription());

            if (trigger instanceof CronTriggerImpl) {
                CronTriggerImpl cronTrigger = (CronTriggerImpl) trigger;
                String cron = cronTrigger.getCronExpression();
                triggerVo.setCron(cron);

                List<Date> dates = TriggerUtils.computeFireTimes(cronTrigger, null, 10);
                triggerVo.setRecentFireTimes(dates);
            }

            try {
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                triggerVo.setState(triggerState.toString());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

            return triggerVo;
        }).collect(Collectors.toList());

        quartzJobVo.setTriggers(triggerVoList);

        return quartzJobVo;
    }
}
