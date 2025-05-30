package com.codechen.scaffold.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codechen.scaffold.framework.domain.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:33
 * @description 系统菜单
 */
@Data
@ApiModel(description = "系统菜单")
@TableName("sys_menu")
public class SysMenu extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "父级菜单")
	@TableField("pid")
	private Long pid;

	@ApiModelProperty(value = "菜单名称")
	@TableField("menu_name")
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;

	@ApiModelProperty(value = "菜单编码")
	@TableField("menu_code")
	@NotBlank(message = "菜单编码不能为空")
	private String menuCode;

	@ApiModelProperty(value = "菜单图标")
	@TableField("menu_icon")
	private String menuIcon;

	@ApiModelProperty(value = "组件路径")
	@TableField("component")
	private String component;

	@ApiModelProperty(value = "跳转页面路径")
	@TableField("path")
	private String path;

	@ApiModelProperty(value = "菜单类型 1:菜单 2:按钮")
	@TableField("type")
	private Integer type;

	@ApiModelProperty(value = "菜单状态 0-正常 1-禁用")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "是否隐藏 0-显示 1-隐藏")
	@TableField("is_hidden")
	private Integer isHidden;

	@ApiModelProperty(value = "菜单顺序")
	@TableField("sort")
	private Integer sort = 0;

	@ApiModelProperty(value = "是否外链 0-否 1-是")
	@TableField("is_external")
	private Integer isExternal;

	@ApiModelProperty(value = "层级")
	private Integer level;

	@ApiModelProperty(value = "下级")
	@TableField(exist = false)
	private List<SysMenu> children;

	@ApiModelProperty(value = "是否选中")
	@TableField(exist = false)
	private boolean isSelect;

}

