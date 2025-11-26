package com.fhk.payment;

import com.fhk.api.dto.ConfirmDto;
import com.fhk.api.dto.toss.Payment;
import com.fhk.factory.PaymentFactory;
import com.fhk.payment.domain.PaymentEntity;
import com.fhk.payment.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Log4j2
@Profile({})
public class PaymentApplicationTests {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void pay(){
        PaymentEntity payment = paymentService.pay(PaymentFactory.getPayReq());
        log.info(payment);
    }

    @Test
    public void confirm(){
        ConfirmDto.Res confirmRes = paymentService.confirm(PaymentFactory.getConfirmReq());
        log.info(confirmRes);
    }

}
