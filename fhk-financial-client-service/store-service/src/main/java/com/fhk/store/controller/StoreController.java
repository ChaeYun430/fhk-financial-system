package com.fhk.store.controller;


import com.fhk.common.api.ApiResponse;
import com.fhk.security.core.record.FhkUserPrincipal;
import com.fhk.store.dto.LicenseDto;
import com.fhk.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    // INTENT : 가맹점주의 사용자 계정과 가맹점 정보를 결합한 계정 도메인

    @GetMapping("/license")
    public ResponseEntity<?> getLicense(@AuthenticationPrincipal FhkUserPrincipal fhkPrincipal) {

        LicenseDto.Req licenseReq = LicenseDto.Req.builder()
                        .accountId(fhkPrincipal.id()).build();
        LicenseDto.Res licenseRes = storeService.getLicense(licenseReq);
        return ApiResponse.ok(licenseRes);
    }

    // POLICY : 관리자/본인이 가맹점 정보 읽기
    @GetMapping()
    public ResponseEntity<?> getStore(@AuthenticationPrincipal FhkUserPrincipal principal) {
        Long accountId = principal.id();


        return ApiResponse.ok(principal);
    }

    // POLICY : 현재 계정을 가맹점 계정으로 연결한다.
    @PostMapping()
    public ResponseEntity<?> registerStore(@AuthenticationPrincipal FhkUserPrincipal principal) {
        Long accountId = principal.id();

        return ApiResponse.ok(principal);
    }

    // POLICY : 본인이 가맹점 정보 수정
    @PutMapping()
    public ResponseEntity<?> modifyStore(@AuthenticationPrincipal FhkUserPrincipal principal) {
        Long accountId = principal.id();


        return ApiResponse.ok(principal);
    }
}
