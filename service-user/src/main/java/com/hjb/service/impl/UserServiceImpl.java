package com.hjb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hjb.domain.User;
import com.hjb.mapper.UserMapper;
import com.hjb.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getLongin() {

        return null;
    }
}
