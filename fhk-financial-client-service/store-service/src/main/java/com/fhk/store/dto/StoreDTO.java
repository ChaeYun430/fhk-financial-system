package com.fhk.store.dto;

import com.fhk.store.constant.BusinessType;
import com.fhk.store.constant.StoreStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class StoreDTO {

    private Long storeId;

    private Long accountId;   // 가맹점주 계정 id

    // 가맹점주 인증
    private String businessNo;  // 사업자등록번호

    // 상점 기본 정보
    private BusinessType businessType;
    private String storeName;   // 상호명

    // 활동 상태
    private StoreStatus storeStatus;

    // 이벤트 소비

    public class Account {

        private String bankCode;
        private String accountNumber; // max 20 chars
        private String holderName;    // 한글 30자, 영문 60자

        // getters/setters
    }

        // 추가 메타데이터 (nullable) — 최대 5개 key-value
        private Map<String, String> metadata;



}
