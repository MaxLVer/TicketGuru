package com.melkeinkood.ticket_guru.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TapahtumaLipputyyppiDTO {

    private long tapahtumaLipputyyppiId;
    @NotNull(message = "TapahtumaId ei saa olla tyhjä")
    private long tapahtumaId;
    @NotNull(message = "AsiakastyyppiId ei saa olla tyhjä")
    private long asiakastyyppiId;
    @NotNull(message = "Hinta ei saa olla tyhjä")
    @Min(value = 0, message = "Hinta ei voi olla negatiivinen")
    private BigDecimal hinta;

    public TapahtumaLipputyyppiDTO() {}

    public TapahtumaLipputyyppiDTO(long tapahtumaLipputyyppiId, long tapahtumaId, long asiakastyyppiId, BigDecimal hinta) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
        this.tapahtumaId = tapahtumaId;
        this.asiakastyyppiId = asiakastyyppiId;
        this.hinta = hinta;
    }
}