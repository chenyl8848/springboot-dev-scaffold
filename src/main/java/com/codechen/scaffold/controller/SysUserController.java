package com.codechen.scaffold.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codechen.scaffold.core.entity.Result;
import com.codechen.scaffold.domain.entity.SysRole;
import com.codechen.scaffold.domain.entity.SysUser;
import com.codechen.scaffold.service.ISysUserService;
import com.codechen.scaffold.domain.vo.LoginUserVo;
import com.codechen.scaffold.domain.vo.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginUserVo loginUserVo) {
        String token = sysUserService.login(loginUserVo);
        return Result.success(token);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public Result getUserInfo() {

        SysUser sysUser = sysUserService.getUserInfo();

        return Result.success(sysUser);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/add")
    public Result addUser(@Validated @RequestBody SysUser sysUser) {

        sysUserService.addUser(sysUser);

        return Result.success(null);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping("/update")
    public Result updateUser(@Validated @RequestBody SysUser sysUser) {

        if (Objects.isNull(sysUser.getId())){
            return Result.fail("用户id不能为空");
        }

        sysUserService.updateUser(sysUser);

        return Result.success(null);
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("/delete/{id}")
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
                       @RequestBody SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> sysUserPage = new Page<>(pageNo, pageSize);

        IPage<SysUser> sysUserIPage = sysUserService.queryList(sysUserPage, sysUserQueryVo);
        return Result.success(sysUserIPage);
    }

    @ApiOperation(value = "根据id获取")
    @PostMapping("/{id}")
    public Result getById(@PathVariable(value = "id") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        return Result.success(sysUser);
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
        List<SysRole> list =  sysUserService.getAssignedUserRole(userId);
        return Result.success(list);
    }
}
