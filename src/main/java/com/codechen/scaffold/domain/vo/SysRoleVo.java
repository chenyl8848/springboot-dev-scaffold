package com.codechen.scaffold.domain.vo;

import com.codechen.scaffold.core.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:33
 * @description 系统角色
 */
@Data
@ApiModel(description = "系统角色")
public class SysRoleVo extends AbstractEntity {

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String remark;

}

