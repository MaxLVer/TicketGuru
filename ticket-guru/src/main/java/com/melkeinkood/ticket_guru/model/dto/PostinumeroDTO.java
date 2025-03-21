package com.melkeinkood.ticket_guru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostinumeroDTO {
    private long postinumeroId;
    private String postinumero;
    private String kaupunki;

    public PostinumeroDTO() {}

    public PostinumeroDTO(long postinumeroId, String postinumero, String kaupunki) {
        this.postinumeroId = postinumeroId;
        this.postinumero = postinumero;
        this.kaupunki = kaupunki;
    }
}
