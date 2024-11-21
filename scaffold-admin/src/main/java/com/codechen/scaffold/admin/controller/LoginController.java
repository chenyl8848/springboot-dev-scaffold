package com.codechen.scaffold.admin.controller;

import com.codechen.scaffold.admin.domain.request.LoginUserRequest;
import com.codechen.scaffold.admin.service.ILoginService;
import com.codechen.scaffold.common.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：Java陈序员
 * @date：2024/5/24 17:16
 * @description：登录
 */
@RestController
@RequestMapping("/login")
@Api(tags = "用户登录")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @ApiOperation(value = "账号密码登录")
    @PostMapping("/")
    public Result login(@Validated @RequestBody LoginUserRequest loginUserRequest) {
        String token = loginService.login(loginUserRequest);
        return Result.success(token);
    }
}
