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


import java.time.LocalDateTime;

@Entity
@Table(name = "lippu")
public class Lippu {

    

    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lippu_id")
    private Long lippu_id;

    @NotNull
    @Column(name = "lippuhinta")
    private double lippuHinta;

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

    public Lippu(Long lippu_id, @NotNull double lippuHinta, @NotNull @Size(min = 1, max = 20) String tunniste,
            LocalDateTime voimassaoloaika, LippuStatus status, Lipputyyppi lipputyyppi) {
        this.lippu_id = lippu_id;
        this.lippuHinta = lippuHinta;
        this.tunniste = tunniste;
        this.voimassaoloaika = voimassaoloaika;
        this.status = status;
        this.lipputyyppi = lipputyyppi;
    }

    public Lippu(@NotNull double lippuHinta, @NotNull @Size(min = 1, max = 20) String tunniste,
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

    public Long getLippu_id() {
        return lippu_id;
    }

    public void setLippu_id(Long lippu_id) {
        this.lippu_id = lippu_id;
    }

    public double getLippuHinta() {
        return lippuHinta;
    }

    public void setLippuHinta(double lippuHinta) {
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
