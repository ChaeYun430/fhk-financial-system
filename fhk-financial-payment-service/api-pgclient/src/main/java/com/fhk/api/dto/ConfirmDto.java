package com.fhk.api.dto;


import com.fhk.api.dto.toss.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ConfirmDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {
        private String paymentKey;
        private String orderId;
        private int amount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res {
        private Payment payment;
    }

}
