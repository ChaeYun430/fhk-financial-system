package com.fhk.payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "virtual_account_tbl")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VirtualAccountEntity {

    @Id
    private String accountNumber;

    @Column
    private Long customerId;

    @Column
    private String customerName;

    @Column
    private String bank;

    @Column
    private LocalDateTime dueDate;

}
