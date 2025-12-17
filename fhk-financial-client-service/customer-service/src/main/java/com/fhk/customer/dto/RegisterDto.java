package com.fhk.customer.dto;

import com.fhk.customer.constant.CustomerStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@NoArgsConstructor
public class RegisterDto {

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
