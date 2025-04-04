package com.melkeinkood.ticket_guru.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import com.melkeinkood.ticket_guru.model.Kayttaja;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "REFRESH_TOKENS")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;

    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "kayttajaId", referencedColumnName = "kayttajaId")
    private Kayttaja kayttaja;

}