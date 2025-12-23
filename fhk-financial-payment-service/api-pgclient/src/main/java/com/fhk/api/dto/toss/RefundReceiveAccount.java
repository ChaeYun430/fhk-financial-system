package com.fhk.api.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundReceiveAccount {

    private String bank;
    private String accountNumber;
    private String holderName;
}
