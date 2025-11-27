package com.fhk.api.cllient;

import com.fhk.api.dto.CancelDto;
import com.fhk.api.dto.ConfirmDto;
import com.fhk.api.dto.VirtualDto;
import com.fhk.api.dto.toss.Payment;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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


        public Payment searchByPayment(String paymentKey) {

            String searchUrl = baseUrl + "/v1/payments/{paymentKey}";
            Map<String, String> param = new HashMap<>();
            param.put("paymentKey", paymentKey);
            return restTemplate.getForObject(searchUrl, Payment.class, param);
        }

       public Payment searchByOrder(String orderId) {

           String searchUrl = baseUrl + "/v1/payments/orders/{orderId}";
           Map<String, String> param = new HashMap<>();
           param.put("orderId", orderId);
           return restTemplate.getForObject(searchUrl, Payment.class, param);
       }


       //   TODO : dto 필드가 완전히 일치해야 하는지
        public Payment cancel(CancelDto.Req cancelReq) {

           String cancelUrl = baseUrl + "/v1/payments/{paymentKey}/cancel";
           Map<String, String> param = new HashMap<>();
           param.put("paymentKey", cancelReq.getPaymentKey());
           return restTemplate.postForObject(cancelUrl, cancelReq, Payment.class, param);
        }


        public Payment virtual(VirtualDto.Req virtualReq) {

            String virtualUrl = baseUrl + "/v1/virtual-accounts";
            return restTemplate.postForObject(virtualUrl, virtualReq, Payment.class);
        }

}

// --- 헤더의 인증객체 ---
//Basic Auth → Authorization: Basic <Base64(user:pass)>
//Bearer Token → Authorization: Bearer <token>
//setBasicAuth()나 setBearerAuth()를 쓰는 이유는 자동으로 Authorization 헤더를 올바른 형식으로 만들어 주기 때문
//단순히 header("Authorization", ...) 직접 넣어도 되지만, 인코딩 등을 수동으로 처리해야 함


