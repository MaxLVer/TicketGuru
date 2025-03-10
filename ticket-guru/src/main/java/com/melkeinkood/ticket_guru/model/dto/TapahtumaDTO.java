package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TapahtumaDTO {

    private Long tapahtumaId;
    private Long tapahtumapaikkaId;
    private LocalDateTime tapahtumaAika;
    private String tapahtumaNimi;
    private String kuvaus;
    private int kokonaislippumaara;
    private int jaljellaOlevaLippumaara; 
}
