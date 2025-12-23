package com.fhk.store.service;

import com.fhk.store.constant.StoreStatus;
import com.fhk.store.dto.EventDto;
import com.fhk.store.dto.LicenseDto;
import com.fhk.store.repository.StoreRepository;
import com.fhk.store.domain.StoreEntity;
import com.fhk.verification.client.TossIdentificationClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Import({TossIdentificationClient.class})
public class StoreService {

    private final ModelMapper modelMapper;
    private final StoreRepository storeRepo;
    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TossIdentificationClient tossIdentificationClient;


    public LicenseDto.Res getLicense(LicenseDto.Req licenseDtoReq) {

        Optional<StoreEntity> storeEntity = storeRepo.findStoreEntityByAccountId(licenseDtoReq.getAccountId());
        if (storeEntity.isEmpty()) {
            return LicenseDto.Res.builder()
                    .isRegistered(false).build();
        }
        return LicenseDto.Res.builder()
                .isRegistered(true)
                .storeStatus(storeEntity.get().getStatus()) .build();
    }


    public LicenseDto.Res createLicense(LicenseDto.Req licenseDtoReq) {

        StoreEntity storeEntity = storeRepo.save(modelMapper.map(licenseDtoReq, StoreEntity.class));
        String storeId = storeEntity.getStoreId();

        // 카프카 이벤트만 발송
        // pg seller 등록 (RestClient)
        //  SubmallCreateForm submallCreateForm = new SubmallCreateForm();
        //  Submall submall = tossPaymentsService.submallCreate(submallCreateForm);
        String redisKey = "fhk:financial:account:" + storeId + ":status";
        redisTemplate.opsForValue().set(redisKey, String.valueOf(StoreStatus.APPROVAL_REQUIRED));

        return LicenseDto.Res.builder().storeStatus(StoreStatus.APPROVAL_REQUIRED).build();
    }

    public LicenseDto.Res registerWebhook(String storeId) {
        // pg seller 등록 웹훅 (controller)
        String redisKey = "fhk:financial:account:" + storeId + ":status";
        Optional<StoreEntity> storeEntity1 = storeRepo.findById(storeId);
        storeEntity1.ifPresent(entity -> entity.changeStatus(StoreStatus.PARTIALLY_APPROVED));
        redisTemplate.opsForValue().set(redisKey, String.valueOf(StoreStatus.PARTIALLY_APPROVED));
        return LicenseDto.Res.builder().storeStatus(StoreStatus.PARTIALLY_APPROVED).build();
    }


    public void publishEvent(EventDto event){

        kafkaTemplate.send("", event.getObjectId(), event.getCorrelationId());
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
//1. 가맹점주 정보 등록 및 심사
//시스템에 가맹점주를 공식적인 판매자로 등록하고, 판매 대금 정산을 위한 계좌를 준비하는 초기 단계입니다.
//
//가맹점주 등록/정보 입력:
//가맹점주(개인/법인) 정보: 사업자등록번호, 대표자명, 연락처 등
//정산 계좌 정보: 은행명, 계좌번호, 예금주명
//필요 서류 업로드: 사업자등록증 사본, 통장 사본, 신분증 사본 등
//
//시스템 심사/승인:
//입력된 정보 및 서류의 진위 여부 및 유효성 확인
//(선택) 신용 평가 및 본인 인증
//심사 통과 시, 가맹점주에게 고유한 판매자 ID 부여


    public void deleteLicense(Long accountReq, String deleteCondition) {


    }


    public void getStore() {


    }


    public void getStoreList() {


    }
}
