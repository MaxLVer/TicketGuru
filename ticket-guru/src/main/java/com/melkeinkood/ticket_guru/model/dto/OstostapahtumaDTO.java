package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter

public class OstostapahtumaDTO {
    private Long ostostapahtumaId;
    private LocalDateTime myyntiaika;
    private Long kayttajaId;
    private List<Long> liput;
    private BigDecimal summa;

    public OstostapahtumaDTO() {}

    public OstostapahtumaDTO(Long ostostapahtumaId, LocalDateTime myyntiaika, Long kayttajaId, List<Long> liput, BigDecimal summa) {
        this.ostostapahtumaId = ostostapahtumaId;
        this.myyntiaika = myyntiaika;
        this.kayttajaId = kayttajaId;
        this.liput = liput;
        this.summa = summa;
    }
}