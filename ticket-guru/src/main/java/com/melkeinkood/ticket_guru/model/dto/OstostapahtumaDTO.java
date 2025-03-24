package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.melkeinkood.ticket_guru.model.Lippu;

import jakarta.validation.constraints.NotNull;


@Getter
@Setter

public class OstostapahtumaDTO extends RepresentationModel<OstostapahtumaDTO>{
    private Long ostostapahtumaId;
    private LocalDateTime myyntiaika;
    @NotNull
    private Long kayttajaId;
    private List<Long> lippuIdt;

    public OstostapahtumaDTO(){}

    public OstostapahtumaDTO(Long ostostapahtumaId, LocalDateTime myyntiaika, Long kayttajaId){
        this.ostostapahtumaId = ostostapahtumaId;
        this.myyntiaika = myyntiaika;
        this.kayttajaId = kayttajaId;
    }

    public OstostapahtumaDTO(Long ostostapahtumaId, LocalDateTime myyntiaika, Long kayttajaId, List<Long> lippuIdt){
        this.ostostapahtumaId = ostostapahtumaId;
        this.myyntiaika = myyntiaika;
        this.kayttajaId = kayttajaId;
        this.lippuIdt = lippuIdt;
    }
}