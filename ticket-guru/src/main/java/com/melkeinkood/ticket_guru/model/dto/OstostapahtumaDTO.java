package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OstostapahtumaDTO {
    private Long ostostapahtumaId;
    private LocalDateTime myyntiaika;
    private Long kayttajaId;
}
