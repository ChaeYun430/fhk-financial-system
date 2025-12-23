package com.fhk.payment.repository;

import com.fhk.payment.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {



}
