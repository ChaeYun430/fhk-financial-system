package com.fhk.customer;

import com.fhk.core.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String Status;

}
