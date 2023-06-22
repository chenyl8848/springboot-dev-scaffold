package com.cyl.scaffold.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author cyl
 * @date 2023-03-28 11:36
 * @description 响应常量枚举类
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),

    VALID_PARAMS_ERROR(1001, "参数校验异常"),

    SYS_USER_USERNAME_EXISTS(1101,"用户名已经存在"),
    SYS_USER_PHONE_EXISTS(1102,"用户手机号已经存在"),
    SYS_USER_NOT_EXISTS(1103,"用户不存在"),

    SYS_ROLE_ROLE_CODE_EXISTS(1201,"角色编码已经存在"),
    SYS_ROLE_NOT_EXISTS(1202,"角色不存在"),

    SYS_MENU_MENUCODE_EXISTS(1301,"菜单编码已经存在"),
    SYS_MENU_NOT_EXISTS(1302,"菜单不存在"),
    ;

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
