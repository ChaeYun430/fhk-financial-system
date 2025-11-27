package com.fhk.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhk.api.cllient.TossClient;
import com.fhk.api.dto.*;
import com.fhk.api.dto.toss.Payment;
import com.fhk.api.dto.toss.VirtualAccount;
import com.fhk.payment.domain.PaymentEntity;
import com.fhk.payment.domain.VirtualAccountEntity;
import com.fhk.payment.dto.EventDto;
import com.fhk.payment.repository.PaymentRepository;
import com.fhk.payment.repository.VirtualAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@Log4j2
@RequiredArgsConstructor
@Import({TossClient.class, ModelMapper.class, Payment.class})
public class PaymentService {

    // TODO : 특정 토픽을 지정하여 이벤트 발행
    //        데이터베이스에 기록
    //        get 같은 경우는 서비스 계층 스킵
    private final TossClient tossClient;
    private final PaymentRepository paymentRepo;
    private final VirtualAccountRepository virtualAccountRepo;
    private final ModelMapper modelMapper;
    private final Executor executor = Executors.newFixedThreadPool(4);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Payment payment;
    //private final OutboxRepository outboxRepository;


    @PreAuthorize("@authEvaluator.isCustomer(#accountId)")
    public PayDto.Res pay(PayDto.Req payReq, Long accountId) {

       PaymentEntity payment = modelMapper.map(payReq, PaymentEntity.class);
       paymentRepo.save(payment);
        return new PayDto.Res(payReq.getOrderId(), "saved");
    }


    public ConfirmDto.Res confirm(ConfirmDto.Req confirmReq) {

        // TODO : 승인 성공 후  변경사항에 따라 modelmapper 또는 findById 쓰기
        Payment payment = tossClient.confirm(confirmReq);

        CompletableFuture.runAsync(() -> {

            PaymentEntity paymentEntity = modelMapper.map(payment, PaymentEntity.class);
            paymentRepo.save(paymentEntity);

            EventDto event = EventDto.builder()
                    .type("")
                    .action(payment.getStatus())
                    .objectId(paymentEntity.getPaymentKey())
                    .correlationId("").build();
            publishEvent(event);

        }, executor);


        return new ConfirmDto.Res(payment);
    }

    //  THINK : 조회에 결제 객체와 사용자의 매칭을 확인해야 하는가
    @PreAuthorize("@authEvaluator.matchWithOrder(#accountId, #orderId.getOrderId())")
    public Payment searchByOrder(SearchDto.OrderId orderId, Long accountId) {

        return tossClient.searchByOrder(orderId.getOrderId());
    }

    @PreAuthorize("@authEvaluator.matchWithKey(#accountId, #paymentKey.getPaymentKey())")
    public Payment searchByPayment(SearchDto.PaymentKey paymentKey, Long accountId) {

        return tossClient.searchByPayment(paymentKey.getPaymentKey());
    }


    @PreAuthorize("@authEvaluator.matchWithKey(#accountId, #cancelReq.getPaymentKey())")
    public CancelDto.Res cancel(CancelDto.Req cancelReq, Long accountId) {

        Payment payment = tossClient.cancel(cancelReq);

        CompletableFuture.runAsync(() -> {

            PaymentEntity paymentEntity = paymentRepo.findById(cancelReq.getPaymentKey()).orElseThrow();
            paymentEntity.changeStatus(payment.getStatus());
            paymentRepo.save(paymentEntity);

            EventDto event = EventDto.builder()
                    .type("")
                    .action(payment.getStatus())
                    .objectId(paymentEntity.getPaymentKey())
                    .correlationId("").build();
            publishEvent(event);

        }, executor);

        return new CancelDto.Res(payment.getStatus());
    }


    @PreAuthorize("@authEvaluator.isCustomer(#accountId)")
    public VirtualDto.Res virtual(VirtualDto.Req virtualReq, Long accountId) {

        Payment payment = tossClient.virtual(virtualReq);
        VirtualAccount virtualAccount = payment.getVirtualAccount();
        CompletableFuture.runAsync(() -> {

            VirtualAccountEntity virtualAccountEntity  = modelMapper.map(virtualAccount, VirtualAccountEntity.class);
            virtualAccountRepo.save(virtualAccountEntity);

            EventDto event = EventDto.builder()
                    .type("")
                    .action("")
                    .objectId(virtualAccountEntity.getAccountNumber())
                    .correlationId("").build();
            publishEvent(event);

        }, executor);

        return new VirtualDto.Res(virtualAccount);
    }


    public void publishEvent(EventDto event){

        kafkaTemplate.send("", event.getObjectId(),event.getCorrelationId());
        kafkaTemplate.flush();

        // INTENT : CompletableFuture: 기존 Spring MVC + 일부 비동기 처리 시 사용
        // FIXME : 카프카 깊게 할때 Publisher 클래스로
        // TODO : Outbox 패턴 이론 한 번 더 보고 활용의 필요성 정리

        /*log.info("Publishing event: " + event);
        try {
            String jasonPayload = objectMapper.writeValueAsString(event.getPayload());
            log.info("payload JasonSerialized: " + jasonPayload);
            event.setPayload(jasonPayload);
            log.info("set JasonSerialized payload to event: " + event);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }*/
        //OutboxEntity outboxEntity = modelMapper.map(event, OutboxEntity.class);
        //log.info("Mapping result: " + outboxEntity);
        //outboxRepository.save(outboxEntity);  // JPA가 중복 키 오류 발생 시 예외

    }


}
