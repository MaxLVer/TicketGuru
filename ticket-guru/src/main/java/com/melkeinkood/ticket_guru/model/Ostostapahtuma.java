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

@Entity
@Table(name = "ostostapahtuma")
public class Ostostapahtuma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ostostapahtumaId")
    private Long ostostapahtumaId;

    @Column(name = "myyntiaika")
    private LocalDateTime myyntiaika;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "kayttajaId")
    private Kayttaja kayttaja;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ostostapahtuma")
    @JsonIgnore
    private List<Lippu> liput;

    public Ostostapahtuma() {
        super();
    }

    public Ostostapahtuma(LocalDateTime myyntiaika, Kayttaja kayttaja) {
        super();
        this.myyntiaika = myyntiaika;
        this.kayttaja = kayttaja;
    }

    public Ostostapahtuma(LocalDateTime myyntiaika) {
        super();
        this.myyntiaika = myyntiaika;
    }

    public Long getOstostapahtumaId() { // Pitäisikö olla näin? Jätin tuohon alas aikaisemman joka on getId. Vai
                                        // pitäisikö muihinkin tauluihin lisätä muoto getId?
        return ostostapahtumaId;
    }

    public void setOstostapahtumaId(Long ostotapahtuma_id) {
        this.ostostapahtumaId = ostotapahtuma_id;
    }

    public Long getId() {
        return ostostapahtumaId;
    }

    public void setId(Long ostotapahtuma_id) {
        this.ostostapahtumaId = ostotapahtuma_id;
    }

    public Kayttaja getKayttaja() {
        return kayttaja;
    }

    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
    }

    public LocalDateTime getMyyntiaika() {
        return myyntiaika;
    }

    public void setMyyntiaika(LocalDateTime myyntiaika) {
        this.myyntiaika = myyntiaika;
    }

    public List<Lippu> getLiput() {
        return liput;
    }
}
