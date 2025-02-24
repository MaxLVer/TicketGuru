package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.Tapahtuma;

@Repository
public interface TapahtumaRepository extends JpaRepository<Tapahtuma, Long>  {
    List<Tapahtuma> findByTapahtuma_id(long tapahtuma_id); 
}
