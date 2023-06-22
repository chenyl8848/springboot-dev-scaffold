package com.cyl.scaffold.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyl.scaffold.entity.SysMenu;
import com.cyl.scaffold.entity.SysRole;
import com.cyl.scaffold.vo.SysRoleQueryVo;

import java.util.List;

/**
 * @author cyl
 * @date 2023-06-15 15:36
 * @description 系统角色接口类
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 添加角色
     *
     * @param sysRole
     */
    public void addRole(SysRole sysRole);

    /**
     * 更新角色
     *
     * @param sysRole
     */
    public void updateRole(SysRole sysRole);

    /**
     * 根据据俄色色编码获取
     *
     * @param roleCode 角色编码
     * @return
     */
    public SysRole getRoleByRoleCode(String roleCode);

    /**
     * 列表查询
     *
     * @param sysRolePage    分页
     * @param sysRoleQueryVo 查询条件
     * @return
     */
    IPage<SysRole> queryList(Page<SysRole> sysRolePage, SysRoleQueryVo sysRoleQueryVo);

    /**
     * 分配角色菜单
     *
     * @param roleId  角色id
     * @param menuIds 菜单id集合
     */
    public void assignRoleMenu(Long roleId, Long[] menuIds);

    /**
     * 获取已分配菜单
     * @param id 角色id
     * @return
     */
    List<SysMenu> getAssignedMenu(Long id);
}
