package com.codechen.scaffold.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author：Java陈序员
 * @date 2023-03-28 11:36
 * @description 响应常量枚举类
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),

    NO_ACCESS_PRIVILEGES(401, "没有访问权限"),

    VALID_PARAMS_ERROR(1001, "参数校验异常"),


    SYS_USER_USERNAME_EXISTS(1101,"用户名已经存在"),
    SYS_USER_PHONE_EXISTS(1102,"用户手机号已经存在"),
    SYS_USER_NOT_EXISTS(1103,"用户不存在"),
    SYS_USER_USERNAME_NOT_EXISTS_OR_PASSWORD_ERROR(1104, "用户名不存在或密码错误"),
    SYS_USER_USERNAME_IS_NULL(1105, "用户名不能为空"),

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
