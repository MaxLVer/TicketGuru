package com.melkeinkood.ticket_guru.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.melkeinkood.ticket_guru.auth.model.RefreshToken;
import com.melkeinkood.ticket_guru.model.Kayttaja;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByKayttaja(Kayttaja kayttaja);
}

