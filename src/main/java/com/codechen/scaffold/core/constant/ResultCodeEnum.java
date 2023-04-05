package com.codechen.scaffold.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author cyl
 * @date 2023-03-28 11:36
 * @description 响应常量枚举类
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    FAIL(500, "服务器出现异常"),
    VALID_PARAMS_ERROR(1000, "校验参数错误"),

    ;

    /** 编码 */
    private Integer code;
    /** 属性 */
    private String name;
}
