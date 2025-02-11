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

@Entity
@Table(name = "tapahtumatyyppi")


public class Tapahtumapaikka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paikka_id")
    private long paikka_id;

    @Column(name = "lahiosoite")
    private String lahiosoite;
    
    @Column(name = "kaupunki")
    private String kaupunki;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtumatyyppi")
    @JsonIgnore
    private List<Tapahtuma> tapahtumat;

    public Tapahtumapaikka() {
        super();
    }

    public Tapahtumapaikka(String lahiosoite, String kaupunki) {
        super();
        this.kaupunki = kaupunki;
        this.lahiosoite = lahiosoite;
    }

    public long getId() {
        return paikka_id;
    }

    public void setId(long paikka_id){
        this.paikka_id = paikka_id;
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

    public List<Tapahtuma> getTapahtumat(){
        return tapahtumat;
    }

    public void setTapahtumat(List<Tapahtuma> tapahtumat) {
        this.tapahtumat = tapahtumat;
    }
}
