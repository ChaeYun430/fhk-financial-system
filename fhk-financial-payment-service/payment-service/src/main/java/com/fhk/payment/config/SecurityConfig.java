package com.fhk.payment.config;

import com.fhk.security.core.interfaces.TokenGuard;
import com.fhk.security.core.jwt.JwtVerifier;
import com.fhk.security.core.jwt.config.DefaultWhiteList;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	// POLICY : api-gateway에서 jwt 검증 완료 (access 토큰을 전달함)
	//			인증 : 결제시스템 서비스에 등록된 사용자인지 판별 후 회원정보 입력하도록 유도
	// TODO :  인가 로직 구현 필요
	//		인가 : 인터셉터로 서비스에서 권한 검증

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(sm -> {
					sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.authorizeHttpRequests(auth -> {

							// 화이트 리스트 = 인증에서 제외
							DefaultWhiteList.URIS.forEach(white -> {
								if (white.contains(":")) {
									String whiteUri = white.split(":")[0];
									String whiteMethod = white.split(":")[1];

									switch (whiteMethod) {
										case "OPTIONS" -> auth.requestMatchers(HttpMethod.OPTIONS, whiteUri).permitAll();
										case "POST" -> auth.requestMatchers(HttpMethod.POST, whiteUri).permitAll();
										case "GET" -> auth.requestMatchers(HttpMethod.GET, whiteUri).permitAll();
										case "PUT" -> auth.requestMatchers(HttpMethod.PUT, whiteUri).permitAll();
										case "PATCH" -> auth.requestMatchers(HttpMethod.PATCH, whiteUri).permitAll();
										case "DELETE" -> auth.requestMatchers(HttpMethod.DELETE, whiteUri).permitAll();
									}

								} else {
									auth.requestMatchers(white).permitAll();
								}
							});

							// 이 외 모든 endpoint 에 인증 수행
							auth
									// TODO : confirm은 csrf허용으로 진입, 요청헤더의 사용자 정보가 유지되도록
									.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
									.requestMatchers("/api/auth/v1/**").permitAll()
									.requestMatchers(HttpMethod.POST, "/api/accounts").permitAll()
									.anyRequest().authenticated();
						}
				)
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.exceptionHandling(e -> {

					// AuthenticationException만 401로
					e.authenticationEntryPoint((req, res, ex) -> {
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
					});

					// AccessDeniedException만 403으로
					e.accessDeniedHandler((req, res, ex) -> {
						res.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
					});
				});

		return http.build();
	}

}
