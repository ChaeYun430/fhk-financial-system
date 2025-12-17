package com.fhk.customer.dto;

import com.fhk.customer.constant.CustomerStatus;
import lombok.*;

@NoArgsConstructor
@Getter
public class LicenseDto {

    @Data
    @Builder
    public static class Req {

        private Long accountId;

    }

    @Data
    public static class Res {

        private String customerId;

        private String customerName;

        private String birth;

        private String email;

        private CustomerStatus customerStatus;
    }
}
