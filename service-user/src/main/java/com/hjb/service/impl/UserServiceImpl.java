package com.hjb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hjb.domain.User;
import com.hjb.mapper.UserMapper;
import com.hjb.service.UserService;
import com.hjb.util.SecurityUserUtils;
import org.omg.CORBA.TIMEOUT;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SecurityUserUtils securityUserUtils;

    @Override
    public boolean addUserScore(Long score) {
        Long userId = Long.valueOf(securityUserUtils.getUserInfo().get("id").toString());

        RLock lock = redissonClient.getLock("user-score-" + userId);
        try {
            lock.lock();
            User user = this.getById(userId);
            user.setScoure(user.getScoure() + score);
            this.saveOrUpdate(user);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public boolean reduceUserScore(Long score) {
        Long userId = Long.valueOf(securityUserUtils.getUserInfo().get("id").toString());

        RLock lock = redissonClient.getLock("user-score-" + userId);
        try {
            lock.lock();
            User user = this.getById(userId);
            user.setScoure((user.getScoure()  - score) <0 ? 0: user.getScoure()-score);
            this.saveOrUpdate(user);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return true;
    }
}
