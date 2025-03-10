package com.melkeinkood.ticket_guru.repositories;
import com.melkeinkood.ticket_guru.model.Tapahtumapaikka;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TapahtumapaikkaRepository extends JpaRepository<Tapahtumapaikka, Long>  {
    Tapahtumapaikka findByTapahtumapaikkaId(long tapahtumapaikkaId); 
}
