package com.codechen.scaffold.domain.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:33
 * @description 系统用户
 */
@Data
@ApiModel(description = "系统用户")
public class SysUserRequest {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    @Pattern(regexp = "^1((34[0-8])|(8\\d{2})|(([35][0-35-9]|4[579]|66|7[35678]|9[1389])\\d{1}))\\d{7}$", message = "手机号格式错误")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @Email
    private String email;
}
