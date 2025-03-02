package com.melkeinkood.ticket_guru.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tapahtuma_lipputyypit")
public class TapahtumaLipputyypit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tapahtumaLipputyyppiId")
    private long tapahtumaLipputyyppiId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tapahtumaId")
    private Tapahtuma tapahtuma;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "asiakastyyppiId")
    private Asiakastyypit asiakastyyppi;

    @NotNull
    @Column(name = "hinta")
    private BigDecimal hinta;

    public TapahtumaLipputyypit(long tapahtumaLipputyyppiId, @NotNull Tapahtuma tapahtuma,
            @NotNull Asiakastyypit asiakastyyppi, @NotNull BigDecimal hinta) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
        this.tapahtuma = tapahtuma;
        this.asiakastyyppi = asiakastyyppi;
        this.hinta = hinta;
    }

    public long getTapahtumaLipputyyppiId() {
        return tapahtumaLipputyyppiId;
    }

    public void setTapahtumaLipputyyppiId(long tapahtumaLipputyyppiId) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
    }

    public Tapahtuma gettapahtuma() {
        return tapahtuma;
    }

    public void settapahtuma(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    public Asiakastyypit getAsiakastyyppi() {
        return asiakastyyppi;
    }

    public void setAsiakastyyppi(Asiakastyypit asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }

    public BigDecimal getHinta() {
        return hinta;
    }

    public void setHinta(BigDecimal hinta) {
        this.hinta = hinta;
    }

}
