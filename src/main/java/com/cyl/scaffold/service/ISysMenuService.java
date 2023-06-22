package com.cyl.scaffold.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyl.scaffold.entity.SysMenu;

import java.util.List;

/**
 * @author cyl
 * @date 2023-06-15 15:36
 * @description 系统菜单接口类
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 添加菜单
     *
     * @param sysMenu
     */
    public void addMenu(SysMenu sysMenu);

    /**
     * 修改菜单
     *
     * @param sysMenu
     */
    public void updateMenu(SysMenu sysMenu);

    public SysMenu getMenuByMenuCode(String menuCode);

    /**
     * 菜单树
     *
     * @return
     */
    public List<SysMenu> menuTree();

}
