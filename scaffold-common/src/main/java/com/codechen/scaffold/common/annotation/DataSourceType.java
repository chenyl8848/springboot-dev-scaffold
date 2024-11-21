package com.codechen.scaffold.common.annotation;

import com.codechen.scaffold.common.enums.DataSourceTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：Java陈序员
 * @date 2023-04-28 11:17
 * @description 多数据源类型
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceType {

    DataSourceTypeEnum value() default DataSourceTypeEnum.PRIMARY;
}
