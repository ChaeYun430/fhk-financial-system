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

    @Id     // 회원의 서비스 내부 접근
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;

    // 회원의 외부 접근
    @Column(nullable = false, unique = true)
    private Long accountId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatus Status;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String birth;

    @Column
    private String email;

    public CustomerEntity changeCustomerStatus(CustomerStatus status) {
        this.Status = status;
        return this;
    }

}
