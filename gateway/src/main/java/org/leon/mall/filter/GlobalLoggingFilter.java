package org.leon.mall.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    Logger logger = LoggerFactory.getLogger(GlobalLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //ServerHttpResponse response = exchange.getResponse();
        var startTime = System.currentTimeMillis();
        logger.info("Incoming request: {} {}", request.getMethod(), request.getURI());

        /* 直接返回
        String data = "Request be denied!";
        DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
        return response.writeWith(Mono.just(wrap));
        */
        Mono<Void> result = chain.filter(exchange);
        var endTime = System.currentTimeMillis();
        logger.info("Incoming request took {} ms", endTime - startTime);
        //String targetUri = Objects.requireNonNull(exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR)).toString();
        String targetUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        logger.info("Target service URI: {}", targetUri);
        return result;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
