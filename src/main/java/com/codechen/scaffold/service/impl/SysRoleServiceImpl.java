package com.codechen.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codechen.scaffold.core.constant.ResultCodeEnum;
import com.codechen.scaffold.core.exception.BusinessException;
import com.codechen.scaffold.core.util.BeanUtil;
import com.codechen.scaffold.domain.entity.SysMenu;
import com.codechen.scaffold.domain.entity.SysRole;
import com.codechen.scaffold.domain.entity.SysRoleMenu;
import com.codechen.scaffold.domain.request.SysRoleQueryRequest;
import com.codechen.scaffold.domain.request.SysRoleRequest;
import com.codechen.scaffold.domain.vo.SysMenuVo;
import com.codechen.scaffold.domain.vo.SysRoleVo;
import com.codechen.scaffold.mapper.SysRoleMapper;
import com.codechen.scaffold.service.ISysMenuService;
import com.codechen.scaffold.service.ISysRoleMenuService;
import com.codechen.scaffold.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
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
    @Transactional(rollbackFor = Exception.class)
    public void addRole(SysRoleRequest sysRoleRequest) {
        // 校验角色编码是否已经存在
        checkUniqueRoleCode(sysRoleRequest.getRoleCode());

        SysRole sysRole = new SysRole();
        BeanUtil.copy(sysRoleRequest, sysRole);

        save(sysRole);

    }

    @Override
    public void updateRole(Long id, SysRoleRequest sysRoleRequest) {
        SysRole sysRole = getById(id);
        if (Objects.isNull(sysRole)) {
            throw new BusinessException(ResultCodeEnum.SYS_ROLE_NOT_EXISTS);
        }

        if (StringUtils.isNotBlank(sysRoleRequest.getRoleCode()) && !sysRole.getRoleCode().equals(sysRoleRequest.getRoleCode())) {
            checkUniqueRoleCode(sysRoleRequest.getRoleCode());
        }

        BeanUtil.copy(sysRoleRequest, sysRole);

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
    public IPage<SysRoleVo> queryList(Page<SysRole> sysRolePage, SysRoleQueryRequest sysRoleQueryRequest) {

        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(sysRoleQueryRequest.getRoleCode()), SysRole::getRoleCode, sysRoleQueryRequest.getRoleCode());
        queryWrapper.like(StringUtils.isNotBlank(sysRoleQueryRequest.getRoleName()), SysRole::getRoleName, sysRoleQueryRequest.getRoleName());

        Page<SysRole> page = page(sysRolePage, queryWrapper);

        IPage<SysRoleVo> sysRoleVoPage = new Page<>();
        BeanUtil.copy(page, sysRoleVoPage);

        return sysRoleVoPage;
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
    public List<SysMenuVo> getAssignedMenu(Long id) {
        SysRole sysRole = getById(id);
        if (Objects.isNull(sysRole)) {
            throw new BusinessException(ResultCodeEnum.SYS_ROLE_NOT_EXISTS);
        }

        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));

        List<Long> menuIds = sysRoleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        if (menuIds != null && menuIds.size() > 0) {
            List<SysMenu> sysMenuList = sysMenuService.list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId, menuIds));
            List<SysMenuVo> sysMenuVoList = new ArrayList<>();

            BeanUtil.copy(sysMenuList, sysMenuVoList);
            return sysMenuVoList;
        } else {
            return new ArrayList<SysMenuVo>();
        }

    }

    @Override
    public List<SysMenuVo> getAssignedMenu(List<Long> ids) {
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, ids));

        List<Long> menuIds = sysRoleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        if (menuIds != null && menuIds.size() > 0) {
            List<SysMenu> sysMenuList = sysMenuService.list(new LambdaQueryWrapper<SysMenu>()
                    .in(SysMenu::getId, menuIds)
                    .orderByAsc(SysMenu::getSort));

            List<SysMenuVo> sysMenuVoList = new ArrayList<>();
            BeanUtil.copy(sysMenuList, sysMenuVoList);
            return sysMenuVoList;
        } else {
            return new ArrayList<SysMenuVo>();
        }
    }
}
