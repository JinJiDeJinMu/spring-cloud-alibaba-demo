package com.hjb.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class GlobalRequestFilter implements GlobalFilter, Ordered {

    @Value("${ignore.url}")
    private String ignoreUrl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("service-gateway filter ,exchange = {}" ,exchange);
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();

        if (checkUrl(path)) {
            //放行
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isEmpty(token)) {
            //请求头中没有token
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //添加头信息 传递给 各个微服务()
        request.mutate().header("Authorization", "Bearer " + token);

        return chain.filter(exchange);
    }

    private boolean checkUrl(String path) {
        if (StringUtils.isEmpty(ignoreUrl)) {
            return false;
        }
        String[] urls = ignoreUrl.split(",");
        for (String url : urls) {
            if (path.startsWith(url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
