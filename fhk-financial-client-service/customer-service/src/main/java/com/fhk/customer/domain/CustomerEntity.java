package com.fhk.customer.domain;

import com.fhk.core.entity.BaseTimeEntity;
import com.fhk.customer.constant.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_tbl")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerEntity extends BaseTimeEntity {

    // POLICY : 결제 시스템 서비스에 사용자로 등록되었는지 확인
    @Id
    private Long customerId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatus Status;

    private String customerName;
    private String phone;
}
