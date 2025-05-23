package com.melkeinkood.ticket_guru.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tapahtuma_lipputyyppi")
public class TapahtumaLipputyyppi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tapahtumaLipputyyppiId")
    private Long tapahtumaLipputyyppiId; //autogenerated tietokannassa, siksi ei ole konstruktorissa

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tapahtumaId")
    private Tapahtuma tapahtuma;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "asiakastyyppiId")
    private Asiakastyyppi asiakastyyppi;

    @NotNull
    @Column(name = "hinta")
    private BigDecimal hinta;

    public TapahtumaLipputyyppi() {}

    public TapahtumaLipputyyppi(@NotNull Tapahtuma tapahtuma,
            @NotNull Asiakastyyppi asiakastyyppi, @NotNull BigDecimal hinta) {
        this.tapahtuma = tapahtuma;
        this.asiakastyyppi = asiakastyyppi;
        this.hinta = hinta;
    }

    public Long getTapahtumaLipputyyppiId() {
        return tapahtumaLipputyyppiId;
    }

    public void setTapahtumaLipputyyppiId(Long tapahtumaLipputyyppiId) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
    }

    public Tapahtuma getTapahtuma() {
        return tapahtuma;
    }

    public void setTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    public Asiakastyyppi getAsiakastyyppi() {
        return asiakastyyppi;
    }

    public void setAsiakastyyppi(Asiakastyyppi asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }

    public BigDecimal getHinta() {
        return hinta;
    }

    public void setHinta(BigDecimal hinta) {
        this.hinta = hinta;
    }

}
