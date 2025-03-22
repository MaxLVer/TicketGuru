package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TapahtumapaikkaDTO {
    private Long tapahtumapaikkaId;
    private String lahiosoite;
    private String tapahtumapaikanNimi;
    private int kapasiteetti;
    private Long postinumeroId;

    public TapahtumapaikkaDTO() {}

    public TapahtumapaikkaDTO(Long tapahtumapaikkaId, String lahiosoite, String tapahtumapaikanNimi, int kapasiteetti, Long postinumeroId) {
        this.tapahtumapaikkaId = tapahtumapaikkaId;
        this.lahiosoite = lahiosoite;
        this.tapahtumapaikanNimi = tapahtumapaikanNimi;
        this.kapasiteetti = kapasiteetti;
        this.postinumeroId = postinumeroId;
    }

}
