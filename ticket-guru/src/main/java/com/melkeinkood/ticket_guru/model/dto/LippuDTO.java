package com.melkeinkood.ticket_guru.model.dto;

import java.time.LocalDateTime;

import com.melkeinkood.ticket_guru.model.Lippu;
import com.melkeinkood.ticket_guru.model.LippuStatus;


public class LippuDTO {
    private Long lippuId;
    private String tunniste;
    private LocalDateTime voimassaoloaika;
    private LippuStatus status;
    private Long tapahtumaId;
    private Long ostostapahtumaId;
    private Long tapahtumaLipputyyppiId;

    
    public LippuDTO(Lippu lippu) {
        this.lippuId = lippu.getLippuId();
        this.tunniste = lippu.getTunniste();
        this.voimassaoloaika = lippu.getVoimassaoloaika();
        this.status = lippu.getStatus();
        this.tapahtumaId = (lippu.getTapahtuma() != null) ? lippu.getTapahtuma().getTapahtumaId() : null;
        this.ostostapahtumaId = (lippu.getOstostapahtuma() != null) ? lippu.getOstostapahtuma().getOstostapahtumaId() : null;
        this.tapahtumaLipputyyppiId = (lippu.getTapahtumaLipputyyppi() != null) ? lippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId() : null;
    }

   
    public Long getLippuId() {
        return lippuId;
    }

    public String getTunniste() {
        return tunniste;
    }

    public LocalDateTime getVoimassaoloaika() {
        return voimassaoloaika;
    }

    public LippuStatus getStatus() {
        return status;
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
}
