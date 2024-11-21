package com.codechen.scaffold.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：Java陈序员
 * @date 2023-05-09 16:59
 * @description 是否允许导出
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableExport {

    String fileName();
}
