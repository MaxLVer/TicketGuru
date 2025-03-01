package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.TapahtumaLipputyypit;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyypit;


@Repository
public interface TapahtumaLipputyypitRepository extends JpaRepository<TapahtumaLipputyypit, OstostapahtumaLippuId> {
    /* List<OstostapahtumaLippu> findByOstostapahtumaId(Long ostostapahtumaId);
    
    List<OstostapahtumaLippu> findByLippuId(Long lippuId); */
}
