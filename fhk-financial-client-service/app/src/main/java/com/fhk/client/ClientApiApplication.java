package com.fhk.client;

//import com.fhk.asset.client.asset.AssetServiceClient;
import com.fhk.common.exception.GlobalExceptionHandler;
import com.fhk.security.core.jwt.JwtVerifier;
import com.fhk.security.core.jwt.config.JwtIssuerProperties;
import com.fhk.security.core.jwt.config.JwtVerifierProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableConfigurationProperties({JwtIssuerProperties.class, JwtVerifierProperties.class})
@Import({GlobalExceptionHandler.class, JwtVerifier.class})
@SpringBootApplication(scanBasePackages = "com.fhk.client")

// 멀티모듈용
@EntityScan(basePackages = {"com.fhk.customer.domain", "com.fhk.store.domain"})
@EnableJpaRepositories(basePackages = {"com.fhk.customer.repository", "com.fhk.store.repository"})
@ComponentScan(basePackages = {
        "com.fhk.customer",
        "com.fhk.store",
        "com.fhk.client.config",
        "com.fhk.security.core",
        "com.fhk.verification",
       "com.fhk.asset.client.s3"

})
//@EnableFeignClients(basePackageClasses = AssetServiceClient.class)
public class ClientApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(ClientApiApplication.class, args);
    }

}
