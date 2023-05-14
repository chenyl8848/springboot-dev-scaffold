package com.codechen.scaffold.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codechen.scaffold.entity.UserEntity;

import java.util.List;

/**
 * @author cyl
 * @date 2023-05-09 10:27
 * @description 用户管理 service
 */
public interface UserService extends IService<UserEntity> {

    public List<UserEntity> getUserList();
}
