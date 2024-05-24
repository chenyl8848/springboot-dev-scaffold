package com.codechen.scaffold.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codechen.scaffold.core.entity.AbstractEntity;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date 2023-06-15 18:37
 * @description 用户角色
 */
@Data
@TableName("sys_user_role")
public class SysUserRole extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @TableField("role_id")
    private Long roleId;

    @TableField("user_id")
    private Long userId;

}
