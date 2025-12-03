package com.fhk.store.service;

import com.fhk.store.dto.LicenseDto;
import com.fhk.store.repository.StoreRepository;
import com.fhk.store.domain.StoreEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final ModelMapper modelMapper;
    private final StoreRepository storeRepo;

    public LicenseDto.Res getLicense(LicenseDto.Req licenseDtoReq) {

        Optional<StoreEntity> storeEntity = storeRepo.findById(licenseDtoReq.getAccountId());
        if (storeEntity.isEmpty()) {
            return LicenseDto.Res.builder()
                    .isRegistered(false).build();
        }
        return LicenseDto.Res.builder()
                .isRegistered(true)
                .storeStatus(storeEntity.get().getStatus()) .build();
    }


 /*   public void createLicense(LicenseReq req) {
        storeRepository.save(modelMapper.map(req, StoreEntity.class));

    }


    public void modifyLicense(LicenseReq req) {
        storeRepository.save(modelMapper.map(req, StoreEntity.class));

    }*/


    public void deleteLicense(Long accountReq, String deleteCondition) {


    }


    public void getStore() {


    }


    public void getStoreList() {


    }
}
