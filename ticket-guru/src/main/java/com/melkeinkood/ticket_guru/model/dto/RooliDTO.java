package com.melkeinkood.ticket_guru.model.dto;

import com.melkeinkood.ticket_guru.model.Rooli;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RooliDTO {
    private Long rooliId;
    @NotNull(message = "Nimike ei saa olla tyhjä")
    private String nimike;
    private String rooliSelite;

    public RooliDTO(Rooli rooli) {
        this.rooliId = rooli.getRooliId();
        this.nimike = rooli.getNimike();
        this.rooliSelite = rooli.getRooliSelite();

    }

    public RooliDTO(){}
    }
    

