package com.hjb.service;

import com.hjb.domain.po.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

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
