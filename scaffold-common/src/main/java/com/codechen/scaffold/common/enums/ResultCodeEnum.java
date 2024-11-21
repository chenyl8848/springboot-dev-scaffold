package com.codechen.scaffold.common.enums;

import lombok.Getter;

/**
 * @author：Java陈序员
 * @date 2023-03-28 11:36
 * @description 响应常量枚举类
 */
@Getter
public enum ResultCodeEnum {

    /** 成功 */
    SUCCESS(200, "成功"),

    /** 请求未授权 */
    UN_AUTHORIZED(401, "请求未授权"),

    /** 未找到请求 */
    NOT_FOUND(404, "未找到请求"),

    /** 不支持当前请求方法 */
    METHOD_NOT_SUPPORT(405, "不支持当前请求方法"),

    /** 内部服务异常 */
    FAIL(500, "内部服务异常"),

    /** 参数错误 */
    ILLEGAL_PARAM(1000, "参数错误"),

    /** 必填参数为空 */
    PARAM_ABSENT(1001, "必填参数为空"),

    /** 用户异常 */
    SYS_USER_USERNAME_EXISTS(1101,"用户名已经存在"),
    SYS_USER_PHONE_EXISTS(1102,"用户手机号已经存在"),
    SYS_USER_NOT_EXISTS(1103,"用户不存在"),
    SYS_USER_USERNAME_NOT_EXISTS_OR_PASSWORD_ERROR(1104, "用户名不存在或密码错误"),
    SYS_USER_USERNAME_IS_NULL(1105, "用户名不能为空"),

    /** 角色异常 */
    SYS_ROLE_ROLE_CODE_EXISTS(1201,"角色编码已经存在"),
    SYS_ROLE_NOT_EXISTS(1202,"角色不存在"),

    /** 菜单异常 */
    SYS_MENU_MENU_CODE_EXISTS(1301,"菜单编码已经存在"),
    SYS_MENU_NOT_EXISTS(1302,"菜单不存在"),
    ;

    private final Integer code;
    private final String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
