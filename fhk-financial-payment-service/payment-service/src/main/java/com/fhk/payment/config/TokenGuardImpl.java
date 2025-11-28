package com.fhk.payment.config;

import com.fhk.security.core.interfaces.TokenGuard;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.Date;

@Component
@AllArgsConstructor
public class TokenGuardImpl implements TokenGuard {

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

    public void checkRegistered(long uid){

        String redisKey = "fhk:financial:account:" + uid + ":status";
        String redisValue = redisTemplate.opsForValue().get(redisKey);

        if (redisValue == null || !redisValue.equals("active")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
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

        checkRegistered(uid);
    }

    @Override
    public void verifyRefresh(Claims claims) {

    }

}