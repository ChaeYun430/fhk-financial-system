package com.fhk.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class SearchDto {

    @Getter
    @NoArgsConstructor
    public static class OrderId{

        private String orderId;
    }

    @Getter
    @NoArgsConstructor
    public static class PaymentKey{

        private String PaymentKey;
    }

    @Getter
    @NoArgsConstructor
    public static class Res{

        private PaymentKey paymentKey;
    }
}
