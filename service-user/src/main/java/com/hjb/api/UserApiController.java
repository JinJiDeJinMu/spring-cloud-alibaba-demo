package com.hjb.api;

import com.hjb.domain.User;
import com.hjb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public User get(Long id){
        return userService.getById(id);
    }


}
