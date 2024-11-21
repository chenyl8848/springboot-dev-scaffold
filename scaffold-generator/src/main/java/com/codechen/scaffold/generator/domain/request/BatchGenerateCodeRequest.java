package com.codechen.scaffold.generator.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024-11-21 15:12
 * @description：批量代码生成请求
 */
@ApiModel(description = "批量代码生成请求")
@Data
public class BatchGenerateCodeRequest {

    @ApiModelProperty(value = "表名")
    private List<String> tableNames;

    @ApiModelProperty(value = "文件路径")
    private String filePath;
}
