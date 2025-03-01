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
    private Tapahtuma tapahtumaId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "asiakastyyppi")
    private Asiakastyypit asiakastyyppi;

    @NotNull
    @Column(name = "hinta")
    private BigDecimal hinta;


    public long getTapahtumaLipputyyppiId() {
        return tapahtumaLipputyyppiId;
    }

    public void setTapahtumaLipputyyppiId(long tapahtumaLipputyyppiId) {
        this.tapahtumaLipputyyppiId = tapahtumaLipputyyppiId;
    }

    public Tapahtuma getTapahtumaId() {
        return tapahtumaId;
    }

    public void setTapahtumaId(Tapahtuma tapahtumaId) {
        this.tapahtumaId = tapahtumaId;
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
