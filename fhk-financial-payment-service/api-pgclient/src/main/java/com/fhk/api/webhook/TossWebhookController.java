package com.fhk.api.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TossWebhookController {

    private final TossWebhookService tossWebhookService;

    @PostMapping("/webhooks/toss")
    public ResponseEntity<Void> handleWebhook(
            @RequestHeader("Toss-Signature") String signature,
            @RequestBody String rawBody
    ) {
        tossWebhookService.handleWebhook(signature, rawBody);
        return ResponseEntity.ok().build(); // 토스는 200 응답만 요구
    }



}
