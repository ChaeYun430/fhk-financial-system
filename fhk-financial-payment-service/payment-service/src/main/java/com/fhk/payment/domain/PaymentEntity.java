package com.fhk.payment.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private String orderId;

    @Column
    private long amount;


    @Builder
    public PaymentEntity(String orderId, long amount) {
        this.orderId = orderId;
        this.amount = amount;
    }


}
