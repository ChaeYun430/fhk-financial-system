package com.fhk.api.dto.toss;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    private String paymentKey;
    private String orderId;
    private long amount;
    private String currency;
    private String method;
    private String orderName;
    private String status;

    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime canceledAt;

    private Card card;
    private Transfer transfer;
    private VirtualAccount virtualAccount;
    private EasyPay easyPay;

}
