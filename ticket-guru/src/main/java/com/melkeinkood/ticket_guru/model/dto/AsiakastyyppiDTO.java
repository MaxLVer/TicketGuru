package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import com.melkeinkood.ticket_guru.model.Asiakastyyppi;

import jakarta.validation.constraints.NotNull;


@Getter
@Setter
public class AsiakastyyppiDTO {
    private Long asiakastyyppiId;

    @NotNull(message = "Asiakastyyppi ei voi olla tyhjä")
    private String asiakastyyppi;
    
    public AsiakastyyppiDTO(Asiakastyyppi asiakasTyyppi) {
        this.asiakastyyppiId = asiakasTyyppi.getAsiakastyyppiId();
        this.asiakastyyppi = asiakasTyyppi.getAsiakastyyppi();
    }

    public AsiakastyyppiDTO(){}
}
