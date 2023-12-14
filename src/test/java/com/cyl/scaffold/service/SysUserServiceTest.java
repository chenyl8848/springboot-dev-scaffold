package com.cyl.scaffold.service;

import com.cyl.scaffold.entity.SysUser;
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
        SysUser sysUser = new SysUser();

        sysUser.setUsername("Test");
        sysUser.setName("Test");

        sysUserService.addUser(sysUser);
    }
}
