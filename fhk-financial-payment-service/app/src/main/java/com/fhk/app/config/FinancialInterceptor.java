package com.fhk.app.config;

import com.fhk.security.core.record.FhkUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class FinancialInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (developSkip(request)) {
            return true;
        }

        return validateFinancialUser(response);
    }

    private boolean developSkip(HttpServletRequest request) {
        return true;
    }
    private boolean shouldSkip(HttpServletRequest request) {

        return "POST".equals(request.getMethod())
                && request.getRequestURI().equals("/customer");
    }


    private boolean validateFinancialUser(HttpServletResponse response) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.debug("auth = {}", auth);
        log.debug("principal = {}", auth != null ? auth.getPrincipal() : null);

        if (auth == null || auth.getPrincipal() == null) {
            response.sendError(401, "UNAUTHORIZED");
            return false;
        }

        if (!(auth.getPrincipal() instanceof FhkUserPrincipal principal)) {
            response.sendError(401, "INVALID_PRINCIPAL");
            return false;
        }

        long uid = principal.id();
        String key = "fhk:financial:account:" + uid + ":status";
        String value = redisTemplate.opsForValue().get(key);

        log.info("value = {}", value);

        if (!"ACTIVE".equals(value)) {
            response.sendError(404, "MEMBER_NOT_FOUND");
            return false;
        }

        return true;
    }
}
