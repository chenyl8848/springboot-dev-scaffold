package com.codechen.scaffold.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：Java陈序员
 * @date 2023-05-09 16:59
 * @description 导出字段注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportField {

    /** 列宽 */
    int columnWidth() default 100;

    /** 列标题 */
    String columnName();

}
