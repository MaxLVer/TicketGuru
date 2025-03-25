package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class TapahtumapaikkaDTO {
    private Long tapahtumapaikkaId;

    @NotBlank(message = "Lähiosoite ei saa olla tyhjä")
    private String lahiosoite;

    @NotBlank(message = "Tapahtumapaikan nimi ei saa olla tyhjä")
    private String tapahtumapaikanNimi;

    @NotNull(message = "Kapasiteetti ei saa olla tyhjä")
    @Min(value = 0, message = "Kapasiteetti ei voi olla negatiivinen")
    private Integer kapasiteetti;

    @NotNull(message = "Postinumeron ID ei saa olla tyhjä")
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
