package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import com.melkeinkood.ticket_guru.model.Asiakastyyppi;

@Getter
@Setter
public class AsiakastyyppiDTO {
    private Long asiakastyypiId;
    private String asiakastyyppi;
    
    public AsiakastyyppiDTO(Asiakastyyppi asiakasTyyppi) {
        this.asiakastyypiId = asiakasTyyppi.getAsiakastyyppiId();
        this.asiakastyyppi = asiakasTyyppi.getAsiakastyyppi();
    }

    public AsiakastyyppiDTO(){}
}
