package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.Kayttaja;

@Repository
public interface KayttajaRepository extends JpaRepository<Kayttaja, Long> {
    List<Kayttaja> findByKayttajanimi(String kayttajanimi);
    Kayttaja findByKayttajaId(Long kayttajaId);
}
