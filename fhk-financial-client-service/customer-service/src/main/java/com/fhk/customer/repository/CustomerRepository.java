package com.fhk.customer.repository;

import com.fhk.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByCustomerId(Long customerId);
}
