package com.fhk.customer.dto;

import com.fhk.customer.constant.CustomerStatus;
import lombok.*;

public class ChangeInfoDto {

    @Data
    public static class Req {

        private Long accountId;

        private String customerName;

        private String birth;

        private String email;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {
        private CustomerStatus customerStatus;
    }
}
