package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-11-30
 */
public interface UserService extends IService<User> {

    boolean addUserScore(Long socre);

    boolean reduceUserScore(Long socre);
}
