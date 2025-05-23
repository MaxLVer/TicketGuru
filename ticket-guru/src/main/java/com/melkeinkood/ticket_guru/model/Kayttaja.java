package com.melkeinkood.ticket_guru.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "kayttaja")

public class Kayttaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kayttajaId")
    private Long kayttajaId;
    
    @ManyToOne
    @JoinColumn(name = "rooliId", nullable = false)
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
        super();
    }

    public Kayttaja(String kayttajanimi, String salasana) {
        this.kayttajanimi = kayttajanimi;
        this.salasana = salasana;
    }

    public Kayttaja(Rooli rooli, String kayttajanimi, String salasana, String etunimi, String sukunimi) {
        super();
        this.rooli = rooli;
        this.kayttajanimi = kayttajanimi;
        this.salasana = salasana;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
    }

    public Long getKayttajaId() {
        return kayttajaId;
    }

    public void setKayttajaId(Long kayttajaId) {
        this.kayttajaId = kayttajaId;
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

