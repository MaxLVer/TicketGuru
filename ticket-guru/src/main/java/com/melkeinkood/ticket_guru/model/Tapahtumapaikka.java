package com.melkeinkood.ticket_guru.model;

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
@Table(name = "tapahtumapaikka")


public class Tapahtumapaikka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tapahtumapaikkaId")
    private Long tapahtumapaikkaId;

    @NotNull
    @Size(min=1 , max=100)
    @Column(name = "lahiosoite")
    private String lahiosoite;
    
    @ManyToOne
    @JoinColumn(name = "postinumeroId", referencedColumnName = "postinumeroId")
    private Postinumero postinumero;

    @NotNull
    @Size(min=1 , max=50)
    @Column(name = "tapahtumapaikan_nimi")
    private String tapahtumapaikanNimi;

    @Column(name = "kapasiteetti")
    private int kapasiteetti;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtumapaikka")
    @JsonIgnore
    private List<Tapahtuma> tapahtumat;

    public Tapahtumapaikka() {
        super();
    }

    public Tapahtumapaikka(@NotNull @Size(min = 1, max = 100) String lahiosoite, Postinumero postinumero, @NotNull @Size(min = 1, max = 50) String tapahtumapaikanNimi, int kapasiteetti) {
        super();
        this.postinumero = postinumero;
        this.lahiosoite = lahiosoite;
        this.tapahtumapaikanNimi = tapahtumapaikanNimi;
        this.kapasiteetti = kapasiteetti;
    }

    public Long getId() {
        return tapahtumapaikkaId;
    }

    public void setId(Long tapahtumapaikkaId){
        this.tapahtumapaikkaId = tapahtumapaikkaId;
    }

    public String getLahiosoite() {
        return lahiosoite;
    }

    public void setLahiosoite(String lahiosoite){
        this.lahiosoite = lahiosoite;
    }

    public Postinumero getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(Postinumero postinumero){
        this.postinumero = postinumero;
    }

    public String getTapahtumapaikanNimi() {
        return tapahtumapaikanNimi;
    }

    public void setTapahtumapaikanNimi(String tapahtumapaikanNimi){
        this.tapahtumapaikanNimi = tapahtumapaikanNimi;
    }

    public int getKapasiteetti() {
        return kapasiteetti;
    }

    public void setKapasiteetti(int kapasiteetti){
        this.kapasiteetti = kapasiteetti;
    }

    public List<Tapahtuma> getTapahtumat(){
        return tapahtumat;
    }

    public void setTapahtumat(List<Tapahtuma> tapahtumat) {
        this.tapahtumat = tapahtumat;
    }
}
