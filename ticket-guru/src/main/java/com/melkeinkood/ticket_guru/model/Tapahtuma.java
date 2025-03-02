package com.melkeinkood.ticket_guru.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="tapahtuma")
public class Tapahtuma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tapahtumaId")
    private long tapahtumaId;

    @ManyToOne
    @JoinColumn(name = "tapahtumapaikkaId")
    private Tapahtumapaikka tapahtumapaikka;

    @Column(name = "tapahtuma_aika")
    private LocalDateTime tapahtumaAika;

    @NotNull
    @Size(min=1 , max=50)
    @Column(name = "tapahtuma_nimi")
    private String tapahtumaNimi;

    @Column(name = "kuvaus")
    private String kuvaus;

    @Column(name = "kokonaislippumaara")
    private int kokonaislippumaara;

    @Column(name = "jaljella_oleva_lippumaara")
    private int jaljellaOlevaLippumaara;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtuma")
    @JsonIgnore
    private List<TapahtumaLipputyypit> tapahtumaLipputyypit;

    public Tapahtuma() {
        super();
    }

    public Tapahtuma(Tapahtumapaikka tapahtumapaikka,LocalDateTime tapahtumaAika,  @NotNull @Size(min = 1, max = 50) String tapahtumaNimi, String kuvaus, int kokonaislippumaara, int jaljellaOlevaLippumaara) {
        super();
        this.tapahtumapaikka = tapahtumapaikka;
        this.tapahtumaAika = tapahtumaAika;
        this.tapahtumaNimi = tapahtumaNimi;
        this.kuvaus = kuvaus;
        this.kokonaislippumaara = kokonaislippumaara;
        this.jaljellaOlevaLippumaara = jaljellaOlevaLippumaara;
    }

    public long getId() {
        return tapahtumaId;
    }

    public void setId(long tapahtumaId){
        this.tapahtumaId = tapahtumaId;
    }

    public Tapahtumapaikka getTapahtumapaikka() {
        return tapahtumapaikka;
    }

    public void setTapahtumapaikka(Tapahtumapaikka tapahtumapaikka){
        this.tapahtumapaikka = tapahtumapaikka;
    }

    public LocalDateTime getTapahtumaAika() {
        return tapahtumaAika;
    }

    public void setTapahtumaAika(LocalDateTime tapahtumaAika){
        this.tapahtumaAika = tapahtumaAika;
    }

    public String getTapahtumaNimi() {
        return tapahtumaNimi;
    }

    public void setTapahtumaNimi(String tapahtumaNimi){
        this.tapahtumaNimi = tapahtumaNimi;
    }
    
    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus){
        this.kuvaus = kuvaus;
    }
    
    public int getKokonaislippumaara() {
        return kokonaislippumaara;
    }

    public void setKokonaislippumaara(int kokonaislippumaara){
        this.kokonaislippumaara = kokonaislippumaara;
    }
    
    public int getJaljellaOlevaLippumaara() {
        return jaljellaOlevaLippumaara;
    }

    public void setJaljellaOlevaLippumaara(int jaljellaOlevaLippumaara){
        this.jaljellaOlevaLippumaara = jaljellaOlevaLippumaara;
    }

    public List<TapahtumaLipputyypit> getTapahtumatLipputyypit(){
        return tapahtumaLipputyypit;
    }

    public void setTapahtumaLipputyypit(List<TapahtumaLipputyypit> tapahtumaLipputyypit) {
        this.tapahtumaLipputyypit = tapahtumaLipputyypit;
    }
}
