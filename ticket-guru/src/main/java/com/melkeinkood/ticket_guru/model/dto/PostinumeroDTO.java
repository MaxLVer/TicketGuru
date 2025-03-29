package com.melkeinkood.ticket_guru.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostinumeroDTO {
    private long postinumeroId;
    @NotNull(message = "Postinumero ei voi olla tyhjä")
    @Size(min = 5, max = 5, message = "Postinumeron on oltava 5 merkkiä pitkä")
    private String postinumero;
    @NotNull(message = "Kaupunki ei voi olla tyhjä")
    private String kaupunki;

    public PostinumeroDTO() {}

    public PostinumeroDTO(long postinumeroId, String postinumero, String kaupunki) {
        this.postinumeroId = postinumeroId;
        this.postinumero = postinumero;
        this.kaupunki = kaupunki;
    }
}
