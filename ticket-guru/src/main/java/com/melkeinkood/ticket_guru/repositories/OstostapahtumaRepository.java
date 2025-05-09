package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.melkeinkood.ticket_guru.model.Ostostapahtuma;

@Repository
public interface OstostapahtumaRepository extends JpaRepository<Ostostapahtuma, Long> {
    List<Ostostapahtuma> findByOstostapahtumaId (Long ostostapahtumaId);

    @Query("SELECT DISTINCT o FROM Ostostapahtuma o JOIN o.liput l WHERE l.tapahtuma.tapahtumaId = :tapahtumaId")
List<Ostostapahtuma> findByLiputTapahtumaId(@Param("tapahtumaId") Long tapahtumaId);


    
} 
// teksti