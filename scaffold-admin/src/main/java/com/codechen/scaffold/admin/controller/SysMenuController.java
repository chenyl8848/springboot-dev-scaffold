package com.codechen.scaffold.admin.controller;

import com.codechen.scaffold.admin.domain.request.SysMenuRequest;
import com.codechen.scaffold.admin.domain.vo.SysMenuVo;
import com.codechen.scaffold.admin.service.ISysMenuService;
import com.codechen.scaffold.common.domain.Result;
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
 * @description 菜单管理
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/sysmenu")
public class SysMenuController {

    @Resource
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "新增菜单")
    @PostMapping("/")
    public Result addMenu(@Validated @RequestBody SysMenuRequest sysMenuRequest) {
        sysMenuService.addMenu(sysMenuRequest);

        return Result.success(null);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/{id}")
    public Result updateMenu(@PathVariable("id") Long id,
                             @Validated @RequestBody SysMenuRequest sysMenuRequest) {
        if (Objects.isNull(id)) {
            return Result.fail("菜单id不能为空");
        }

        sysMenuService.updateMenu(id, sysMenuRequest);

        return Result.success(null);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{id}")
    public Result deleteMenu(@PathVariable(value = "id") Long id) {
        sysMenuService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation(value = "批量删除菜单")
    @DeleteMapping("/")
    public Result batchDeleteMenu(@RequestBody Long[] ids) {
        sysMenuService.removeBatchByIds(Arrays.asList(ids));
        return Result.success(null);
    }

    @ApiOperation(value = "菜单树列表")
    @GetMapping("/")
    public Result menuTree() {
        List<SysMenuVo> menuTree = sysMenuService.menuTree();
        return Result.success(menuTree);
    }
}
