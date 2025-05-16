package com.codechen.scaffold.generator.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：Java陈序员
 * @date：2024-11-5 16:05
 * @description：表信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {

    /** 表名 */
    private String tableName;

    /** 表注释 */
    private String tableComment;

    /** 类名 */
    private String className;
}
