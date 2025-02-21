package com.melkeinkood.ticket_guru.model;

import java.math.BigDecimal;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ostostapahtumalippu")
public class OstostapahtumaLippu {

    @EmbeddedId
    private OstostapahtumaLippuId id;

    @NotNull
    @Column(name = "ostostapahtuma_lippu_hinta")
    private BigDecimal hinta;

    @NotNull
    @ManyToOne
    @MapsId("ostostapahtumaId")
    @JoinColumn(name = "ostostapahtuma_id")
    // Toimii kun ostostapahtuma entity lisätään
    private Ostostapahtuma ostostapahtuma;

    @NotNull
    @ManyToOne
    @MapsId("lippuId")
    @JoinColumn(name = "lippu_id")
    private Lippu lippu;

    public OstostapahtumaLippuId getId() {
        return id;
    }

    public void setId(OstostapahtumaLippuId id) {
        this.id = id;
    }

    public BigDecimal getHinta() {
        return hinta;
    }

    public void setHinta(BigDecimal hinta) {
        this.hinta = hinta;
    }

    public Ostostapahtuma getOstostapahtuma() {
        return ostostapahtuma;
    }

    public void setOstostapahtuma(Ostostapahtuma ostostapahtuma) {
        this.ostostapahtuma = ostostapahtuma;
    }

    public Lippu getLippu() {
        return lippu;
    }

    public void setLippu(Lippu lippu) {
        this.lippu = lippu;
    }

}
