package com.melkeinkood.ticket_guru.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tapahtumapaikka")


public class Tapahtumapaikka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tapahtumapaikkaId")
    private long tapahtumapaikkaId;

    @NotNull
    @Size(min=1 , max=100)
    @Column(name = "lahiosoite")
    private String lahiosoite;
    
    @NotNull
    @Size(min=1 , max=50)
    @Column(name = "kaupunki")
    private String kaupunki;

    @NotNull
    @Size(min=1 , max=50)
    @Column(name = "tapahtumapaikan_nimi")
    private String tapahtumapaikan_nimi;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtumapaikka")
    @JsonIgnore
    private List<Tapahtuma> tapahtumat;

    public Tapahtumapaikka() {
        super();
    }

    public Tapahtumapaikka(@NotNull @Size(min = 1, max = 100) String lahiosoite, @NotNull @Size(min = 1, max = 50) String kaupunki, @NotNull @Size(min = 1, max = 50) String tapahtumapaikan_nimi) {
        super();
        this.kaupunki = kaupunki;
        this.lahiosoite = lahiosoite;
        this.tapahtumapaikan_nimi = tapahtumapaikan_nimi;
    }

    public long getId() {
        return tapahtumapaikkaId;
    }

    public void setId(long tapahtumapaikkaId){
        this.tapahtumapaikkaId = tapahtumapaikkaId;
    }

    public String getLahiosoite() {
        return lahiosoite;
    }

    public void setLahiosoite(String lahiosoite){
        this.lahiosoite = lahiosoite;
    }

    public String getKaupunki() {
        return kaupunki;
    }

    public void setKaupunki(String kaupunki){
        this.kaupunki = kaupunki;
    }

    public String getTapahtumapaikan_nimi() {
        return tapahtumapaikan_nimi;
    }

    public void setTapahtumapaikan_nimi(String tapahtumapaikan_nimi){
        this.tapahtumapaikan_nimi = tapahtumapaikan_nimi;
    }

    public List<Tapahtuma> getTapahtumat(){
        return tapahtumat;
    }

    public void setTapahtumat(List<Tapahtuma> tapahtumat) {
        this.tapahtumat = tapahtumat;
    }
}
