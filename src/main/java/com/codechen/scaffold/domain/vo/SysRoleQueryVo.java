package com.codechen.scaffold.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author：Java陈序员
 * @date 2023-06-15 17:12
 * @description 系统角色查询 vo
 */
@Data
@ApiModel(description = "角色查询实体")
public class SysRoleQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
