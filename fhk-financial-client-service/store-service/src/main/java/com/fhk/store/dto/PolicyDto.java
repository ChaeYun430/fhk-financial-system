package com.fhk.store.dto;

import lombok.Data;

public class PolicyDto {

    @Data
    public static class Req {

        private Boolean termsRequired;

        private Boolean marketing;
    }


    public static class Res {}
}
