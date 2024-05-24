package com.codechen.scaffold.domain.vo;

import com.codechen.scaffold.core.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date：2024/5/24 17:27
 * @description：用户信息
 */
@Data
@ApiModel(value = "用户信息")
public class SysUserVo extends AbstractEntity {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
