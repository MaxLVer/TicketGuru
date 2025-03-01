package com.melkeinkood.ticket_guru.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "ostostapahtuma")
public class Ostostapahtuma {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ostostapahtumaId")
    private long ostostapahtumaId;

    @ManyToOne
    @JoinColumn(name="tapahtumaId")
    private Tapahtuma tapahtuma;

    @Column(name="myyntiaika")
    private LocalDateTime myyntiaika;

    @ManyToOne
    @JoinColumn(name="kayttajaId")
    private Kayttaja kayttaja;

    public Ostostapahtuma(){
        super();
    } 

    public Ostostapahtuma(LocalDateTime myyntiaika, Tapahtuma tapahtuma) {
        super();
        this.myyntiaika = myyntiaika;
        this.tapahtuma = tapahtuma;
    }

    public long getId() {
        return ostostapahtumaId;
    }

    public void setId(long ostotapahtuma_id){
        this.ostostapahtumaId = ostotapahtuma_id;
    }

    public LocalDateTime getMyyntiaika() {
        return myyntiaika;
    }

    public void setMyyntiaika(LocalDateTime myyntiaika){
        this.myyntiaika = myyntiaika;
    }

    public Tapahtuma getTapahtuma() {
        return tapahtuma;
    }

    public void setTapahtuma(Tapahtuma tapahtuma){
        this.tapahtuma = tapahtuma;
    }

    
}
