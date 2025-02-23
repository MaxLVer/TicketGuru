package com.melkeinkood.ticket_guru.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roolit")
public class Rooli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rooli_id")
    private long rooli_id;

    @Column(name = "rooli_selite", nullable = false)
    private String rooli_selite;

    public Rooli() {
    }

    public Rooli(String rooliSelite) {
        this.rooli_selite = rooliSelite;
    }

    public long getRooli_id() {
        return rooli_id;
    }

    public void setRooli_id(long rooli_id) {
        this.rooli_id = rooli_id;
    }

    public String getRooli_selite() {
        return rooli_selite;
    }

    public void setRooli_selite(String rooli_selite) {
        this.rooli_selite = rooli_selite;
    }
}
