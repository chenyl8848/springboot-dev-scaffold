package com.codechen.scaffold.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codechen.scaffold.domain.entity.SysMenu;
import com.codechen.scaffold.domain.request.SysMenuRequest;
import com.codechen.scaffold.domain.vo.SysMenuVo;

import java.util.List;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:36
 * @description 系统菜单接口类
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 添加菜单
     *
     * @param sysMenuRequest
     */
    public void addMenu(SysMenuRequest sysMenuRequest);

    /**
     * 修改菜单
     *
     * @param id
     * @param sysMenuRequest
     */
    public void updateMenu(Long id, SysMenuRequest sysMenuRequest);

    public SysMenu getMenuByMenuCode(String menuCode);

    /**
     * 菜单树
     *
     * @return
     */
    public List<SysMenuVo> menuTree();

    /**
     * 获取有权限的菜单信息
     * @param menuList
     * @return
     */
    public List<SysMenu> getPermissionMenus(List<SysMenu> menuList);

    /**
     * 获取有权限的按钮信息
     * @param menuList
     * @return
     */
    public List<String> getPermissionButtons(List<SysMenu> menuList);

}
