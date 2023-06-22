package com.cyl.scaffold.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author cyl
 * @date 2023-03-28 11:36
 * @description 通用常量枚举类
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CommonCodeEnum {

    NORMAL("0", "正常"),
    DELETED("1", "删除"),

    ENABLE("0", "启用"),
    DISABLE("1", "禁用"),

    TRACE_ID("TRACE_ID", "日志追踪ID"),

    ;

    /** 编码 */
    private String code;
    /** 属性 */
    private String name;
}
