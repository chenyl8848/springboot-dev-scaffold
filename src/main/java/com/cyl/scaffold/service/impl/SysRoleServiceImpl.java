package com.cyl.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyl.scaffold.core.constant.ResultCodeEnum;
import com.cyl.scaffold.core.exception.BusinessException;
import com.cyl.scaffold.entity.SysMenu;
import com.cyl.scaffold.entity.SysRole;
import com.cyl.scaffold.entity.SysRoleMenu;
import com.cyl.scaffold.mapper.SysRoleMapper;
import com.cyl.scaffold.service.ISysMenuService;
import com.cyl.scaffold.service.ISysRoleMenuService;
import com.cyl.scaffold.service.ISysRoleService;
import com.cyl.scaffold.vo.SysRoleQueryVo;
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
 * @date 2023-06-15 18:19
 * @description 系统角色接口实现类
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Override
    public void addRole(SysRole sysRole) {
        // 校验角色编码是否已经存在
        checkUniqueRoleCode(sysRole.getRoleCode());

        save(sysRole);

    }

    @Override
    public void updateRole(SysRole sysRole) {
        SysRole role = getById(sysRole.getId());
        if (Objects.isNull(role)) {
            throw new BusinessException(ResultCodeEnum.SYS_ROLE_NOT_EXISTS);
        }

        if (StringUtils.isNotBlank(sysRole.getRoleCode()) && !role.getRoleCode().equals(sysRole.getRoleCode())) {
            checkUniqueRoleCode(sysRole.getRoleCode());
        }

        updateById(sysRole);
    }

    private void checkUniqueRoleCode(String roleCode) {
        SysRole roleByRoleCode = getRoleByRoleCode(roleCode);
        if (Objects.nonNull(roleByRoleCode)) {
            throw new BusinessException(ResultCodeEnum.SYS_ROLE_ROLE_CODE_EXISTS);
        }
    }

    @Override
    public SysRole getRoleByRoleCode(String roleCode) {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysRole::getRoleCode, roleCode);

        return getOne(queryWrapper);
    }

    @Override
    public IPage<SysRole> queryList(Page<SysRole> sysRolePage, SysRoleQueryVo sysRoleQueryVo) {

        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(sysRoleQueryVo.getRoleCode()), SysRole::getRoleCode, sysRoleQueryVo.getRoleCode());
        queryWrapper.like(StringUtils.isNotBlank(sysRoleQueryVo.getRoleName()), SysRole::getRoleName, sysRoleQueryVo.getRoleName());

        Page<SysRole> page = page(sysRolePage, queryWrapper);

        return page;
    }

    @Override
    public void assignRoleMenu(Long roleId, Long[] menuIds) {
        SysRole sysRole = getById(roleId);
        if (Objects.isNull(sysRole)) {
            throw new BusinessException(ResultCodeEnum.SYS_ROLE_NOT_EXISTS);
        }

        sysRoleMenuService.removeByRoleId(roleId);

        List<SysRoleMenu> sysRoleMenuList = Arrays.asList(menuIds).stream().map(item -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(item);
            return sysRoleMenu;
        }).collect(Collectors.toList());

        sysRoleMenuService.saveBatch(sysRoleMenuList);

    }

    @Override
    public List<SysMenu> getAssignedMenu(Long id) {
        SysRole sysRole = getById(id);
        if (Objects.isNull(sysRole)) {
            throw new BusinessException(ResultCodeEnum.SYS_ROLE_NOT_EXISTS);
        }

        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));

        List<Long> menuIds = sysRoleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        if (menuIds != null && menuIds.size() > 0) {
            return sysMenuService.list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId, menuIds));
        } else {
            return new ArrayList<SysMenu>();
        }

    }

    @Override
    public List<SysMenu> getAssignedMenu(List<Long> ids) {
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, ids));

        List<Long> menuIds = sysRoleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        if (menuIds != null && menuIds.size() > 0) {
            return sysMenuService.list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId, menuIds).orderByAsc(SysMenu::getSort));
        } else {
            return new ArrayList<SysMenu>();
        }
    }
}
