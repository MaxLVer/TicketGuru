package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.NotNull;


@Getter
@Setter

public class OstostapahtumaDTO extends RepresentationModel<OstostapahtumaDTO>{
    private Long ostostapahtumaId;
    private LocalDateTime myyntiaika;
    @NotNull(message = "KayttajaId ei voi olla tyhjä")
    private Long kayttajaId;
    private BigDecimal summa;
    private List<Long> lippuIdt;

    public OstostapahtumaDTO(){}

    public OstostapahtumaDTO(Long ostostapahtumaId, LocalDateTime myyntiaika, Long kayttajaId){
        this.ostostapahtumaId = ostostapahtumaId;
        this.myyntiaika = myyntiaika;
        this.kayttajaId = kayttajaId;
    }

    public OstostapahtumaDTO(Long ostostapahtumaId, LocalDateTime myyntiaika, Long kayttajaId, BigDecimal summa, List<Long> lippuIdt){
        this.ostostapahtumaId = ostostapahtumaId;
        this.myyntiaika = myyntiaika;
        this.kayttajaId = kayttajaId;
        this.summa = summa;
        this.lippuIdt = lippuIdt;
    }

    public OstostapahtumaDTO(Long ostostapahtumaId, LocalDateTime myyntiaika, Long kayttajaId, List<Long> lippuIdt){
        this.ostostapahtumaId = ostostapahtumaId;
        this.myyntiaika = myyntiaika;
        this.kayttajaId = kayttajaId;
        this.lippuIdt = lippuIdt;
    }
}