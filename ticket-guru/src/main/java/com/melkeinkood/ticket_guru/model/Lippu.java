package com.melkeinkood.ticket_guru.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;



@Entity
@Table(name = "lippu")
public class Lippu {

    

    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lippuId")
    private Long lippuId;

    @ManyToOne
    @JoinColumn(name = "ostostapahtumaId")
    private Ostostapahtuma ostostapahtuma;

    @ManyToOne
    @JoinColumn(name = "tapahtumaLipputyyppiId")
    private TapahtumaLipputyyppi tapahtumalipputyyppi;

    @ManyToOne
    @JoinColumn(name = "tapahtumaId")
    private Tapahtuma tapahtuma;



    

    


    public Lippu(Long lippuId, Ostostapahtuma ostostapahtuma,
            TapahtumaLipputyyppi tapahtumalipputyyppi, Tapahtuma tapahtuma) {
        this.lippuId = lippuId;
        this.ostostapahtuma = ostostapahtuma;
        this.tapahtumalipputyyppi = tapahtumalipputyyppi;
        this.tapahtuma = tapahtuma;
    }

    public Lippu(Ostostapahtuma ostostapahtuma,
            TapahtumaLipputyyppi tapahtumalipputyyppi, Tapahtuma tapahtuma) {
        this.ostostapahtuma = ostostapahtuma;
        this.tapahtumalipputyyppi = tapahtumalipputyyppi;
        this.tapahtuma = tapahtuma;
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





    public Ostostapahtuma getOstostapahtuma() {
        return ostostapahtuma;
    }

    public void setOstostapahtuma(Ostostapahtuma ostostapahtuma) {
        this.ostostapahtuma = ostostapahtuma;
    }

    public TapahtumaLipputyyppi getTapahtumaLipputyyppi() {
        return tapahtumalipputyyppi;
    }

    public void setTapahtumaLipputyyppi(TapahtumaLipputyyppi tapahtumalipputyyppi) {
        this.tapahtumalipputyyppi = tapahtumalipputyyppi;
    }

    public Tapahtuma getTapahtuma() {
        return tapahtuma;
    }



    public void setTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    

}
