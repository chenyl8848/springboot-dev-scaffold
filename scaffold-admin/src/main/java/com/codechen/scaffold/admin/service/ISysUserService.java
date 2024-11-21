package com.codechen.scaffold.admin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codechen.scaffold.admin.domain.entity.SysUser;
import com.codechen.scaffold.admin.domain.request.SysUserQueryRequest;
import com.codechen.scaffold.admin.domain.request.SysUserRequest;
import com.codechen.scaffold.admin.domain.vo.SysMenuVo;
import com.codechen.scaffold.admin.domain.vo.SysRoleVo;
import com.codechen.scaffold.admin.domain.vo.SysUserVo;

import java.util.List;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:36
 * @description 系统用户接口类
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 添加用户
     *
     * @param sysUserRequest
     */
    public void addUser(SysUserRequest sysUserRequest);

    /**
     * 修改用户
     *
     * @param id
     * @param sysUser
     */
    public void updateUser(Long id, SysUserRequest sysUserRequest);

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    public SysUser getUserByUsername(String username);

    /**
     * 根据手机号查询
     *
     * @param phone
     * @return
     */
    public SysUser getUserByPhone(String phone);

    /**
     * 根据邮箱查询
     *
     * @param email
     * @return
     */
    public SysUser getUserByEmail(String email);

    /**
     * 列表查询
     *
     * @param sysUserPage         分页
     * @param sysUserQueryRequest 查询条件
     * @return
     */
    IPage<SysUserVo> queryList(Page<SysUser> sysUserPage, SysUserQueryRequest sysUserQueryRequest);

    /**
     * 分配用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id
     */
    public void assignUserRole(Long userId, Long[] roleIds);

    /**
     * 根据用户id获取已分配的角色
     *
     * @param userId
     * @return
     */
    List<SysRoleVo> getAssignedUserRole(Long userId);

    /**
     * 根据用户id获取已分配的菜单
     *
     * @param userId
     * @return
     */
    List<SysMenuVo> getAssignedMenu(Long userId);

    /**
     * 获取用户信息
     *
     * @return
     */
    public SysUserVo getUserInfo();

}
