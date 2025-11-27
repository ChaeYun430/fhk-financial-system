package com.fhk.store.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreDTO {

    private Long storeId;

    private Long accountId;   // 가맹점주 계정 id

    private String storeName;   // 상호명

    private String businessNo;  // 사업자등록번호

    private String bankAccount;
}
