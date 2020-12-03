package com.hjb.filter;

import com.alibaba.fastjson.JSONObject;
import com.hjb.domain.po.User;
import com.hjb.jwt.JwtUtils;
import com.hjb.util.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class GlobalRequestFilter implements GlobalFilter, Ordered {

    @Value("${gateway.white.url}")
    private String whiteUrl;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestUri = exchange.getRequest().getPath().pathWithinApplication().value();
        log.info("requestUri="+ requestUri);
        if(isStartWith(requestUri)){//url白名单
            return chain.filter(exchange);
        }
        if(!exchange.getRequest().getHeaders().containsKey("token")){
            log.info("headers is not token");
            return authError(exchange.getResponse(),"操作认证失败");
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : whiteUrl.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 检验token是否合法以及对应用户权限
     * @param token
     * @return
     */
    private boolean checkToken(String token){
        try{
            JSONObject result = JSONObject.parseObject(JwtUtils.vaildToken(token));
            if(!result.containsKey("userId") || !result.containsKey("userName")){
               return false;
            }
            Long userId = result.getLong("userId");
            String userName = result.getString("userName");

        }catch (Exception e){
        }
        return false;
    }

    /**
     * 封装认证错误返回信息
     * @param response
     * @param msg
     * @return
     */
    private Mono<Void> authError(ServerHttpResponse response,String msg){
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-type","application/json;charset=UTF-8");

        String resultMsg = JSONObject.toJSONString( Result.FAILURE(msg,HttpStatus.UNAUTHORIZED.value()));

        DataBuffer buffer =  response.bufferFactory().wrap(resultMsg.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Flux.just(buffer));
    }
}
