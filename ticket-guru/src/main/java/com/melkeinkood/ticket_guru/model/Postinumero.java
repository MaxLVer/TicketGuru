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
@Table(name = "postinumero")

public class Postinumero {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="postinumeroId")
    private long postinumeroId;

    @NotNull
    @Column(name = "postinumero")
    private String postinumero;
    
    @NotNull
    @Size(min=1 , max=50)
    @Column(name = "kaupunki")
    private String kaupunki;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postinumero")
    @JsonIgnore
    private List<Tapahtumapaikka> tapahtumapaikat;

    public Postinumero() {
        super();
    }

    public Postinumero(@NotNull String postinumero, @NotNull @Size(min = 1, max = 50) String kaupunki) {
        super();
        this.postinumero = postinumero;
        this.kaupunki = kaupunki;
    }

    public long getPostinumeroID() {
        return postinumeroId;
    }
    
    public String getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(String postinumero){
        this.postinumero = postinumero;
    }

    public String getKaupunki() {
        return kaupunki;
    }

    public void setKaupunki(String kaupunki){
        this.kaupunki = kaupunki;
    }

    public List<Tapahtumapaikka> getTapahtumapaikat(){
        return tapahtumapaikat;
    }

    public void setTapahtumatpaikat(List<Tapahtumapaikka> tapahtumapaikat) {
        this.tapahtumapaikat = tapahtumapaikat;
    }
}
