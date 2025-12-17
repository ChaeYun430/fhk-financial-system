package com.fhk.customer.controller;

import com.fhk.common.api.ApiResponse;
import com.fhk.customer.constant.CustomerStatus;
import com.fhk.customer.dto.ChangeInfoDto;
import com.fhk.customer.dto.ChangeStatusDto;
import com.fhk.customer.dto.LicenseDto;
import com.fhk.customer.dto.RegisterDto;
import com.fhk.customer.service.CustomerService;
import com.fhk.security.core.record.FhkUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody RegisterDto.Req registerReq,
                                      @AuthenticationPrincipal FhkUserPrincipal principal) {
        registerReq.setAccountId(principal.id());
        log.info("register customer: {}", registerReq);
        RegisterDto.Res res = customerService.register(registerReq);

        return ApiResponse.ok(res);
    }


    // https://www.notion.so/Restful-27dce9798014802f8520c6ccf01ed2f3
    @PutMapping("/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable CustomerStatus status,
                                      @AuthenticationPrincipal FhkUserPrincipal principal) {

        ChangeStatusDto.Req changeReq = ChangeStatusDto.Req.builder()
                .accountId(principal.id())
                .customerStatus(status).build();
        log.info("changeStatus customer: {}", changeReq);
        ChangeStatusDto.Res res = customerService.changeStatus(changeReq);

        return ApiResponse.ok(res);
    }


    @PutMapping("/info")
    public ResponseEntity<?> changeInfo(@RequestBody ChangeInfoDto.Req changeReq,
                                      @AuthenticationPrincipal FhkUserPrincipal principal) {
        changeReq.setAccountId(principal.id());
        log.info("changeInfo customer: {}", changeReq);
        ChangeInfoDto.Res res = customerService.changeInfo(changeReq);

        return ApiResponse.ok(res);
    }


    @GetMapping("/license")
    public ResponseEntity<?> getLicense(@AuthenticationPrincipal FhkUserPrincipal principal) {

        LicenseDto.Req licenseReq = LicenseDto.Req.builder()
                    .accountId(principal.id()).build();
        log.info("changeInfo customer: {}", licenseReq);
        LicenseDto.Res res = customerService.getLicense(licenseReq);

        return ApiResponse.ok(res);
    }

}
