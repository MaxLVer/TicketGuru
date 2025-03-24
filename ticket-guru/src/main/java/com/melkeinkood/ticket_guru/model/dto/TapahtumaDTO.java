package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class TapahtumaDTO {

    private Long tapahtumaId;
    @NotNull(message = "TapahtumapaikkaId ei saa olla tyhjä")
    private Long tapahtumapaikkaId;
    @NotNull(message = "Tapahtuma aika ei saa olla tyhjä")
    private LocalDateTime tapahtumaAika;
    @NotNull(message = "Tapahtuman nimi ei saa olla tyhjä")
    private String tapahtumaNimi;
    @NotNull(message = "Kuvaus ei saa olla tyhjä")
    private String kuvaus;
    @NotNull(message = "Kokonaislippumäärä ei saa olla tyhjä")
    private int kokonaislippumaara;
    private int jaljellaOlevaLippumaara; 

    public TapahtumaDTO() {}

    public TapahtumaDTO(long tapahtumaId, long tapahtumapaikkaId, LocalDateTime tapahtumaAika, String tapahtumaNimi, String kuvaus, int kokonaislippumaara, int jaljellaOlevaLippumaara) {
        this.tapahtumaId = tapahtumaId;
        this.tapahtumapaikkaId = tapahtumapaikkaId;
        this.tapahtumaAika = tapahtumaAika;
        this.tapahtumaNimi = tapahtumaNimi;
        this.kuvaus = kuvaus;
        this.kokonaislippumaara = kokonaislippumaara;
        this.jaljellaOlevaLippumaara = jaljellaOlevaLippumaara;
    }
}
