package com.codechen.scaffold.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author：Java陈序员
 * @date：2024/6/3 15:43
 * @description：定时任务
 */
@Data
@ApiModel(description = "定时任务")
public class QuartzJobRequest {

    @ApiModelProperty(value = "任务类路径", required = true)
    @NotBlank(message = "任务类路径不能为空")
    private String jobClazz;

    @ApiModelProperty(value = "任务名称", required = true)
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "任务数据")
    private Map<String,Object> jobData;

    @ApiModelProperty(value = "Cron表达式")
    private String cron;

    @ApiModelProperty(value = "描述")
    private String description;
}
