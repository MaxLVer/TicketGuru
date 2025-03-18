package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KayttajaDTO {
    private Long kayttajaId;
    private Long rooliId; 
    private String kayttajanimi;
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
