package com.codechen.scaffold.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codechen.scaffold.core.entity.Result;
import com.codechen.scaffold.core.util.BeanUtil;
import com.codechen.scaffold.domain.entity.SysRole;
import com.codechen.scaffold.domain.entity.SysUser;
import com.codechen.scaffold.domain.request.SysUserQueryRequest;
import com.codechen.scaffold.domain.request.SysUserRequest;
import com.codechen.scaffold.domain.vo.SysRoleVo;
import com.codechen.scaffold.domain.vo.SysUserVo;
import com.codechen.scaffold.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:27
 * @description 用户管理
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public Result<SysUserVo> getUserInfo() {

        SysUserVo sysUserVo = sysUserService.getUserInfo();

        return Result.success(sysUserVo);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/")
    public Result addUser(@Validated @RequestBody SysUserRequest sysUserRequest) {

        sysUserService.addUser(sysUserRequest);

        return Result.success(null);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/{id}")
    public Result updateUser(@PathVariable("id") Long id,
                             @Validated @RequestBody SysUserRequest sysUserRequest) {

        if (Objects.isNull(id)) {
            return Result.fail("用户id不能为空");
        }

        sysUserService.updateUser(id, sysUserRequest);

        return Result.success(null);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable(value = "id") Long id) {
        sysUserService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation(value = "批量删除用户")
    @PostMapping("/batchDelete")
        public Result batchDeleteUser(@RequestBody Long[] ids) {
        sysUserService.removeBatchByIds(Arrays.asList(ids));
        return Result.success(null);
    }

    @ApiOperation(value = "用户列表")
    @PostMapping("/pageList/{pageNo}/{pageSize}")
    public Result list(@PathVariable(value = "pageNo") Long pageNo,
                       @PathVariable(value = "pageSize") Long pageSize,
                       @RequestBody SysUserQueryRequest sysUserQueryRequest) {
        Page<SysUser> sysUserPage = new Page<>(pageNo, pageSize);

        IPage<SysUserVo> sysUserVoIPage = sysUserService.queryList(sysUserPage, sysUserQueryRequest);
        return Result.success(sysUserVoIPage);
    }

    @ApiOperation(value = "根据id获取")
    @GetMapping("/{id}")
    public Result getById(@PathVariable(value = "id") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtil.copy(sysUser, sysUserVo);
        return Result.success(sysUserVo);
    }


    @ApiOperation(value = "分配角色")
    @PostMapping("/assignUserRole/{id}")
    public Result assignUserRole(@PathVariable("id") Long id,
                                 @RequestBody Long[] roleIds) {
        sysUserService.assignUserRole(id, roleIds);
        return Result.success(null);
    }

    @ApiOperation(value = "获取已分配的角色")
    @PostMapping("/getAssignedUserRole/{userId}")
    public Result getAssignedUserRole(@PathVariable("userId") Long userId) {
        List<SysRoleVo> list = sysUserService.getAssignedUserRole(userId);
        return Result.success(list);
    }
}
