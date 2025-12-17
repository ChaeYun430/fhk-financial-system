package com.fhk.customer.dto;

import com.fhk.customer.constant.CustomerStatus;
import lombok.*;

public class ChangeStatusDto {

    @Builder
    @Data
    public static class Req {

        private Long accountId;

        private CustomerStatus customerStatus;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {
        private CustomerStatus customerStatus;
    }
}
