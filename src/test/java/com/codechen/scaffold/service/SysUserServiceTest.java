package com.codechen.scaffold.service;

import com.codechen.scaffold.domain.entity.SysUser;
import com.codechen.scaffold.domain.request.SysUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author：Java陈序员
 * @date：2023/12/14 10:21
 * @description：
 */
@SpringBootTest
public class SysUserServiceTest {

    @Autowired
    private ISysUserService sysUserService;

    @Test
    public void testAddUser() {
        SysUserRequest sysUserRequest = new SysUserRequest();

        sysUserRequest.setUsername("admin");
        sysUserRequest.setNickName("管理员");
        sysUserRequest.setPassword("123456");

        sysUserService.addUser(sysUserRequest);
    }
}
