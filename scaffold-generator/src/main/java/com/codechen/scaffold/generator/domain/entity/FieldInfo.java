package com.codechen.scaffold.generator.domain.entity;

import lombok.Data;

/**
 * @author：Java陈序员
 * @date：2024-11-4 9:53
 * @description：类属性
 */
@Data
public class FieldInfo {

    /** 列名 */
    private String columnName;

    /** 列类型 */
    private String columnType;

    /** 数据类型 */
    private String dataType;

    /** 属性名 */
    private String fileName;

    /** 属性类型 */
    private String fieldType;

    /** 属性注释 */
    private String filedComment;

    /** 是否主键 1 - 是 0 - 否*/
    private Integer isPk;

}
