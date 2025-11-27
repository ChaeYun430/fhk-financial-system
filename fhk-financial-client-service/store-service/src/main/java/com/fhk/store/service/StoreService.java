package com.fhk.store.service;

import com.fhk.store.repository.StoreRepository;
import com.fhk.store.domain.StoreEntity;
import com.fhk.store.dto.LicenseReq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final ModelMapper modelMapper;
    private final StoreRepository storeRepository;

    public void getLicense(Long accountReq) {
        // TODO : 레디스 캐시에 저장
        // INTENT : 자주 사용되는 정보


    }

    public void createLicense(LicenseReq req) {
        storeRepository.save(modelMapper.map(req, StoreEntity.class));

    }


    public void modifyLicense(LicenseReq req) {
        storeRepository.save(modelMapper.map(req, StoreEntity.class));

    }


    public void deleteLicense(Long accountReq, String deleteCondition) {


    }

    public void getStoreList() {


    }


}
