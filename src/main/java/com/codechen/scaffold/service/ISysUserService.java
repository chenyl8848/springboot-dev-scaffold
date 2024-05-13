package com.codechen.scaffold.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codechen.scaffold.entity.SysMenu;
import com.codechen.scaffold.entity.SysRole;
import com.codechen.scaffold.entity.SysUser;
import com.codechen.scaffold.vo.LoginUserVo;
import com.codechen.scaffold.vo.SysUserQueryVo;

import java.util.List;

/**
 * @author cyl
 * @date 2023-06-15 15:36
 * @description 系统用户接口类
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 添加用户
     *
     * @param sysUser
     */
    public void addUser(SysUser sysUser);

    /**
     * 修改用户
     *
     * @param sysUser
     */
    public void updateUser(SysUser sysUser);

    /**
     * 根究用户名查询
     *
     * @param username
     * @return
     */
    public SysUser getUserByUsername(String username);

    /**
     * 根究手机号查询
     *
     * @param phone
     * @return
     */
    public SysUser getUserByPhone(String phone);

    /**
     * 列表查询
     *
     * @param sysUserPage    分页
     * @param sysUserQueryVo 查询条件
     * @return
     */
    IPage<SysUser> queryList(Page<SysUser> sysUserPage, SysUserQueryVo sysUserQueryVo);

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
    List<SysRole> getAssignedUserRole(Long userId);

    /**
     * 根据用户id获取已分配的菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> getAssignedMenu(Long userId);

    /**
     * 用户登录
     *
     * @param loginUserVo
     * @return
     */
    public String login(LoginUserVo loginUserVo);

    /**
     * 获取用户信息
     *
     * @return
     */
    public SysUser getUserInfo();

}
