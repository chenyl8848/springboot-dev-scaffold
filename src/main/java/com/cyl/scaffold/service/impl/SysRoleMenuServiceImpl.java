package com.cyl.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cyl.scaffold.entity.SysRoleMenu;
import com.cyl.scaffold.mapper.SysRoleMenuMapper;
import com.cyl.scaffold.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @author cyl
 * @date 2023-06-15 18:19
 * @description 角色菜单接口实现类
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);

        remove(queryWrapper);
    }
}
