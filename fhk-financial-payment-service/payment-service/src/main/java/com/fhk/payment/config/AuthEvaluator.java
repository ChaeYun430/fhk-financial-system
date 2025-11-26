package com.fhk.payment.config;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component("authEvaluator")
public class AuthEvaluator {//  MethodSecurityInterceptor 호출시 SpEL용 클래스


    private final RedisTemplate<String, String> redisTemplate;

    public boolean isMerchant(String accountId) {

        String storedRole = Objects.requireNonNull(redisTemplate.opsForValue().get("account:" + accountId + ":role"));
        return storedRole.equals("merchant");
    }

    //SpEL이 실제로 쓰이는 곳:
    //@Value("#{systemProperties['user.home']}") 같은 프로퍼티 주입
    //@PreAuthorize("principal.username == #user.username") 같은 메서드 보안
    //@Cacheable(key = "#dto.id") 같은 캐시 키 생성

}