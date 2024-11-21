package com.codechen.scaffold.admin.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author：Java陈序员
 * @date 2023-06-25 15:50
 * @description 登录用户实体vo
 */
@Data
public class LoginUserRequest {

    /** 用户名 */
    @NotBlank(message = "登录用户名不能为空")
    private String username;

    /** 密码 */
    @NotBlank(message = "登录密码不能为空")
    private String password;
}
