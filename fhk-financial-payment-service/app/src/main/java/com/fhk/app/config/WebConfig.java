package com.fhk.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final FinancialInterceptor financialInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342", "http://192.168.0.154:63342")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(financialInterceptor)
                .addPathPatterns("/**")        // 적용 대상 URL
                .excludePathPatterns("/error", "/api/identification/**", "/swagger-ui/index.html", "/v3/api-docs"); // 예외 URL
    }
    // POLICY : 클라이언트 요청 수신
    //          DispatcherServlet이 어떤 컨트롤러가 처리할지 HandlerMapping에 질의
    //          매핑된 핸들러(컨트롤러) + 해당 핸들러에 적용된 인터셉터 목록을 가져옴
    //          그리고 preHandle() 자동 호출
    //          preHandle()이 true면 → 컨트롤러 실행
    //          컨트롤러 실행 후 postHandle()
    //          응답 직전 afterCompletion()

}

