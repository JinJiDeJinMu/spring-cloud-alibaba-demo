package com.hjb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjb.domain.Permission;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-11
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> getUserPermission(Long userId);
}
