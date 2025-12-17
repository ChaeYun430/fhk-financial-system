package com.fhk.verification.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Log4j2
@Component
public class TossTokenProvider {

    private volatile String accessToken;
    private volatile long expiredAt;

    @Value("${toss.cert.client-id}")
    private String clientId;

    @Value("${toss.cert.client-secret}")
    private String clientSecret;



    public synchronized String getToken() {
        if (accessToken == null || System.currentTimeMillis() > expiredAt) {
            log.info("token expired or null");
            issueToken();
            log.info("token issued");
        }
        return accessToken;
    }

    private void issueToken() {
        Map res = RestClient.create().post()
                .uri("https://oauth2.cert.toss.im/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                        "grant_type=client_credentials&" +
                                "client_id=" + clientId + "&" +
                                "client_secret=" + clientSecret + "&" +
                                "scope=ca"
                )
                .retrieve()
                .body(Map.class);
        log.info(res.toString());
        accessToken = (String) res.get("access_token");
        Integer expiresIn = (Integer) res.get("expires_in");
        expiredAt = System.currentTimeMillis() + (expiresIn - 60) * 1000L;
    }
}
