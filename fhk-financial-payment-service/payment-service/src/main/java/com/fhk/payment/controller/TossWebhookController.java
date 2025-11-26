package com.fhk.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks/toss")
public class TossWebhookController {

    private final TossWebhookService tossWebhookService;

    public TossWebhookController(TossWebhookService tossWebhookService) {
        this.tossWebhookService = tossWebhookService;
    }

    @PostMapping
    public ResponseEntity<Void> handleWebhook(
            @RequestHeader("Toss-Signature") String signature,
            @RequestBody String rawBody
    ) {
        tossWebhookService.handleWebhook(signature, rawBody);
        return ResponseEntity.ok().build(); // 토스는 200 응답만 요구
    }
}
