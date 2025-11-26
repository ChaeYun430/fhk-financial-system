package com.fhk.payment.controller;

import com.fhk.api.cllient.TossClient;
import com.fhk.api.dto.ConfirmDto;
import com.fhk.api.dto.PayDto;
import com.fhk.payment.service.PaymentService;
import com.fhk.security.core.record.FhkUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Import({TossClient.class})
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/confirm")
    public ResponseEntity<ConfirmDto.Res> confirm(@RequestBody ConfirmDto.Req confirmReq,
                                                  @AuthenticationPrincipal FhkUserPrincipal fhkUser) {

        Long accountId = fhkUser.id();
        paymentService.confirm(confirmReq);
        return null;
    }

/*    @PostMapping("/cancel")
    public ResponseEntity<PayRes> cancel(@RequestBody PayReq payReq) {
        return null;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PayRes> searchByOrder(@PathVariable String orderId) {
        tossClient.searchByOrder(orderId);
        return null;
    }

    @GetMapping("/{paymentKey}")
    public ResponseEntity<PayRes> searchByPayment(@RequestBody String paymentKey) {
        return null;
    }*/

    //searchByAccount


}

