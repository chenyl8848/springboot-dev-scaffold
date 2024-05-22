package com.codechen.scaffold.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codechen.scaffold.core.entity.Result;
import com.codechen.scaffold.domain.entity.SysMenu;
import com.codechen.scaffold.domain.entity.SysRole;
import com.codechen.scaffold.service.ISysRoleService;
import com.codechen.scaffold.domain.vo.SysRoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:27
 * @description 角色管理
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/sysrole")
public class SysRoleController {

    @Resource
    private ISysRoleService sysRoleService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/add")
    public Result addRole(@Validated @RequestBody SysRole sysRole) {
        sysRoleService.addRole(sysRole);

        return Result.success(null);
    }

    @ApiOperation(value = "修改角色")
    @PostMapping("/update")
    public Result updateRole(@Validated @RequestBody SysRole sysRole) {
        if (Objects.isNull(sysRole.getId())){
            return Result.fail("角色id不能为空");
        }

        sysRoleService.updateRole(sysRole);

        return Result.success(null);
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/delete/{id}")
    public Result deleteRole(@PathVariable(value = "id") Long id) {
        sysRoleService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation(value = "角色分页列表")
    @PostMapping("pageList/{pageNo}/{pageSize}")
    public Result pageList(@PathVariable(value = "pageNo") Long pageNo,
                       @PathVariable(value = "pageSize") Long pageSize,
                       @RequestBody SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> sysRolePage = new Page<>(pageNo, pageSize);

        IPage<SysRole> sysRoleIPage = sysRoleService.queryList(sysRolePage, sysRoleQueryVo);
        return Result.success(sysRoleIPage);
    }

    @ApiOperation(value = "角色列表")
    @PostMapping("list")
    public Result list(@RequestBody SysRoleQueryVo sysRoleQueryVo) {
        List<SysRole> sysRoleList = sysRoleService.list(new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(sysRoleQueryVo.getRoleName()), SysRole::getRoleName, sysRoleQueryVo.getRoleName())
                .like(StringUtils.isNotBlank(sysRoleQueryVo.getRoleCode()), SysRole::getRoleCode, sysRoleQueryVo.getRoleCode())
                .orderByDesc(SysRole::getCreateTime));
        return Result.success(sysRoleList);
    }

    @ApiOperation(value = "根据id获取")
    @PostMapping("/{id}")
    public Result getById(@PathVariable(value = "id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return Result.success(sysRole);
    }

    @ApiOperation(value = "分配角色菜单")
    @PostMapping("/assignRoleMenu/{id}")
    public Result assignRoleMenu(@PathVariable("id") Long id,
                                 @RequestBody Long[] menuIds) {

        sysRoleService.assignRoleMenu(id, menuIds);
        return Result.success(null);
    }

    @ApiOperation(value = "获取已分配菜单")
    @PostMapping("/getAssignedMenu/{id}")
    public Result getAssignedMenu(@PathVariable("id") Long id) {

        List<SysMenu> sysMenuList = sysRoleService.getAssignedMenu(id);
        return Result.success(sysMenuList);
    }
}
