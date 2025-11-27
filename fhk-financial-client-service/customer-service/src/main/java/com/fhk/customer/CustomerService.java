package com.fhk.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RedisTemplate<String, Long> restTemplate;
    private final CustomerRepository customerRepo;

    // 신규 회원 등록
    public void register(RegisterReq registerReq) {

        CustomerEntity customerEntity = new CustomerEntity(registerReq.getCustomerId(), "");
        customerRepo.save(customerEntity);

        restTemplate.opsForValue().set("", registerReq.getCustomerId());


    }

    // Look aside Cache


}
