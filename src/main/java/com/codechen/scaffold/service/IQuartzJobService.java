package com.codechen.scaffold.service;

import com.codechen.scaffold.domain.request.QuartzJobDetailRequest;
import com.codechen.scaffold.domain.request.QuartzJobRequest;
import com.codechen.scaffold.domain.vo.QuartzJobVo;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024/6/3 14:43
 * @description：定时任务接口
 */
public interface IQuartzJobService {

    public void addJob(QuartzJobRequest request) throws SchedulerException;

    public void updateJob(QuartzJobRequest request) throws SchedulerException;

    public void deleteJob(QuartzJobDetailRequest request) throws SchedulerException;

    public void pauseJob(QuartzJobDetailRequest request) throws SchedulerException;

    public void resumeJob(QuartzJobDetailRequest request) throws SchedulerException;

    public List<QuartzJobVo> jobList() throws SchedulerException;

    public QuartzJobVo jobDetail(QuartzJobDetailRequest request) throws SchedulerException;

}
