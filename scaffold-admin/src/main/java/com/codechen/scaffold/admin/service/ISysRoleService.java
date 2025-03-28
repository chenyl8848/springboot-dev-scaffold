package com.codechen.scaffold.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codechen.scaffold.admin.domain.entity.SysRole;
import com.codechen.scaffold.admin.domain.request.SysRoleQueryRequest;
import com.codechen.scaffold.admin.domain.request.SysRoleRequest;
import com.codechen.scaffold.admin.domain.vo.SysMenuVo;
import com.codechen.scaffold.admin.domain.vo.SysRoleVo;

import java.util.List;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:36
 * @description 系统角色接口类
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 添加角色
     *
     * @param sysRoleRequest
     */
    public void addRole(SysRoleRequest sysRoleRequest);

    /**
     * 更新角色
     *
     * @param id
     * @param sysRoleRequest
     */
    public void updateRole(Long id, SysRoleRequest sysRoleRequest);

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
     * @param sysRoleQueryRequest 查询条件
     * @return
     */
    IPage<SysRoleVo> queryList(Page<SysRole> sysRolePage, SysRoleQueryRequest sysRoleQueryRequest);

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
    List<SysMenuVo> getAssignedMenu(Long id);

    /**
     * 获取已分配菜单
     * @param ids 角色id集合
     * @return
     */
    List<SysMenuVo> getAssignedMenu(List<Long> ids);
}
