package com.cyl.scaffold.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cyl
 * @date 2023-06-15 17:12
 * @description 系统用户查询 vo
 */
@Data
@ApiModel(description = "用户查询实体")
public class SysUserQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String name;
}
