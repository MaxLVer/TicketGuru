package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.melkeinkood.ticket_guru.model.Ostostapahtuma;

@Repository
public interface OstostapahtumaRepository extends JpaRepository<Ostostapahtuma, Long> {
    List<Ostostapahtuma> findByOstostapahtumaId (long ostostapahtuma_id);

    
} 
