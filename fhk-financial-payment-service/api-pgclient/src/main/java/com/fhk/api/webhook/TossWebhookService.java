package com.fhk.api.webhook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class TossWebhookService {


    private final String webhookSecret;

    public TossWebhookService(@Value("${toss.payments.security-key}")String webhookSecret) {
        this.webhookSecret = webhookSecret;
    }

    public void handleWebhook(String signature, String rawBody) {
        if (!verifySignature(signature, rawBody)) {
            throw new IllegalArgumentException("Invalid signature");
        }

        // 유효하면 실제 비즈니스 로직 호출
        //processEvent(rawBody);
    }

    private boolean verifySignature(String signature, String rawBody) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(webhookSecret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);

            byte[] hash = mac.doFinal(rawBody.getBytes());
            String expected = Base64.getEncoder().encodeToString(hash);

            return expected.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
