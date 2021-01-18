package com.hjb.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 给feign加上Authorization
 */

@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            HttpServletRequest request = requestAttributes.getRequest();

            Enumeration<String> headerNames = request.getHeaderNames();
            if(headerNames != null){
                while (headerNames.hasMoreElements()){
                    //取出所有的请求头
                    String name = headerNames.nextElement();
                    String value = request.getHeader(name);

                    //放到feign请求中
                    requestTemplate.header(name, value);
                }
            }

        }

    }
}
