package com.fhk.customer.service;

import com.fhk.customer.constant.CustomerStatus;
import com.fhk.customer.domain.CustomerEntity;
import com.fhk.customer.dto.CheckRegisteredDto;
import com.fhk.customer.dto.LicenseDto;
import com.fhk.customer.dto.RegisterDto;
import com.fhk.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RedisTemplate<String, String> resdisTemplate;
    private final CustomerRepository customerRepo;

    // After Look aside Cache
    // POLICY : isRegistered의 결과도 false이면 회원가입 유도
    //          프론트의 api/financial/member/register 페이지로 이동하여
    //          api/financial/customer/register or api/financial/merchant/register를 bff server에게 전달한다.
    public LicenseDto.Res getLicense(LicenseDto.Req licenseDtoReq) {

        Optional<CustomerEntity> customerEntity = customerRepo.findById(licenseDtoReq.getAccountId());
        if (customerEntity.isEmpty()) {
            return LicenseDto.Res.builder()
                    .isRegistered(false).build();
        }
        return LicenseDto.Res.builder()
                .isRegistered(true)
                .customerStatus(customerEntity.get().getStatus()).build();
    }

    // 신규 회원 등록
    public RegisterDto.Res register(RegisterDto.Req registerReq) {

        CustomerEntity customerEntity = CustomerEntity.builder()
                .customerId(registerReq.getAccountId())
                .Status(CustomerStatus.ACTIVE)
                .build();
        customerRepo.save(customerEntity);

        String redisKey = "fhk:financial:account:" + customerEntity.getCustomerId() + ":status";
        resdisTemplate.opsForValue().set(redisKey, String.valueOf(customerEntity.getStatus()));
        return RegisterDto.Res.builder()
                .customerStatus(CustomerStatus.ACTIVE).build();
    }





}
