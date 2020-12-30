package com.hjb.controller;

import com.hjb.domain.po.TbUser;
import com.hjb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "login")
public class LoginController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object user(){
        return "User!!!!!!";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Object hello(){
        return "Hello!!!!!";
    }

    @GetMapping(value = "/test")
    public Object test(){


        return null;
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("current")
    public Object info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userDetailsService.loadUserByUsername(username);
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
