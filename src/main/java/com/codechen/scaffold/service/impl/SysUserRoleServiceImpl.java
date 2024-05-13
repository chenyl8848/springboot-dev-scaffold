package com.codechen.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.codechen.scaffold.entity.SysUserRole;
import com.codechen.scaffold.mapper.SysUserRoleMapper;
import com.codechen.scaffold.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author cyl
 * @date 2023-06-15 15:38
 * @description 用户角色接口实现类
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public void removeByUserId(Long userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userId);

        remove(queryWrapper);
    }
}
