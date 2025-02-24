package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.Tapahtumapaikka;

@Repository
public interface TapahtumapaikkaRepository extends JpaRepository<Tapahtumapaikka, Long>  {
    List<Tapahtumapaikka> findByTapahtumapaikka_id(long tapahtumapaikka_id); 
}
