package com.melkeinkood.ticket_guru.model;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lippu")
public class Lippu {

    

    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lippuId")
    private Long lippuId;

    @NotNull
    @Column(name = "lippuhinta")
    private BigDecimal lippuHinta;

    @NotNull
    @Size(min=1, max=20)
    @Column(name = "tunniste")
    private String tunniste;

    @Column(name = "voimassaoloaika")
    private LocalDateTime voimassaoloaika;

    @Enumerated(EnumType.STRING)
    private LippuStatus status;

    @ManyToOne
    @JoinColumn(name = "lipputyyppiid")
    private Lipputyyppi lipputyyppi;

    public Lippu(Long lippuId, @NotNull BigDecimal lippuHinta, @NotNull @Size(min = 1, max = 20) String tunniste,
            LocalDateTime voimassaoloaika, LippuStatus status, Lipputyyppi lipputyyppi) {
        this.lippuId = lippuId;
        this.lippuHinta = lippuHinta;
        this.tunniste = tunniste;
        this.voimassaoloaika = voimassaoloaika;
        this.status = status;
        this.lipputyyppi = lipputyyppi;
    }

    public Lippu(@NotNull BigDecimal lippuHinta, @NotNull @Size(min = 1, max = 20) String tunniste,
            LocalDateTime voimassaoloaika, LippuStatus status, Lipputyyppi lipputyyppi) {
        this.lippuHinta = lippuHinta;
        this.tunniste = tunniste;
        this.voimassaoloaika = voimassaoloaika;
        this.status = status;
        this.lipputyyppi = lipputyyppi;
    }

    public Lippu() {
        super();
    }

    public Long getLippuId() {
        return lippuId;
    }

    public void setLippuId(Long lippuId) {
        this.lippuId = lippuId;
    }

    public BigDecimal getLippuHinta() {
        return lippuHinta;
    }

    public void setLippuHinta(BigDecimal lippuHinta) {
        this.lippuHinta = lippuHinta;
    }

    public String getTunniste() {
        return tunniste;
    }

    public void setTunniste(String tunniste) {
        this.tunniste = tunniste;
    }

    public LocalDateTime getVoimassaoloaika() {
        return voimassaoloaika;
    }

    public void setVoimassaoloaika(LocalDateTime voimassaoloaika) {
        this.voimassaoloaika = voimassaoloaika;
    }

    public LippuStatus getStatus() {
        return status;
    }

    public void setStatus(LippuStatus status) {
        this.status = status;
    }

    public Lipputyyppi getLipputyyppi() {
        return lipputyyppi;
    }

    public void setLipputyyppi(Lipputyyppi lipputyyppi) {
        this.lipputyyppi = lipputyyppi;
    }

    

}
