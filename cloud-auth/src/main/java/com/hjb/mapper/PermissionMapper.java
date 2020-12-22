package com.hjb.mapper;

import com.hjb.domain.po.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author jinmu
 * @since 2020-12-11
 */
public interface PermissionMapper extends BaseMapper<Permission> {


    List<Permission> getUserPermission(@Param("userId") Long userId);
}
