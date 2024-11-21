package com.codechen.scaffold.job.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author：Java陈序员
 * @date：2024/6/3 15:49
 * @description： 定时任务
 */
@ApiModel(value = "定时任务")
@Data
public class QuartzJobVo {

    @ApiModelProperty(value = "任务类路径")
    private String jobClazz;

    @ApiModelProperty(value = "任务类名")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "任务数据")
    private Map<String, Object> jobData;

    @ApiModelProperty(value = "Cron表达式")
    private String cron;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "触发器")
    private List<QuartzJobTriggerVo> triggers;
}
