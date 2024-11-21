package com.codechen.scaffold.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codechen.scaffold.admin.domain.entity.SysUser;
import com.codechen.scaffold.admin.domain.request.LoginUserRequest;
import com.codechen.scaffold.admin.service.ILoginService;
import com.codechen.scaffold.admin.service.ISysUserService;
import com.codechen.scaffold.common.enums.ResultCodeEnum;
import com.codechen.scaffold.common.exception.BusinessException;
import com.codechen.scaffold.common.util.JWTUtil;
import com.codechen.scaffold.framework.config.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author：Java陈序员
 * @date：2024/5/24 17:18
 * @description：登录接口实现类
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private AuthConfig authConfig;

    @Override
    public String login(LoginUserRequest loginUserRequest) {
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, loginUserRequest.getUsername())
                .eq(SysUser::getPassword, loginUserRequest.getPassword()));
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_USERNAME_NOT_EXISTS_OR_PASSWORD_ERROR);
        }

        String token = JWTUtil.generateToken(sysUser.getUsername(),
                sysUser,
                authConfig.getTokenSecret(),
                authConfig.getTokenExpiredTime());

        return token;
    }
}
