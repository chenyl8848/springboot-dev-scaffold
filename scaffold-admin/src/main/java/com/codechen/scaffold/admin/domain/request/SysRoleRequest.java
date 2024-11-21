package com.codechen.scaffold.admin.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:33
 * @description 系统角色
 */
@Data
@ApiModel(description = "系统角色")
public class SysRoleRequest {

    @ApiModelProperty(value = "角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String remark;

}

