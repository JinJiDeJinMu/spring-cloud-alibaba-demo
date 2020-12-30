package com.hjb.service.impl;

import com.hjb.domain.po.Permission;
import com.hjb.domain.po.TbUser;
import com.hjb.service.PermissionService;
import com.hjb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        TbUser user = userService.getByUsername(username);

        if(user != null){
            List<Permission> userPermission = permissionService.getUserPermission(user.getId());

            userPermission.forEach(e->{
                if (e != null && e.getEnname() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(e.getEnname());
                    grantedAuthorities.add(grantedAuthority);
                }
            });
        }

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
