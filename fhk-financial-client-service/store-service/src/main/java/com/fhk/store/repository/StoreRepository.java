package com.fhk.store.repository;

import com.fhk.store.domain.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, String> {

    Optional<StoreEntity> findStoreEntityByAccountId(Long accountId);

}
