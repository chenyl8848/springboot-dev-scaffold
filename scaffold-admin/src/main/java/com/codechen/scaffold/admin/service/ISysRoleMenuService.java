package com.codechen.scaffold.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codechen.scaffold.admin.domain.entity.SysRoleMenu;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:36
 * @description 角色菜单接口类
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据角色id删除角色菜单
     *
     * @param roleId
     */
    public void removeByRoleId(Long roleId);
}
