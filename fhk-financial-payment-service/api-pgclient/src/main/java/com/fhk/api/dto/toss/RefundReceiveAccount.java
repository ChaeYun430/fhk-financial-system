package com.fhk.api.dto.toss;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundReceiveAccount {

    private String bank;
    private String accountNumber;
    private String holderName;
}
