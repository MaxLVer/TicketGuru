package com.melkeinkood.ticket_guru.model.dto;

import java.math.BigDecimal;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TapahtumaLipputyyppiDTO {

    private Long tapahtumaLipputyyppiId;
    private Long tapahtumaId; 
    private String tapahtumaHref; 
    private Long asiakastyyppiId; 
    private String asiakastyyppiHref; 
    private BigDecimal hinta;

    public TapahtumaLipputyyppiDTO(Long tapahtumaLipputyyppiId, Long tapahtumaId, String tapahtumaHref, Long asiakastyyppiId, String asiakastyyppiHref, BigDecimal hinta) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
        this.tapahtumaId = tapahtumaId;
        this.tapahtumaHref = tapahtumaHref;
        this.asiakastyyppiId = asiakastyyppiId;
        this.asiakastyyppiHref = asiakastyyppiHref;
        this.hinta = hinta;
    }

}
