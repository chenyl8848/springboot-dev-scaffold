package com.codechen.scaffold.generator.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：Java陈序员
 * @date：2024-11-21 15:12
 * @description：代码生成请求
 */
@ApiModel(description = "代码生成请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCodeRequest {

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "文件路径")
    private String filePath;
}
