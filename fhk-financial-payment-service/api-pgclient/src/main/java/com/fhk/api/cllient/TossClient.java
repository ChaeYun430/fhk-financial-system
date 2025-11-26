package com.fhk.api.cllient;

import com.fhk.api.dto.ConfirmDto;
import com.fhk.api.dto.PayDto;
import com.fhk.api.dto.SearchDto;
import com.fhk.api.dto.toss.Payment;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Log4j2
public class TossClient implements PgClient {

        private final RestTemplate restTemplate;
        private final String baseUrl = "https://api.tosspayments.com";


        public TossClient(@Value("${tosspayments.secret-key}") String secretKey){

            this.restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add((request, body, execution) -> {

                String auth = secretKey + ":";
                String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
                String authHeader = "Basic " + encodedAuth;
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                request.getHeaders().set(HttpHeaders.AUTHORIZATION, authHeader);

                return execution.execute(request, body);
            });
        }


        public Payment confirm(ConfirmDto.Req confirmReq) {

            String confirmUrl = baseUrl + "/v1/payments/confirm";
            return restTemplate.postForObject(confirmUrl, confirmReq, Payment.class);
        }


       public Payment searchByOrder(SearchDto orderId) {

           String confirmUrl = baseUrl + "/v1/payments/confirm";
           return restTemplate.postForObject(confirmUrl, confirmReq, Payment.class);
       }


       public Payment searchByPayment(String paymentKey) {

            return null;
       }



/*
        public PaymentEntity cancel(CancelReq cancelReq) {
            Mono<CancelRes> response = webClient.post()
                    .uri("/v1/payments/{paymentKey}/cancel")
                    .attribute("paymentKey", cancelReq.getPaymentKey())
                    .retrieve()
                    .bodyToMono(CancelRes.class);


            return null;
        }

        public PaymentEntity virtual(VirtualReq virtualReq) {
            Mono<VirtualRes> response = webClient.post()
                    .uri("/v1/virtual-accounts")
                    .bodyValue(virtualReq)
                    .retrieve()
                    .bodyToMono(VirtualRes.class);
            return null;
        }*/

}

// --- 헤더의 인증객체 ---
//Basic Auth → Authorization: Basic <Base64(user:pass)>
//Bearer Token → Authorization: Bearer <token>
//setBasicAuth()나 setBearerAuth()를 쓰는 이유는 자동으로 Authorization 헤더를 올바른 형식으로 만들어 주기 때문
//단순히 header("Authorization", ...) 직접 넣어도 되지만, 인코딩 등을 수동으로 처리해야 함


