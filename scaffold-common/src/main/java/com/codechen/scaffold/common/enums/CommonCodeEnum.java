package com.codechen.scaffold.common.enums;

import lombok.Getter;

/**
 * @author：Java陈序员
 * @date 2023-03-28 11:36
 * @description 通用常量枚举类
 */
@Getter
public enum CommonCodeEnum {

    NORMAL("0", "正常"),
    DELETED("1", "删除"),

    ENABLE("0", "启用"),
    DISABLE("1", "禁用"),

    ;

    /** 编码 */
    private final String code;
    /** 属性 */
    private final String name;

    private CommonCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
