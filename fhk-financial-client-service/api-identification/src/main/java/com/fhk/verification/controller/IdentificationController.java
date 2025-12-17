package com.fhk.verification.controller;

import com.fhk.verification.client.TossIdentificationClient;
import com.fhk.verification.dto.IdRequestDto;
import com.fhk.verification.dto.IdResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/identification")
@RequiredArgsConstructor
@Log4j2
public class IdentificationController {

    private final TossIdentificationClient tossIdentificationClient;

    /**
     * 본인인증 요청
     */
    @PostMapping("/request")
    public ResponseEntity<IdRequestDto.Res> request(@RequestBody IdRequestDto.Req req) {
        IdRequestDto.Res res = tossIdentificationClient.requestIdentification(req);
        return ResponseEntity.ok(res);
    }

    /**
     * 본인인증 결과 조회
     */
    @PostMapping("/result")
    public ResponseEntity<IdResultDto.Res> result(@RequestBody IdResultDto.Req req) {
        IdResultDto.Res res = tossIdentificationClient.getResult(req);
        return ResponseEntity.ok(res);
    }
}
