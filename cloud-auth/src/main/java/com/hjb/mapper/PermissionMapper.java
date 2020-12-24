package com.hjb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjb.domain.Permission;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author jinmu
 * @since 2020-12-24
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getUserPermission(Long userId);
}
