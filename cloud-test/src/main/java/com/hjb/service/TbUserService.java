package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.TbUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-11
 */
public interface TbUserService extends IService<TbUser> {

    TbUser getByUsername(String username);
}
