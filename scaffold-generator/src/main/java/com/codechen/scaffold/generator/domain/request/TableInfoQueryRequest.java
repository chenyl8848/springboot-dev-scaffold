package com.codechen.scaffold.generator.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：Java陈序员
 * @date：2025-5-6 16:48
 * @description：数据库表查询实体
 */
@ApiModel(description = "数据库表查询实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfoQueryRequest {

    @ApiModelProperty(value = "表名")
    private String tableName;
}
