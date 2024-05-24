package com.codechen.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codechen.scaffold.core.constant.ResultCodeEnum;
import com.codechen.scaffold.core.exception.BusinessException;
import com.codechen.scaffold.core.util.JwtUtil;
import com.codechen.scaffold.domain.entity.SysUser;
import com.codechen.scaffold.domain.request.LoginUserRequest;
import com.codechen.scaffold.service.ILoginService;
import com.codechen.scaffold.service.ISysUserService;
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

    @Value("${platform.config.auth.tokenSignSecret}")
    private String tokenSignSecret;

    @Value("${platform.config.auth.tokenExpiredTime}")
    private Long tokenExpiredTime;

    @Override
    public String login(LoginUserRequest loginUserRequest) {
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, loginUserRequest.getUsername())
                .eq(SysUser::getPassword, loginUserRequest.getPassword()));
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(ResultCodeEnum.SYS_USER_USERNAME_NOT_EXISTS_OR_PASSWORD_ERROR);
        }

        Map<String, Object> info = new HashMap<>();
        info.put("username", sysUser.getUsername());
        String token = JwtUtil.generateToken(sysUser.getUsername(), info, tokenSignSecret, tokenExpiredTime);

        return token;
    }
}
