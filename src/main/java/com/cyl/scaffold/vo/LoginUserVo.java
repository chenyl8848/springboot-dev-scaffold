package com.cyl.scaffold.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author cyl
 * @date 2023-06-25 15:50
 * @description 登录用户实体vo
 */
@Data
public class LoginUserVo {

    /** 用户名 */
    @NotBlank(message = "登录用户名不能为空")
    private String username;

    /** 密码 */
    @NotBlank(message = "登录密码不能为空")
    private String password;
}
