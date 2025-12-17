package com.fhk.store.domain;

import com.fhk.core.entity.BaseTimeEntity;
import com.fhk.store.constant.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_tbl")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity extends BaseTimeEntity {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String storeId;

    @Column(name = "owner_account_id", nullable = false)
    private Long owner;   // 가맹점주 계정 id

    @Column(name = "store_name", nullable = false, length = 100)
    private String storeName;   // 상호명

    @Column(name = "business_no", nullable = false, length = 20, unique = true)
    private String businessNo;  // 사업자등록번호

    @Column(name = "bank_account", nullable = false, length = 50)
    private String bankAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StoreStatus status;
/*
    @Column(nullable = false, length = 50)
    private String category; // 업종

    @Column(nullable = false, length = 200)
    private String address; // 주소

    @Column(nullable = false, length = 30)
    private String phone; // 연락처

    @Column(name = "logo_url", length = 255)
    private String logoUrl; // 로고 이미지 URL*/

    public StoreEntity changeStatus(StoreStatus status) {
        this.status = status;
        return this;
    }
}
