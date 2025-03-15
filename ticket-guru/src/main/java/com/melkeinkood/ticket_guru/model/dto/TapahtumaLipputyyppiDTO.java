package com.melkeinkood.ticket_guru.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TapahtumaLipputyyppiDTO {

    private long tapahtumaLipputyyppiId;
    private long tapahtumaId;
    private long asiakastyyppiId;
    private BigDecimal hinta;

    public TapahtumaLipputyyppiDTO() {}

    public TapahtumaLipputyyppiDTO(long tapahtumaLipputyyppiId, long tapahtumaId, long asiakastyyppiId, BigDecimal hinta) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
        this.tapahtumaId = tapahtumaId;
        this.asiakastyyppiId = asiakastyyppiId;
        this.hinta = hinta;
    }
}
