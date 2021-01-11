package com.hjb.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
//order越小越是最先执行
@Order(1)
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.hjb.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录下请求内容
        logger.info("URL ：" + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "obj", pointcut = "webLog()")
    public void doAfter(Object obj) throws Exception {
        //处理完请求，返回内容
        logger.info("RESPONSE : " + obj);
        logger.info("SPEED_TIME : " + (System.currentTimeMillis() - startTime.get()));
    }
}
