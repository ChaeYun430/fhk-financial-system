package com.fhk.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhk.api.cllient.TossClient;
import com.fhk.api.dto.toss.Payment;
import com.fhk.payment.domain.PaymentEntity;
import com.fhk.api.dto.ConfirmDto;
import com.fhk.api.dto.PayDto;
import com.fhk.payment.dto.EventDto;
import com.fhk.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final ModelMapper modelMapper;
    private final Executor executor = Executors.newFixedThreadPool(4);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    //private final OutboxRepository outboxRepository;


    // INTENT : 프론트에서 처리하여 pg로 바로 보내고 성공시 응답을 confirm으로 리다이렉션
    // customer
    // public PaymentEntity pay(PayDto.Req payReq) {
    //     PaymentEntity payment = new PaymentEntity(payReq.getOrderId(), payReq.getAmount());
    //     payment = paymentRepo.save(payment);
    //     return payment;
    // }


    // merchant
    //@PreAuthorize("@authEvaluator.authorizeReview(#req.reviewId, principal.getUsername())")
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
