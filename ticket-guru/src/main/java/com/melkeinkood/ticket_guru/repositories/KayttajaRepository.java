package com.melkeinkood.ticket_guru.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.Kayttaja;

@Repository
public interface KayttajaRepository extends JpaRepository<Kayttaja, Long> {
    List<Kayttaja> findByKayttajanimi(String kayttajanimi);
    Optional<Kayttaja> findByKayttajaId(Long kayttajaId);
    Kayttaja findByUsername(String kayttajanimi);
}
