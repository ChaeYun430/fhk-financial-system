package com.fhk.api.filter;

import com.fhk.api.client.SecurityServerClient;
import com.fhk.security.core.jwt.JwtVerifier;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Import({JwtVerifier.class}) // TODO : 임시 임포트
public class TokenFilter implements GlobalFilter, Ordered {

    private final StringRedisTemplate redisTemplate;
    private final JwtVerifier jwtVerifier;
    private final SecurityServerClient securityClient;
    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }
        String token = authHeader.substring(7);
        Claims claims = jwtVerifier.getClaims(token);

        verifyAccess(claims);
        //  TODO : 블랙리스트 체크

        return chain.filter(exchange);
    }


    private void verifyAccess(Claims claims) {

        long uid = Long.parseLong(claims.getSubject());

        // aud 검증
        if (!"access".equals(claims.getAudience())) {
            //TODO : 시큐리티 서버 호출
        }

        // token 내 만료기간 없음
        Date expDate = claims.getExpiration();
        Integer tokenVersion = claims.get("version", Integer.class);
        if (expDate == null) {
            checkVersion(uid, tokenVersion);
        }
    }


    private void checkVersion(long uid, Integer tokenVersion) {

        String redisKey = "fhk:security:account:" + uid + ":ver";
        String redisValue = redisTemplate.opsForValue().get(redisKey);
        int currentVersion = (redisValue != null) ?
                Integer.parseInt(redisValue) : null; //TODO : 시큐리티 서버 호출

        if (tokenVersion == null || tokenVersion != currentVersion) {
            //TODO : 시큐리티 서버 호출;

        }
    }

    @Override
    public int getOrder() {
        return 1;
    }

}