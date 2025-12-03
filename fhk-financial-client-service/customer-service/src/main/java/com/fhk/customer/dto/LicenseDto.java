package com.fhk.customer.dto;

import com.fhk.customer.constant.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LicenseDto {

    @NoArgsConstructor
    @Getter
    public static class Req {
        private Long accountId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res {
        private Boolean isRegistered;
        private CustomerStatus customerStatus;
    }
}
