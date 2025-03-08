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

    @Column(name="myyntiaika")
    private LocalDateTime myyntiaika;

    @ManyToOne
    @JoinColumn(name="kayttajaId")
    private Kayttaja kayttaja;

    public Ostostapahtuma(){
        super();
    } 

    
    public Ostostapahtuma(Kayttaja kayttaja, LocalDateTime myyntiaika) {
        super();
        this.kayttaja = kayttaja;
        this.myyntiaika = myyntiaika;
    }

    public Ostostapahtuma(LocalDateTime myyntiaika) {
        super();
        this.myyntiaika = myyntiaika;
    }

    public long getOstostapahtumaId() {  //Pitäisikö olla näin? Jätin tuohon alas aikaisemman joka on getId. Vai pitäisikö muihinkin tauluihin lisätä muoto getId?
        return ostostapahtumaId;
    }

    public void setOstostapahtumaId(long ostotapahtuma_id){
        this.ostostapahtumaId = ostotapahtuma_id;
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
}

