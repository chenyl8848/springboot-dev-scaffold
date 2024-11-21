package com.codechen.scaffold.job.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date：2024/6/3 15:43
 * @description：定时任务详情
 */
@Data
@ApiModel(description = "定时任务详情")
public class QuartzJobDetailRequest {

    @ApiModelProperty(value = "任务名称", required = true)
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;
}
