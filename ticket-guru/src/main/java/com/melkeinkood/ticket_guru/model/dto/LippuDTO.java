package com.melkeinkood.ticket_guru.model.dto;

import jakarta.validation.constraints.NotNull;

public class LippuDTO {

    private Long lippuId;
    @NotNull(message = "TapahtumaId ei saa olla tyhjä")
    private Long tapahtumaId;
    @NotNull(message = "OstostapahtumaId ei saa olla tyhjä")
    private Long ostostapahtumaId;
    @NotNull(message = "TapahtumaLipputyyppiId ei saa olla tyhjä")
    private Long tapahtumaLipputyyppiId;
    @NotNull(message = "Koodi ei saa olla tyhjä")
    private String koodi;
    private String status;


    public LippuDTO(Long lippuId, Long tapahtumaId,
            Long ostostapahtumaId,
            Long tapahtumaLipputyyppiId,
            String koodi, String status) {
        this.lippuId = lippuId;
        this.tapahtumaId = tapahtumaId;
        this.ostostapahtumaId = ostostapahtumaId;
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
        this.koodi = koodi;
        this.status=status;
    }

    public LippuDTO() {
    }

    public Long getLippuId() {
        return lippuId;
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

    public void setTapahtumaId(Long tapahtumaId) {
        this.tapahtumaId = tapahtumaId;
    }

    public void setOstostapahtumaId(Long ostostapahtumaId) {
        this.ostostapahtumaId = ostostapahtumaId;
    }

    public void setTapahtumaLipputyyppiId(Long tapahtumaLipputyyppiId) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
    }

    public String getKoodi() {
        return koodi;
    }
    
    public void setKoodi(String koodi) {
        this.koodi = koodi;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
