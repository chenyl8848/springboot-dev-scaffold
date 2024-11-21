package com.codechen.scaffold.admin.domain.vo;

import com.codechen.scaffold.framework.domain.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024/5/24 17:27
 * @description：用户信息
 */
@Data
@ApiModel(value = "用户信息")
public class SysUserVo extends BaseVo {

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

    @ApiModelProperty(value = "角色信息")
    private List<SysRoleVo> roles;

    @ApiModelProperty(value = "菜单信息")
    private List<SysMenuVo> menus;

    @ApiModelProperty(value = "按钮信息")
    private List<String> buttons;
}
