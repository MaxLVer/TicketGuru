package com.melkeinkood.ticket_guru.model.dto;



import com.melkeinkood.ticket_guru.model.Lippu;

import jakarta.validation.constraints.NotNull;



public class LippuDTO {
    

    private Long lippuId;
    @NotNull(message = "TapahtumaId ei saa olla tyhjä")
    private Long tapahtumaId;
    @NotNull(message = "OstostapahtumaId ei saa olla tyhjä")
    private Long ostostapahtumaId;
    @NotNull(message = "TapahtumaLipputyyppiId ei saa olla tyhjä")
    private Long tapahtumaLipputyyppiId;

    public LippuDTO(Long lippuId, @NotNull(message = "TapahtumaId ei saa olla tyhjä") Long tapahtumaId,
            @NotNull(message = "OstostapahtumaId ei saa olla tyhjä") Long ostostapahtumaId,
            @NotNull(message = "TapahtumaLipputyyppiId ei saa olla tyhjä") Long tapahtumaLipputyyppiId) {
        this.lippuId = lippuId;
        this.tapahtumaId = tapahtumaId;
        this.ostostapahtumaId = ostostapahtumaId;
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
    }
    

    public LippuDTO(){}

   
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
}
