package com.fhk.store.dto;

import com.fhk.store.constant.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LicenseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {
        private Long accountId;   // 가맹점주 계정 id
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res {
        private Boolean isRegistered;
        private StoreStatus storeStatus;
    }
}
