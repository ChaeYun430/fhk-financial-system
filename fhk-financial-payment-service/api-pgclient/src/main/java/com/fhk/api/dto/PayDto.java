package com.fhk.api.dto;

import com.fhk.api.dto.toss.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PayDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {

        private String orderId;
        private long amount;
    }

    @Getter
    @NoArgsConstructor
    public static class Res {

        private Payment payment;

    }

}
