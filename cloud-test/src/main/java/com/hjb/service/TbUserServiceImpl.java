package com.hjb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjb.domain.TbUser;
import com.hjb.mapper.UserMapper;
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
public class TbUserServiceImpl extends ServiceImpl<UserMapper, TbUser> implements TbUserService {


    @Override
    public TbUser getByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<TbUser>().eq(TbUser::getUsername,username));
    }
}
