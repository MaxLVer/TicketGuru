package com.melkeinkood.ticket_guru.model.dto;

import java.time.LocalDateTime;

import com.melkeinkood.ticket_guru.model.Lippu;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class LippuDTO {
    private Long lippuId;
    @NotEmpty
    @Size(min=1, max=20)
    private String tunniste;
    private LocalDateTime voimassaoloaika;
    private Long tapahtumaId;
    private Long ostostapahtumaId;
    private Long tapahtumaLipputyyppiId;

    
    public LippuDTO(Lippu lippu) {
        this.lippuId = lippu.getLippuId();
        this.tunniste = lippu.getTunniste();
        this.voimassaoloaika = lippu.getVoimassaoloaika();
        this.tapahtumaId = (lippu.getTapahtuma() != null) ? lippu.getTapahtuma().getTapahtumaId() : null;
        this.ostostapahtumaId = (lippu.getOstostapahtuma() != null) ? lippu.getOstostapahtuma().getId() : null;
        this.tapahtumaLipputyyppiId = (lippu.getTapahtumaLipputyyppi() != null) ? lippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId() : null;
    }

    public LippuDTO(){}

   
    public Long getLippuId() {
        return lippuId;
    }

    public String getTunniste() {
        return tunniste;
    }

    public LocalDateTime getVoimassaoloaika() {
        return voimassaoloaika;
    }


    public Long getTapahtumaId() {
        return tapahtumaId;
    }

    public Long getOstostapahtumaId() {
        return ostostapahtumaId;
    }

    public Long getTapahtumaLipputyyppiId() {
        return tapahtumaLipputyyppiId;
    }

    public void setLippuId(Long lippuId) {
        this.lippuId = lippuId;
    }

    public void setTunniste(String tunniste) {
        this.tunniste = tunniste;
    }

    public void setVoimassaoloaika(LocalDateTime voimassaoloaika) {
        this.voimassaoloaika = voimassaoloaika;
    }

    public void setTapahtumaId(Long tapahtumaId) {
        this.tapahtumaId = tapahtumaId;
    }

    public void setOstostapahtumaId(Long ostostapahtumaId) {
        this.ostostapahtumaId = ostostapahtumaId;
    }

    public void setTapahtumaLipputyyppiId(Long tapahtumaLipputyyppiId) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
    }
}
