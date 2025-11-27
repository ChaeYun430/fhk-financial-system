package com.fhk.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TrackingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(TrackingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 4) Correlation-ID 생성 및 헤더 추가
        String correlationId = java.util.UUID.randomUUID().toString();

        exchange = exchange.mutate()
                .request(r -> r.header("X-Correlation-ID", correlationId))
                .build();

        // .header("X-User-ID", userId))

        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 2;
    }

}
