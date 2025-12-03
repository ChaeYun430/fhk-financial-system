package com.fhk.customer.dto;

import com.fhk.customer.constant.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterDto {

    @Getter
    @NoArgsConstructor
    public static class Req {
        private Long accountId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {
        private CustomerStatus customerStatus;
    }

}
