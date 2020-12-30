package com.hjb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.po.TbUser;
import com.hjb.mapper.UserMapper;
import com.hjb.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, TbUser> implements UserService {


    @Override
    public TbUser getByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<TbUser>().eq(TbUser::getUsername,username));
    }

}
