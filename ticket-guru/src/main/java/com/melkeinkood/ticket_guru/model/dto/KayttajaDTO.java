package com.melkeinkood.ticket_guru.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KayttajaDTO {
    private Long kayttajaId;
    
    @NotNull(message = "RooliId ei saa olla tyhjä")
    private Long rooliId; 
    @NotNull(message = "Kayttajanimi ei saa olla tyhjä")
    @Size(min = 5, max = 10, message = "Kayttajanimen pituuden tulee olla 5-10 merkkiä")
    private String kayttajanimi;

    //Salasana piilotettu responsesta
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Salasana ei saa olla tyhjä")
    @Size(min = 10, max = 15, message = "Salasanan pituuden tulee olla 10-15 merkkiä")
    private String salasana;
    private String etunimi;
    private String sukunimi;

    public KayttajaDTO() {}

    public KayttajaDTO(Long kayttajaId, Long rooliId, String kayttajanimi, String salasana, String etunimi, String sukunimi){
        this.kayttajaId = kayttajaId;
        this.rooliId = rooliId;
        this.kayttajanimi = kayttajanimi;
        this.salasana = salasana;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
    }
}
