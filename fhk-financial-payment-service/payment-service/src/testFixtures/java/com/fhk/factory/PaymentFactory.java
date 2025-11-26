package com.fhk.factory;

import com.fhk.api.dto.ConfirmDto;
import com.fhk.api.dto.PayDto;

public class PaymentFactory {

    public static PayDto.Req getPayReq() {
        return PayDto.Req.builder()
                .orderId("orderId-test")
                .amount(1111).build();
    }

    public static ConfirmDto.Req getConfirmReq() {
        return ConfirmDto.Req.builder()
                .paymentKey("661f790a-4cf9-465a-a68c-21d1f0fea361")
                .orderId("orderId-test")
                .amount(1111).build();
    }


}
