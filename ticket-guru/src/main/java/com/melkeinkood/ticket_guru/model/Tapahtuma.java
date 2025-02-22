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
@Table(name = "tapahtuma")

public class Tapahtuma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tapahtuma_id")
    private long tapahtuma_id;

    @Column(name = "tapahtuma_aika")
    private LocalDateTime tapahtuma_aika;

    @ManyToOne
    @JoinColumn(name = "paikka_id")
    private Tapahtumapaikka tapahtumapaikka;

    @NotNull
    @Size(min=1 , max=100)
    @Column(name = "tapahtuma_nimi")
    private String tapahtuma_nimi;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtuma")
    @JsonIgnore
    private List<Tapahtuma> ostotapahtumat;

    public Tapahtuma() {
        super();
    }

    public Tapahtuma(LocalDateTime tapahtuma_aika, Tapahtumapaikka tapahtumapaikka, String tapahtuma_nimi) {
        super();
        this.tapahtuma_aika = tapahtuma_aika;
        this.tapahtumapaikka = tapahtumapaikka;
        this.tapahtuma_nimi = tapahtuma_nimi;
    }

    public long getId() {
        return tapahtuma_id;
    }

    public void setId(long tapahtuma_id){
        this.tapahtuma_id = tapahtuma_id;
    }

    public LocalDateTime getTapahtuma_aika() {
        return tapahtuma_aika;
    }

    public void setTapahtuma_aika(LocalDateTime tapahtuma_aika){
        this.tapahtuma_aika = tapahtuma_aika;
    }

    public Tapahtumapaikka getTapahtumapaikka() {
        return tapahtumapaikka;
    }

    public void setTapahtumapaikka(Tapahtumapaikka tapahtumapaikka){
        this.tapahtumapaikka = tapahtumapaikka;
    }

    public String getTapahtuma_nimi() {
        return tapahtuma_nimi;
    }

    public void setTapahtuma_nimi(String tapahtuma_nimi){
        this.tapahtuma_nimi = tapahtuma_nimi;
    }
}
