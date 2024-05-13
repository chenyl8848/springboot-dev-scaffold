package com.codechen.scaffold.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codechen.scaffold.core.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author cyl
 * @date 2023-06-15 15:33
 * @description 系统角色
 */
@Data
@ApiModel(description = "系统角色")
@TableName("sys_role")
public class SysRole extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编码")
    @TableField("role_code")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}

