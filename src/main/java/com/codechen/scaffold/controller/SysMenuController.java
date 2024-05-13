package com.codechen.scaffold.controller;

import com.codechen.scaffold.core.entity.Result;
import com.codechen.scaffold.entity.SysMenu;
import com.codechen.scaffold.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
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
 * @author cyl
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
    @PostMapping("/add")
    public Result addMenu(@Validated @RequestBody SysMenu sysMenu) {
        sysMenuService.addMenu(sysMenu);

        return Result.success(null);
    }

    @ApiOperation(value = "修改菜单")
    @PostMapping("/update")
    public Result updateMenu(@Validated @RequestBody SysMenu sysMenu) {
        if (Objects.isNull(sysMenu.getId())) {
            return Result.fail("菜单id不能为空");
        }

        sysMenuService.updateMenu(sysMenu);

        return Result.success(null);
    }

    @ApiOperation(value = "删除菜单")
    @PostMapping("/delete/{id}")
    public Result deleteMenu(@PathVariable(value = "id") Long id) {
        sysMenuService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation(value = "批量删除菜单")
    @PostMapping("/batchDelete")
    public Result batchDeleteMenu(@RequestBody Long[] ids) {
        sysMenuService.removeBatchByIds(Arrays.asList(ids));
        return Result.success(null);
    }

    @ApiOperation(value = "菜单树列表")
    @PostMapping("/menuTree")
    public Result menuTree() {
        List<SysMenu> menuTree = sysMenuService.menuTree();
        return Result.success(menuTree);
    }
}
