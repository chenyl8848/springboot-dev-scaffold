package com.codechen.scaffold.controller;

import com.codechen.scaffold.core.entity.Result;
import com.codechen.scaffold.domain.request.QuartzJobDetailRequest;
import com.codechen.scaffold.domain.request.QuartzJobRequest;
import com.codechen.scaffold.domain.vo.QuartzJobVo;
import com.codechen.scaffold.service.IQuartzJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024/6/3 14:42
 * @description：定时任务
 */
@RestController
@RequestMapping("/job")
@Api(tags = "定时任务")
public class QuartzJobController {

    @Autowired
    private IQuartzJobService quartzJobService;

    @PostMapping("/")
    @ApiOperation(value = "添加任务")
    public Result addJob(@Validated @RequestBody QuartzJobRequest request) throws SchedulerException {
        quartzJobService.addJob(request);
        return Result.success(null);
    }

    @PutMapping("/")
    @ApiOperation(value = "修改任务")
    public Result updateJob(@Validated @RequestBody QuartzJobRequest request) throws SchedulerException {
        quartzJobService.updateJob(request);
        return Result.success(null);
    }

    @DeleteMapping("/")
    @ApiOperation(value = "删除任务")
    public Result deleteJob(@Validated @RequestBody QuartzJobDetailRequest request) throws SchedulerException {
        quartzJobService.deleteJob(request);
        return Result.success(null);
    }

    @PostMapping("/pause")
    @ApiOperation(value = "暂停任务")
    public Result pauseJob(@Validated @RequestBody QuartzJobDetailRequest request) throws SchedulerException {
        quartzJobService.pauseJob(request);
        return Result.success(null);
    }

    @PostMapping("/resume")
    @ApiOperation(value = "恢复任务")
    public Result resumeJob(@Validated @RequestBody QuartzJobDetailRequest request) throws SchedulerException {
        quartzJobService.resumeJob(request);
        return Result.success(null);
    }

    @GetMapping("/")
    @ApiOperation(value = "任务列表")
    public Result<List<QuartzJobVo>> jobList() throws SchedulerException {
        List<QuartzJobVo> jobList = quartzJobService.jobList();
        return Result.success(jobList);
    }

    @PostMapping("/jobDetail")
    @ApiOperation(value = "任务详情")
    public Result<QuartzJobVo> jobDetail(@Validated @RequestBody QuartzJobDetailRequest request) throws SchedulerException {
        QuartzJobVo quartzJobVo = quartzJobService.jobDetail(request);
        return Result.success(quartzJobVo);
    }
}
