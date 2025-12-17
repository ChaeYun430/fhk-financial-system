package com.fhk.customer.service;

import com.fhk.customer.constant.CustomerStatus;
import com.fhk.customer.domain.CustomerEntity;
import com.fhk.customer.dto.ChangeInfoDto;
import com.fhk.customer.dto.ChangeStatusDto;
import com.fhk.customer.dto.LicenseDto;
import com.fhk.customer.dto.RegisterDto;
import com.fhk.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RedisTemplate<String, String> redisTemplate;
    private final CustomerRepository customerRepo;
    private final ModelMapper modelMapper;


    // 신규 회원 등록
    public RegisterDto.Res register(RegisterDto.Req registerReq) {

        CustomerEntity customerEntity = modelMapper.map(registerReq, CustomerEntity.class);
        customerRepo.save(customerEntity.changeCustomerStatus(CustomerStatus.ACTIVE));

        String redisKey = "fhk:financial:account:" + customerEntity.getAccountId() + ":status";
        redisTemplate.opsForValue().set(redisKey, String.valueOf(customerEntity.getStatus()));
        return RegisterDto.Res.builder()
                .customerStatus(CustomerStatus.ACTIVE).build();
    }


    // 회원 상태 변경
    @Transactional
    public ChangeStatusDto.Res changeStatus(ChangeStatusDto.Req changeReq) {

        CustomerEntity customerEntity = customerRepo.findCustomerEntityByAccountId(changeReq.getAccountId())
                                                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        customerEntity.changeCustomerStatus(changeReq.getCustomerStatus());

        String redisKey = "fhk:financial:account:" + customerEntity.getCustomerId() + ":status";
        redisTemplate.opsForValue().set(redisKey, String.valueOf(customerEntity.getStatus()));

        return ChangeStatusDto.Res.builder()
                .customerStatus(customerEntity.getStatus()).build();
    }


    // 회원 정보 변경
    @Transactional
    public ChangeInfoDto.Res changeInfo(ChangeInfoDto.Req changeReq) {

        CustomerEntity customerEntity = customerRepo.findCustomerEntityByAccountId(changeReq.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        modelMapper.map(changeReq, customerEntity);

        return ChangeInfoDto.Res.builder()
                .customerStatus(customerEntity.getStatus()).build();
    }

    // 회원 정보 조회
    public LicenseDto.Res getLicense(LicenseDto.Req licenseDtoReq) {

        CustomerEntity customerEntity = customerRepo.findCustomerEntityByAccountId(licenseDtoReq.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return  modelMapper.map(customerEntity, LicenseDto.Res.class);
    }
}
