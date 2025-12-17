package com.fhk.verification.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class IdRequestDto {

    @Data
    public static class Req {
/*
        private String requestId;*/

        private String requestType;     // USER_PERSONAL, USER_NONE 등
        private String requestUrl;      // 인증 완료 후 리다이렉트 URL
        private String triggerType;     // APP_SCHEME, PUSH 등

        private String userName;        // 암호화된 사용자 이름
        private String userPhone;       // 암호화된 전화번호
        private String userBirthday;    // 암호화된 생년월일

        private String sessionKey;      // 암호화된 세션 키

    }

//    {
//  "ci": "CI01100000000011000000000110000000001100000000011000000000110000000001100000000011000000",
//  "di": "DI01100000000011000000000110000000001100000000011000000000110000",
//  "userName": "김토스",
//  "userBirthday": "19930324",
//  "gender": "FEMALE",
//  "nationality": "LOCAL"
//}
    @Data
    public static class Res {

        private String resultType; // SUCCESS / FAIL
        private Success success;
        private Fail fail;

        @Getter
        @Setter
        public static class Success {
            private String txId;
            private String appScheme;
            private String androidAppUri;
            private String iosAppUri;
            private String requestedDt;
            private String authUrl; // 표준창일 때만 내려옴
        }

        @Getter
        @Setter
        public static class Fail {
            private String errorCode;
            private String reason;
        }
    }
}
