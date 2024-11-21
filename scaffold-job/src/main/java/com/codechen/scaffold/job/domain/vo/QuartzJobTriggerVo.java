package com.codechen.scaffold.job.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024/6/3 15:49
 * @description：定时任务触发器详情
 */
@ApiModel(value = "触发器")
@Data
public class QuartzJobTriggerVo {

    @ApiModelProperty(value = "触发器名称")
    private String triggerName;

    @ApiModelProperty(value = "触发器组名")
    private String triggerGroup;

    @ApiModelProperty(value = "Cron表达式")
    private String cron;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "最近触发时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<Date> recentFireTimes;

}
