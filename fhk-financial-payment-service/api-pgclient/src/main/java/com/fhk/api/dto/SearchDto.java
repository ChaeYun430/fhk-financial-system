package com.fhk.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SearchDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderId{

        private String orderId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentKey{

        private String PaymentKey;
    }

    @Getter
    @NoArgsConstructor
    public static class Res{

        private PaymentKey paymentKey;
    }
}
