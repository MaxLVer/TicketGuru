package com.melkeinkood.ticket_guru.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roolit")
public class Rooli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rooliId")
    private long rooliId;

    @Column(name = "nimike", nullable = false)
    private String nimike;

    @Column(name = "rooliSelite", nullable = false) 
    private String rooliSelite;

    public Rooli() {
    }

    public Rooli(String nimike, String rooliSelite) {
        this.nimike = nimike;
        this.rooliSelite = rooliSelite;
    }

    public long getRooliId() {
        return rooliId;
    }

    public void setRooliId(long rooliId) {
        this.rooliId = rooliId;
    }

    public String getNimike() {
        return nimike;
    }

    public void setNimike(String nimike) {
        this.nimike = nimike;
    }

    public String getRooliSelite() {
        return rooliSelite;
    }

    public void setRooliSelite(String rooliSelite) {
        this.rooliSelite = rooliSelite;
    }
}