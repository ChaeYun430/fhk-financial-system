package com.fhk.app;

import com.fhk.common.exception.GlobalExceptionHandler;
import com.fhk.security.core.jwt.JwtVerifier;
import com.fhk.security.core.jwt.config.JwtIssuerProperties;
import com.fhk.security.core.jwt.config.JwtVerifierProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableConfigurationProperties({JwtIssuerProperties.class, JwtVerifierProperties.class})
@Import({GlobalExceptionHandler.class, JwtVerifier.class})
@SpringBootApplication(scanBasePackages = "com.fhk.app")

// 멀티모듈용
@EntityScan(basePackages = {"com.fhk.payment.domain"})
@EnableJpaRepositories(basePackages = {"com.fhk.payment.repository"})
@ComponentScan(basePackages = {
        "com.fhk.payment",
        "com.fhk.app",
        "com.fhk.api",
        "com.fhk.common"
})
public class AppApplication {

    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("org.springframework.web.method.ControllerAdviceBean");
            System.out.println("### DEBUG: ControllerAdviceBean Source -> " + clazz.getProtectionDomain().getCodeSource().getLocation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpringApplication.run(AppApplication.class, args);}

}
