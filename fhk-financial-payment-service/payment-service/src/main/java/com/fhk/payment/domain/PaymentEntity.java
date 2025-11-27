package com.fhk.payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_tbl")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentKey;

    @Column
    private String accountId;

    @Column
    private String orderId;

    @Column
    private long amount;

    @Column
    private String status;

    @Column
    private LocalDateTime requestedAt;
    @Column
    private LocalDateTime approvedAt;
    @Column
    private LocalDateTime canceledAt;

    @Builder
    public PaymentEntity(String orderId, long amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    // Helper method
    public void changeStatus(String status) {
        this.status = status;
    }

}
