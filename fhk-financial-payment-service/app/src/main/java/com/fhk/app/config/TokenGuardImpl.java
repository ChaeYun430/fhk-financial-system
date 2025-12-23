package com.fhk.app.config;

import com.fhk.security.core.interfaces.TokenGuard;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log4j2
@Component
@AllArgsConstructor
public class TokenGuardImpl implements TokenGuard {

    // INTENT : JwtAuthFilter나 TokenGuard 안에서 각 Artifact 등록 여부 검증
    //          → 인증 도메인과 금융 도메인 강결합
    //          → MSA 원칙 위배

    private final StringRedisTemplate redisTemplate;

    private void checkVersion(long uid, Integer tokenVersion) {

        String redisKey = "fhk:security:account:" + uid + ":ver";
        String redisValue = redisTemplate.opsForValue().get(redisKey);
        if (redisValue == null) {
            throw new CredentialsExpiredException("no version info in cache");
        }

        int currentVersion = Integer.parseInt(redisValue);
        if (tokenVersion == null || !tokenVersion.equals(currentVersion)) {
            throw new CredentialsExpiredException("ver mismatch");
        }
    }


    @Override
    public void verifyAccess(Claims claims){

        if (!"access".equals(claims.getAudience())) {
            throw new BadCredentialsException("not access token");
        }

        Date expDate = claims.getExpiration();
        if (expDate == null) {
            throw new BadCredentialsException("exp missing");
        }

        long uid = Long.parseLong(claims.getSubject());
        Integer tokenVersion = claims.get("version", Integer.class);
        checkVersion(uid, tokenVersion);

    }


    @Override
    public void verifyRefresh(Claims claims) {

    }
}