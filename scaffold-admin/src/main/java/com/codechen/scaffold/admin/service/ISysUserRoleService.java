package com.codechen.scaffold.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codechen.scaffold.admin.domain.entity.SysUserRole;

/**
 * @author：Java陈序员
 * @date 2023-06-15 15:36
 * @description 用户角色接口类
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户id删除用户角色
     *
     * @param userId 用户id
     */
    public void removeByUserId(Long userId);
}
