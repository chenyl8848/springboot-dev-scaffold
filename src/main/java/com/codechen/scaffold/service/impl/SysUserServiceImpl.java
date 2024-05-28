package com.codechen.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codechen.scaffold.core.constant.CommonCodeEnum;
import com.codechen.scaffold.core.constant.ResultCodeEnum;
import com.codechen.scaffold.core.exception.BusinessException;
import com.codechen.scaffold.core.util.BeanUtil;
import com.codechen.scaffold.core.util.ThreadLocalUtil;
import com.codechen.scaffold.domain.entity.SysRole;
import com.codechen.scaffold.domain.entity.SysUser;
import com.codechen.scaffold.domain.entity.SysUserRole;
import com.codechen.scaffold.domain.request.SysUserQueryRequest;
import com.codechen.scaffold.domain.request.SysUserRequest;
import com.codechen.scaffold.domain.vo.SysMenuVo;
import com.codechen.scaffold.domain.vo.SysRoleVo;
import com.codechen.scaffold.domain.vo.SysUserVo;
import com.codechen.scaffold.mapper.SysUserMapper;
import com.codechen.scaffold.service.ISysMenuService;
import com.codechen.scaffold.service.ISysRoleService;
import com.codechen.scaffold.service.ISysUserRoleService;
import com.codechen.scaffold.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:38
 * @description 系统用户接口实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(SysUserRequest sysUserRequest) {
        // 1.校验用户名
        checkUniqueUsername(sysUserRequest.getUsername());

        // 2.校验用户手机号
        checkUniquePhone(sysUserRequest.getPhone());

        SysUser sysUser = new SysUser();
        BeanUtil.copy(sysUser, sysUser);
        save(sysUser);
    }

    @Override
    public void updateUser(Long id, SysUserRequest sysUserRequest) {
        SysUser sysUser = getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_NOT_EXISTS);
        }

        if (StringUtils.isNotBlank(sysUserRequest.getUsername()) && !sysUser.getUsername().equals(sysUserRequest.getUsername())) {
            checkUniqueUsername(sysUserRequest.getUsername());
        }

        if (StringUtils.isNotBlank(sysUserRequest.getPhone()) && !sysUser.getPhone().equals(sysUserRequest.getPhone())) {
            checkUniquePhone(sysUserRequest.getPhone());
        }

        if (StringUtils.isNotBlank(sysUserRequest.getEmail()) && !sysUser.getEmail().equals(sysUserRequest.getEmail())) {
            checkUniqueEmail(sysUserRequest.getEmail());
        }
        BeanUtils.copyProperties(sysUserRequest, sysUser);

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
    public SysUser getUserByEmail(String email) {
        LambdaQueryWrapper<SysUser> query = Wrappers.lambdaQuery();
        query.eq(SysUser::getPhone, email);

        return getOne(query);
    }

    @Override
    public IPage<SysUserVo> queryList(Page<SysUser> sysUserPage, SysUserQueryRequest sysUserQueryRequest) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(sysUserQueryRequest.getUsername()), SysUser::getUsername, sysUserQueryRequest.getUsername());
        queryWrapper.like(StringUtils.isNotBlank(sysUserQueryRequest.getName()), SysUser::getNickName, sysUserQueryRequest.getName());

        Page<SysUser> page = page(sysUserPage, queryWrapper);

        Page<SysUserVo> sysUserVoPage = new Page<>();
        BeanUtils.copyProperties(page, sysUserVoPage);
        return sysUserVoPage;
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
    public List<SysRoleVo> getAssignedUserRole(Long userId) {
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
            List<SysRoleVo> sysRoleVoList = new ArrayList<>();
            BeanUtils.copyProperties(sysRoleList, sysRoleVoList);
            return sysRoleVoList;
        } else {
            return new ArrayList<SysRoleVo>();
        }
    }

    @Override
    public List<SysMenuVo> getAssignedMenu(Long userId) {
        List<Long> roleIds = getAssignedUserRole(userId).stream()
                .map(SysRoleVo::getId)
                .collect(Collectors.toList());

        List<SysMenuVo> sysMenuVoList = sysRoleService.getAssignedMenu(roleIds);

        return sysMenuVoList;
    }

    @Override
    public SysUserVo getUserInfo() {
        SysUserVo sysUserVo = new SysUserVo();

        String username = ThreadLocalUtil.get(CommonCodeEnum.THREAD_LOCAL_LOGIN_USER_KEY.getCode());
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_USERNAME_IS_NULL);
        }

        SysUser sysUser = getUserByUsername(username);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_NOT_EXISTS);
        }
        BeanUtils.copyProperties(sysUser, sysUserVo);
        // TODO: 2024/5/24  用户角色、菜单权限
//        List<SysRole> sysRoles = new ArrayList<>();
//        List<SysMenu> sysMenus = new ArrayList<>();
//
//        if (username.equals(CommonConstant.ADMIN_USER_NAME)) {
//            sysRoles = sysRoleService.list();
//            sysMenus = sysMenuService.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
//        } else {
//            sysRoles = getAssignedUserRole(sysUser.getId());
//            sysMenus = getAssignedMenu(sysUser.getId());
//        }
//
//        List<SysMenu> permissionMenus = sysMenuService.getPermissionMenus(sysMenus);
//        List<String> permissionButtons = sysMenuService.getPermissionButtons(sysMenus);
//
//        sysUser.setRoles(sysRoles);
//        sysUser.setMenus(permissionMenus);
//        sysUser.setButtons(permissionButtons);

        return sysUserVo;
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

    /**
     * 校验邮箱是否唯一
     *
     * @param email
     */
    private void checkUniqueEmail(String email) {
        SysUser userByPhone = getUserByEmail(email);
        if (Objects.nonNull(userByPhone)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_PHONE_EXISTS);
        }
    }
}
