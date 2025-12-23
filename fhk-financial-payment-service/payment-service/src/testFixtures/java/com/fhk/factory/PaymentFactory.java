package com.fhk.factory;

import com.fhk.api.dto.*;
import com.fhk.api.dto.toss.RefundReceiveAccount;

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

    public static SearchDto.OrderId getSearchOrderId() {
        return SearchDto.OrderId.builder()
                .orderId("test-orderId")
                .build();
    }

    public static SearchDto.PaymentKey getSearchPaymentKey() {
        return SearchDto.PaymentKey.builder()
                .PaymentKey("661f790a-4cf9-465a-a68c-21d1f0fea361").build();
    }

    public static CancelDto.Req getCancelReq() {
        RefundReceiveAccount.builder()
                .accountNumber("")
                .bank("")
                .holderName("").build();

        return CancelDto.Req.builder()
                .paymentKey("661f790a-4cf9-465a-a68c-21d1f0fea361")
                .cancelReason("단순변심")
                .build();
    }

    public static VirtualDto.Req getVirtualReq() {
        return VirtualDto.Req.builder()
                .orderId("orderId-test")
                .bank("")
                .customerName("").build();
    }
}
