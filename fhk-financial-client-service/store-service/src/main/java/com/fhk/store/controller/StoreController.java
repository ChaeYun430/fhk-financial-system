package com.fhk.store.controller;


import com.fhk.common.api.ApiResponse;
import com.fhk.security.core.record.FhkUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    // INTENT : 가맹점주의 사용자 계정과 가맹점 정보를 결합한 계정 도메인



    // POLICY : 관리자/본인이 가맹점 정보 읽기
    @GetMapping()
    public ResponseEntity<?> getStore(@AuthenticationPrincipal FhkUserPrincipal principal) {
        Long accountId = principal.id();


        return ApiResponse.ok(principal);
    }

    // POLICY : 현재 계정을 가맹점 계정으로 연결한다.
    @PostMapping()
    public ResponseEntity<?> createStore(@AuthenticationPrincipal FhkUserPrincipal principal) {
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
