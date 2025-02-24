package com.melkeinkood.ticket_guru.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "kayttajat")

public class Kayttaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kayttaja_id")
    private long kayttaja_id;
    
    @ManyToOne
    @JoinColumn(name = "rooli_id", nullable = false)
    private Rooli rooli;

    @Column(name = "kayttajanimi", nullable = false, unique = true)
    private String kayttajanimi;

    @Column(name = "salasana", nullable = false)
    @JsonIgnore
    private String salasana;

    @Column(name = "etunimi")
    private String etunimi;

    @Column(name = "sukunimi")
    private String sukunimi;

    public Kayttaja() {
    }

    public Kayttaja(Rooli rooli, String kayttajanimi, String salasana, String etunimi, String sukunimi) {
        this.rooli = rooli;
        this.kayttajanimi = kayttajanimi;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
    }

    public long getKayttaja_id() {
        return kayttaja_id;
    }

    public void setKayttaja_id(long kayttaja_id) {
        this.kayttaja_id = kayttaja_id;
    }

    public Rooli getRooli() {
        return rooli;
    }

    public void setRooli(Rooli rooli) {
        this.rooli = rooli;
    }

    public String getKayttajanimi() {
        return kayttajanimi;
    }

    public void setKayttajanimi(String kayttajanimi) {
        this.kayttajanimi = kayttajanimi;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }
}

