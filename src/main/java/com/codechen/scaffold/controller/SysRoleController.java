package com.codechen.scaffold.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codechen.scaffold.core.entity.Result;
import com.codechen.scaffold.domain.entity.SysRole;
import com.codechen.scaffold.domain.request.SysRoleQueryRequest;
import com.codechen.scaffold.domain.request.SysRoleRequest;
import com.codechen.scaffold.domain.vo.SysMenuVo;
import com.codechen.scaffold.domain.vo.SysRoleVo;
import com.codechen.scaffold.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PostMapping("/")
    public Result addRole(@Validated @RequestBody SysRoleRequest sysRoleRequest) {
        sysRoleService.addRole(sysRoleRequest);

        return Result.success(null);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/{id}")
    public Result updateRole(@PathVariable("id") Long id,
                             @Validated @RequestBody SysRoleRequest sysRoleRequest) {
        if (Objects.isNull(id)) {
            return Result.fail("角色id不能为空");
        }

        sysRoleService.updateRole(id, sysRoleRequest);

        return Result.success(null);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public Result deleteRole(@PathVariable(value = "id") Long id) {
        sysRoleService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation(value = "角色分页列表")
    @PostMapping("pageList/{pageNo}/{pageSize}")
    public Result pageList(@PathVariable(value = "pageNo") Long pageNo,
                           @PathVariable(value = "pageSize") Long pageSize,
                           @RequestBody SysRoleQueryRequest sysRoleQueryRequest) {
        Page<SysRole> sysRolePage = new Page<>(pageNo, pageSize);

        IPage<SysRoleVo> sysRoleIPage = sysRoleService.queryList(sysRolePage, sysRoleQueryRequest);
        return Result.success(sysRoleIPage);
    }

    @ApiOperation(value = "角色列表")
    @PostMapping("list")
    public Result<List<SysRoleVo>> list(@RequestBody SysRoleQueryRequest sysRoleQueryRequest) {
        List<SysRole> sysRoleList = sysRoleService.list(new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(sysRoleQueryRequest.getRoleName()), SysRole::getRoleName, sysRoleQueryRequest.getRoleName())
                .like(StringUtils.isNotBlank(sysRoleQueryRequest.getRoleCode()), SysRole::getRoleCode, sysRoleQueryRequest.getRoleCode())
                .orderByDesc(SysRole::getCreateTime));

        List<SysRoleVo> sysRoleVoList = BeanUtil.copyToList(sysRoleList, SysRoleVo.class);
        return Result.success(sysRoleVoList);
    }

    @ApiOperation(value = "根据id获取")
    @GetMapping("/{id}")
    public Result getById(@PathVariable(value = "id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        SysRoleVo sysRoleVo = new SysRoleVo();
        BeanUtil.copyProperties(sysRole, sysRoleVo);
        return Result.success(sysRoleVo);
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

        List<SysMenuVo> sysMenuVoList = sysRoleService.getAssignedMenu(id);
        return Result.success(sysMenuVoList);
    }
}
