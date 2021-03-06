package com.hjb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjb.domain.TbUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jinmu
 * @since 2020-12-11
 */
public interface UserMapper extends BaseMapper<TbUser> {

    TbUser getUser(@Param("username") String username);
}
