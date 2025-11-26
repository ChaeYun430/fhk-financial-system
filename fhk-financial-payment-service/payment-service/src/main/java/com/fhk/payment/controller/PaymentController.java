package com.fhk.payment.controller;

import com.fhk.api.cllient.TossClient;
import com.fhk.api.dto.ConfirmDto;
import com.fhk.api.dto.PayDto;
import com.fhk.api.dto.toss.Payment;
import com.fhk.payment.service.PaymentService;
import com.fhk.security.core.record.FhkUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Import;
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


    @PostMapping("/confirm")
    public ResponseEntity<ConfirmDto.Res> confirm(@RequestBody ConfirmDto.Req confirmReq,
                                                  @AuthenticationPrincipal FhkUserPrincipal fhkUser) {

        Long accountId = fhkUser.id();
        paymentService.confirm(confirmReq, accountId);
        return null;
    }

    @GetMapping("/confirm")
    public ResponseEntity confirmPayment(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam int amount) {

        ConfirmDto.Req req = new ConfirmDto.Req(paymentKey, orderId, amount);

        log.info("get confirm"+ req.toString());
        // TODO : store에게 notification

        // 처리 후 리다이렉트 페이지
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

