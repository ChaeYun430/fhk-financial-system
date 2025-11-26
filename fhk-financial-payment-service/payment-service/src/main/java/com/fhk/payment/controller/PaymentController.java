package com.fhk.payment.controller;

import com.fhk.api.cllient.TossClient;
import com.fhk.api.dto.ConfirmDto;
import com.fhk.api.dto.PayDto;
import com.fhk.api.dto.toss.Payment;
import com.fhk.common.api.ApiResponse;
import com.fhk.common.api.ApiWrapper;
import com.fhk.payment.service.PaymentService;
import com.fhk.security.core.record.FhkUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Import({TossClient.class})
public class PaymentController {

    private final PaymentService paymentService;

    // POLICY : 고객이 결제 요청 전 데이터 무결성을 위한 결제 데이터 저장
    @PostMapping("/pay")
    public ResponseEntity<ApiWrapper<PayDto.Res>> pay(
            @RequestBody PayDto.Req payReq,
            @AuthenticationPrincipal FhkUserPrincipal fhkUser) {

        Long accountId = fhkUser.id();
        PayDto.Res payRes = paymentService.pay(payReq, accountId);

        return ApiResponse.ok(payRes);
    }


    //  POLICY : 승인 결과에 대해 타 서비스에 상태전이 이벤트 전송, 고객에게 알림 발송
    //           Toss로부터 받은 리다이렉션으로 작동
    @GetMapping("/confirm")
    public ResponseEntity<ApiWrapper<ConfirmDto.Res>> confirm(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam int amount) {

        ConfirmDto.Req confirmReq = new ConfirmDto.Req(paymentKey, orderId, amount);
        ConfirmDto.Res confirmRes = paymentService.confirm(confirmReq);

        return ApiResponse.ok(confirmRes);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> searchByOrder(@PathVariable String orderId,
                                                @AuthenticationPrincipal FhkUserPrincipal fhkUser) {

        return null;
    }
    @GetMapping("/{paymentKey}")
    public ResponseEntity<Payment> searchByPayment(@RequestBody String paymentKey) {
        return null;
    }


/*    @PostMapping("/cancel")
    public ResponseEntity<PayRes> cancel(@RequestBody PayReq payReq) {
        return null;
    }*/



    //searchByAccount


}

