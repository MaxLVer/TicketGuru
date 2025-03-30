package com.melkeinkood.ticket_guru.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;



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
    @JoinColumn(name = "tapahtumaLipputyyppiId", referencedColumnName = "tapahtumaLipputyyppiId")
    private TapahtumaLipputyyppi tapahtumaLipputyyppi;

    @ManyToOne
    @JoinColumn(name = "tapahtumaId")
    private Tapahtuma tapahtuma;



    

    


    public Lippu(Long lippuId, Ostostapahtuma ostostapahtuma,
            TapahtumaLipputyyppi tapahtumalipputyyppi, Tapahtuma tapahtuma) {
        this.lippuId = lippuId;
        this.ostostapahtuma = ostostapahtuma;
        this.tapahtumaLipputyyppi = tapahtumalipputyyppi;
        this.tapahtuma = tapahtuma;
    }

    public Lippu(Ostostapahtuma ostostapahtuma,
            TapahtumaLipputyyppi tapahtumalipputyyppi, Tapahtuma tapahtuma) {
        this.ostostapahtuma = ostostapahtuma;
        this.tapahtumaLipputyyppi = tapahtumalipputyyppi;
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
        return tapahtumaLipputyyppi;
    }

    public void setTapahtumaLipputyyppi(TapahtumaLipputyyppi tapahtumalipputyyppi) {
        this.tapahtumaLipputyyppi = tapahtumalipputyyppi;
    }

    public Tapahtuma getTapahtuma() {
        return tapahtuma;
    }



    public void setTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    

}
