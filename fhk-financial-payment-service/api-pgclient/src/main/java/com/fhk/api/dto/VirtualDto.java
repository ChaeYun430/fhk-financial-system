package com.fhk.api.dto;

import com.fhk.api.dto.toss.VirtualAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class VirtualDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Req {

        private Integer amount;
        private String bank;
        private String customerName;
        private String orderId;
        private String orderName;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {
        private VirtualAccount virtualAccount;
    }
}
