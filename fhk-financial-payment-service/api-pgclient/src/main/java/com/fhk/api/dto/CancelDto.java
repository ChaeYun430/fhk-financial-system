package com.fhk.api.dto;

import com.fhk.api.dto.toss.RefundReceiveAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CancelDto {

    @Getter
    @NoArgsConstructor
    public static class Req{

        private String paymentKey;
        private String cancelReason;
        private RefundReceiveAccount refundReceiveAccount;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res{

        private String status;
    }

}
