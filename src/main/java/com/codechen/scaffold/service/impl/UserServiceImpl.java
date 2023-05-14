package com.codechen.scaffold.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codechen.scaffold.entity.UserEntity;
import com.codechen.scaffold.mapper.UserMapper;
import com.codechen.scaffold.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cyl
 * @date 2023-05-09 10:28
 * @description 用户管理 service 实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public List<UserEntity> getUserList() {
        return list();
    }
}
