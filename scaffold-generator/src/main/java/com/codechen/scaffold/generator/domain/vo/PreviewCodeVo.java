package com.codechen.scaffold.generator.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date：2024-11-21 15:24
 * @description：代码预览
 */
@ApiModel(description = "代码预览")
@Data
public class PreviewCodeVo {

    /** 模板名称 */
    @ApiModelProperty(value = "模板名称")
    private String templateName;

    /** 预览代码 */
    @ApiModelProperty(value = "预览代码")
    private String code;
}
