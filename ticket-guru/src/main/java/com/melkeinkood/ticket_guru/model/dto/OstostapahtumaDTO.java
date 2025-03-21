package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter

public class OstostapahtumaDTO extends RepresentationModel<OstostapahtumaDTO>{
    private Long ostostapahtumaId;
    private LocalDateTime myyntiaika;
    private Long kayttajaId;

    public OstostapahtumaDTO(){}

    public OstostapahtumaDTO(Long ostostapahtumaId, LocalDateTime myyntiaika, Long kayttajaId){
        this.ostostapahtumaId = ostostapahtumaId;
        this.myyntiaika = myyntiaika;
        this.kayttajaId = kayttajaId;
    }
}