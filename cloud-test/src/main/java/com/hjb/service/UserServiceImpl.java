package com.hjb.service;

import com.google.common.collect.Lists;
import com.hjb.domain.Permission;
import com.hjb.domain.SecurityUser;
import com.hjb.domain.TbUser;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理业务类
 * Created by macro on 2020/6/19.
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    private TbUserService tbUserService;

    @Resource
    private PermissionService permissionService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = new SecurityUser();
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        TbUser user = tbUserService.getByUsername(username);

        if(user != null){
            List<Permission> userPermission = permissionService.getUserPermission(user.getId());

            userPermission.forEach(e->{
                if (e != null && e.getEnname() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(e.getEnname());
                    grantedAuthorities.add(grantedAuthority);
                }
            });
        }

        securityUser.setUsername(user.getUsername());
        securityUser.setPassword(user.getPassword());
        securityUser.setId(user.getId());
        securityUser.setAuthorities(grantedAuthorities);
        securityUser.setEnabled(true);
        return securityUser;
    }
}
