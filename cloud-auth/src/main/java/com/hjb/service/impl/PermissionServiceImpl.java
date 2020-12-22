package com.hjb.service.impl;

import com.hjb.domain.po.Permission;
import com.hjb.mapper.PermissionMapper;
import com.hjb.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2020-12-11
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getUserPermission(Long userId) {
        return permissionMapper.getUserPermission(userId);
    }
}
