package com.fhk.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SecurityServerClient {

    private final WebClient webClient; // Spring Bean으로 등록

    @Value("${spring.cloud.gateway.server.webflux.routes[0].uri}")
    private String securityUrl;


    public SecurityServerClient() {
        this.webClient = WebClient.builder()
                .baseUrl(securityUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(h -> h.setBasicAuth(secretKey, ""))
                .build();
    }

    public Mono<Boolean> performLogin(long userId, Integer tokenVersion) {
        return webClient.get()
                .uri("/api/auth/v1/login", userId, tokenVersion)
                .retrieve()
                .bodyToMono(Boolean.class);

        //TODO : 프론트로 퉤
    }

    public Mono<Boolean> refreshToken(long userId, Integer tokenVersion) {
        return webClient.get()
                .uri("/api/auth/v1/refresh", userId, tokenVersion)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

}
