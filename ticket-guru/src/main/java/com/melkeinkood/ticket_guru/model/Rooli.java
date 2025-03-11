package com.melkeinkood.ticket_guru.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roolit")
public class Rooli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rooliId")
    private Long rooliId;

    @Column(name = "nimike", nullable = false)
    private String nimike;

    @Column(name = "rooli_selite", nullable = false) 
    private String rooli_selite;    //Pitäisikö tämän kentän olla muodossa rooliSelite? Vai riittäisikö jopa pelkkä selite?

    public Rooli() {
    }

    public Rooli(String nimike, String rooliSelite) {
        this.nimike = nimike;
        this.rooli_selite = rooliSelite;
    }

    public Long getRooliId() {
        return rooliId;
    }

    public void setRooliId(Long rooliId) {
        this.rooliId = rooliId;
    }

    public String getNimike() {
        return nimike;
    }

    public void setNimike(String nimike) {
        this.nimike = nimike;
    }

    public String getRooli_selite() {
        return rooli_selite;
    }

    public void setRooli_selite(String rooli_selite) {
        this.rooli_selite = rooli_selite;
    }
}