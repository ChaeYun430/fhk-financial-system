package com.fhk.client.config;

import com.fhk.customer.constant.CustomerStatus;
import com.fhk.security.core.record.FhkUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
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
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {

        if (shouldSkip(request)) {
            return true;
        }

        return validateFinancialUser(response);
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

        if (!CustomerStatus.ACTIVE.toString().equals(value)) {
            response.sendError(404, "MEMBER_NOT_FOUND");
            return false;
        }

        return true;
    }
}
