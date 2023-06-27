package com.cyl.scaffold.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyl.scaffold.core.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author cyl
 * @date 2023-06-15 15:33
 * @description 系统用户
 */
@Data
@ApiModel(description = "系统用户")
@TableName("sys_user")
public class SysUser extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "昵称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "手机")
    @TableField("phone")
    @Pattern(regexp = "^1((34[0-8])|(8\\d{2})|(([35][0-35-9]|4[579]|66|7[35678]|9[1389])\\d{1}))\\d{7}$", message = "手机号格式错误")
    private String phone;

    @ApiModelProperty(value = "仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "角色信息")
    @TableField(exist = false)
    private List<SysRole> roles;

    @ApiModelProperty(value = "菜单信息")
    @TableField(exist = false)
    private List<SysMenu> menus;

    @ApiModelProperty(value = "按钮信息")
    @TableField(exist = false)
    private List<String> buttons;
}
