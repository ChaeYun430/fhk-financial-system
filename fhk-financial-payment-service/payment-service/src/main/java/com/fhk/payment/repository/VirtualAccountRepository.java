package com.fhk.payment.repository;

import com.fhk.payment.domain.VirtualAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualAccountRepository extends JpaRepository<VirtualAccountEntity, String> {

}
