package com.codechen.scaffold.service;

import com.codechen.scaffold.domain.request.LoginUserRequest;

/**
 * @author：Java陈序员
 * @date：2024/5/24 17:17
 * @description：登录接口
 */
public interface ILoginService {

    /**
     * 用户名/密码登录
     * @param loginUserRequest
     * @return token
     */
    String login(LoginUserRequest loginUserRequest);
}
