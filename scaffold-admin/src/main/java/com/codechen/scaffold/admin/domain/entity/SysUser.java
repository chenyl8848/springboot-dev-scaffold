package com.codechen.scaffold.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codechen.scaffold.common.domain.UserInfo;
import com.codechen.scaffold.framework.domain.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:33
 * @description 系统用户
 */
@Data
@TableName("sys_user")
public class SysUser extends AbstractEntity implements UserInfo {
    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("nick_name")
    private String nickName;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @ApiModelProperty(value = "角色信息")
    @TableField(exist = false)
    private List<SysRole> roles;

    @ApiModelProperty(value = "菜单信息")
    @TableField(exist = false)
    private List<SysMenu> menus;

    @ApiModelProperty(value = "按钮信息")
    @TableField(exist = false)
    private List<String> buttons;

    @Override
    public Long getUserId() {
        return this.getId();
    }

    @Override
    public String getUserNickName() {
        return getNickName();
    }
}
