package com.fhk.verification.dto;

import lombok.Builder;
import lombok.Data;

public class IdResultDto {

    @Data
    public static class Req {

        private String txId;
        private String sessionKey;      // 암호화된 세션 키
    }

    @Data
    @Builder
    public static class Res {
        private String resultType;
        private IdResult idResult;

        @Data
        public static class IdResult {

            private String txId;
            private String status;
            private String userIdentifier;
            private String userCiToken;
            private String signature;
            private String randomValue;

            private String completedDt;
            private String requestedDt;

            private PersonalData personalData;

            @Data
            public static class PersonalData {

                private String ci;
                private String name;
                private String birthday;
                private String gender;
                private String nationality;
                private String ci2;
                private String di;
                private String ciUpdate;
            }
        }
    }
}
