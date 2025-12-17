package com.fhk.customer.repository;

import com.fhk.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    Optional<CustomerEntity> findCustomerEntityByAccountId(Long accountId);
}
