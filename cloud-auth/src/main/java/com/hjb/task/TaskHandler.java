package com.hjb.task;

import com.hjb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
public class TaskHandler {

    @Autowired
    private UserService userService;

    @Async(value = "service-auth-task")
    public void updateUser(com.hjb.domain.User user,String url) {
        log.info("service-auth-task is run Thread = " + Thread.currentThread().getName());
        user.setLastLoginIp(url);
        user.setLastLoginTime(LocalDateTime.now());

        userService.saveOrUpdate(user);
    }
}
