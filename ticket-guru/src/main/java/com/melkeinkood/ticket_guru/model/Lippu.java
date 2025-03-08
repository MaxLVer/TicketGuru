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
    @Column(name = "lippuId")
    private Long lippuId;


    @NotNull
    @Size(min=1, max=20)
    @Column(name = "tunniste")
    private String tunniste;

    @ManyToOne
    @JoinColumn(name = "ostostapahtumaId")
    private Ostostapahtuma ostostapahtuma;

    @ManyToOne
    @JoinColumn(name = "tapahtumaLipputyyppiId")
    private TapahtumaLipputyypit tapahtumalipputyyppi;

    @ManyToOne
    @JoinColumn(name = "tapahtumaId")
    private Tapahtuma tapahtuma;

    

    @Column(name = "voimassaoloaika")
    private LocalDateTime voimassaoloaika;

    @Enumerated(EnumType.STRING)
    private LippuStatus status;

    


    public Lippu(Long lippuId, @NotNull @Size(min = 1, max = 20) String tunniste, Ostostapahtuma ostostapahtuma,
            TapahtumaLipputyypit tapahtumalipputyyppi, LocalDateTime voimassaoloaika, LippuStatus status, Tapahtuma tapahtuma) {
        this.lippuId = lippuId;
        this.tunniste = tunniste;
        this.ostostapahtuma = ostostapahtuma;
        this.tapahtumalipputyyppi = tapahtumalipputyyppi;
        this.voimassaoloaika = voimassaoloaika;
        this.status = status;
        this.tapahtuma = tapahtuma;
    }

    public Lippu(@NotNull @Size(min = 1, max = 20) String tunniste, Ostostapahtuma ostostapahtuma,
            TapahtumaLipputyypit tapahtumalipputyyppi, LocalDateTime voimassaoloaika, LippuStatus status, Tapahtuma tapahtuma) {
        this.tunniste = tunniste;
        this.ostostapahtuma = ostostapahtuma;
        this.tapahtumalipputyyppi = tapahtumalipputyyppi;
        this.voimassaoloaika = voimassaoloaika;
        this.status = status;
        this.tapahtuma = tapahtuma;
    }



    public Lippu(@NotNull @Size(min = 1, max = 20) String tunniste, LocalDateTime voimassaoloaika, LippuStatus status) {
        this.tunniste = tunniste;
        this.voimassaoloaika = voimassaoloaika;
        this.status = status;
    }





    public Lippu(Long lippuId, @NotNull @Size(min = 1, max = 20) String tunniste, LocalDateTime voimassaoloaika,
            LippuStatus status) {
        this.lippuId = lippuId;
        this.tunniste = tunniste;
        this.voimassaoloaika = voimassaoloaika;
        this.status = status;
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

    public Ostostapahtuma getOstostapahtuma() {
        return ostostapahtuma;
    }

    public void setOstostapahtuma(Ostostapahtuma ostostapahtuma) {
        this.ostostapahtuma = ostostapahtuma;
    }

    public TapahtumaLipputyypit getTapahtumalipputyyppi() {
        return tapahtumalipputyyppi;
    }

    public void setTapahtumalipputyyppi(TapahtumaLipputyypit tapahtumalipputyyppi) {
        this.tapahtumalipputyyppi = tapahtumalipputyyppi;
    }

    public Tapahtuma getTapahtuma() {
        return tapahtuma;
    }



    public void setTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    

}
