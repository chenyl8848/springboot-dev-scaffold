package com.codechen.scaffold.admin.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.codechen.scaffold.framework.domain.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:33
 * @description 系统菜单
 */
@Data
@ApiModel(description = "系统菜单")
public class SysMenuVo extends BaseVo {

	@ApiModelProperty(value = "所属上级")
	private Long pid;

	@ApiModelProperty(value = "名称")
	private String menuName;

	@ApiModelProperty(value = "菜单编码")
	private String menuCode;

	@ApiModelProperty(value = "菜单图标")
	private String menuIcon;

	@ApiModelProperty(value = "组件路径")
	private String component;

	@ApiModelProperty(value = "跳转页面路径")
	private String path;

	@ApiModelProperty(value = "类型(1:菜单,2:按钮)")
	private Integer type;

	@ApiModelProperty(value = "状态(0:禁止,1:正常)")
	private Integer status;

	@ApiModelProperty(value = "是否隐藏(0:显示,1:隐藏)")
	private Integer isHidden;

	@ApiModelProperty(value = "菜单顺序")
	@TableField("sort")
	private Integer sort = 0;

	@ApiModelProperty(value = "是否外链(0:否,1:是)")
	private Integer isExternal;

	@ApiModelProperty(value = "层级")
	private Integer level;

	@ApiModelProperty(value = "下级")
	private List<SysMenuVo> children;

	@ApiModelProperty(value = "是否选中")
	private boolean isSelect;

}

