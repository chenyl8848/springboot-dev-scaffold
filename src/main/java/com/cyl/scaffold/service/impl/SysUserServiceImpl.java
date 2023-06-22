package com.cyl.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyl.scaffold.core.constant.ResultCodeEnum;
import com.cyl.scaffold.core.exception.BusinessException;
import com.cyl.scaffold.entity.SysRole;
import com.cyl.scaffold.entity.SysUser;
import com.cyl.scaffold.entity.SysUserRole;
import com.cyl.scaffold.mapper.SysUserMapper;
import com.cyl.scaffold.service.ISysRoleService;
import com.cyl.scaffold.service.ISysUserRoleService;
import com.cyl.scaffold.service.ISysUserService;
import com.cyl.scaffold.vo.SysUserQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author cyl
 * @date 2023-06-15 15:38
 * @description 系统用户接口实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public void addUser(SysUser sysUser) {
        // 1.校验用户名
        checkUniqueUsername(sysUser.getUsername());

        // 2.校验用户手机号
        checkUniquePhone(sysUser.getPhone());

        save(sysUser);
    }

    @Override
    public void updateUser(SysUser sysUser) {
        SysUser user = getById(sysUser.getId());
        if (Objects.isNull(user)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_NOT_EXISTS);
        }

        if (StringUtils.isNotBlank(sysUser.getUsername()) && !user.getUsername().equals(sysUser.getUsername())) {
            checkUniqueUsername(sysUser.getUsername());
        }

        if (StringUtils.isNotBlank(sysUser.getPhone()) && !user.getPhone().equals(sysUser.getPhone())) {
            checkUniquePhone(sysUser.getPhone());
        }

        updateById(sysUser);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> query = Wrappers.lambdaQuery();
        query.eq(SysUser::getUsername, username);

        return getOne(query);
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        LambdaQueryWrapper<SysUser> query = Wrappers.lambdaQuery();
        query.eq(SysUser::getPhone, phone);

        return getOne(query);
    }

    @Override
    public IPage<SysUser> queryList(Page<SysUser> sysUserPage, SysUserQueryVo sysUserQueryVo) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(sysUserQueryVo.getUsername()), SysUser::getUsername, sysUserQueryVo.getUsername());
        queryWrapper.like(StringUtils.isNotBlank(sysUserQueryVo.getName()), SysUser::getName, sysUserQueryVo.getName());

        Page<SysUser> page = page(sysUserPage, queryWrapper);
        return page;
    }

    @Override
    public void assignUserRole(Long userId, Long[] roleIds) {
        SysUser sysUser = getById(userId);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_NOT_EXISTS);
        }
        sysUserRoleService.removeByUserId(userId);
        List<SysUserRole> sysUserRoleList = Arrays.asList(roleIds).stream().map(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(item);
            return sysUserRole;
        }).collect(Collectors.toList());

        sysUserRoleService.saveBatch(sysUserRoleList);

    }

    @Override
    public List<SysRole> getAssignedUserRole(Long userId) {
        SysUser sysUser = getById(userId);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_NOT_EXISTS);
        }

        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        List<Long> roleIds = sysUserRoleList.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        if (roleIds != null && roleIds.size() > 0) {

            List<SysRole> sysRoleList = sysRoleService.list(new LambdaQueryWrapper<SysRole>().in(SysRole::getId, roleIds));
            return sysRoleList;
        } else {
            return new ArrayList<SysRole>();
        }
    }

    /**
     * 校验用户名是否唯一
     *
     * @param username
     */
    private void checkUniqueUsername(String username) {
        SysUser userByUsername = getUserByUsername(username);
        if (Objects.nonNull(userByUsername)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_USERNAME_EXISTS);
        }
    }

    /**
     * 校验手机号是否唯一
     *
     * @param phone
     */
    private void checkUniquePhone(String phone) {
        SysUser userByPhone = getUserByPhone(phone);
        if (Objects.nonNull(userByPhone)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_PHONE_EXISTS);
        }
    }
}
