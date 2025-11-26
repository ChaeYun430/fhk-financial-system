package com.fhk.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class EventDto {

    private String type;
    private String objectId;
    private String action;
    private String correlationId;

}
