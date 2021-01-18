package com.hjb.service.impl;

import com.google.common.collect.Lists;
import com.hjb.domain.Permission;
import com.hjb.domain.SecurityUser;
import com.hjb.service.PermissionService;
import com.hjb.service.UserService;
import com.hjb.task.TaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    private TaskHandler taskHandler;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //秘钥
                String clientSecret = clientDetails.getClientSecret();
                //数据库查找方式
                return new User(username,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        com.hjb.domain.User user = userService.getByUsername(username);

        if(user != null){
            List<Permission> userPermission = permissionService.getUserPermission(user.getId());

            userPermission.forEach(e->{
                if (e != null && e.getEnname() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(e.getEnname());
                    grantedAuthorities.add(grantedAuthority);
                }
            });
            //登录成功，异步更新记录
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            taskHandler.updateUser(user,request.getRemoteAddr());
        }

        return new SecurityUser(user,grantedAuthorities);
    }


}
