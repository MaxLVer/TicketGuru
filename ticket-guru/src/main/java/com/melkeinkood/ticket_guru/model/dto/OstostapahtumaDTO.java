package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class OstostapahtumaDTO {
    @Getter @Setter private Long ostostapahtumaId;
    @Getter @Setter private LocalDateTime myyntiaika;
    @Getter @Setter private Long kayttajaId;

}