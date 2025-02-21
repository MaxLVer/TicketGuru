package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.OstostapahtumaLippu;
import com.melkeinkood.ticket_guru.model.OstostapahtumaLippuId;


@Repository
public interface OstostapahtumaLippuRepository extends JpaRepository<OstostapahtumaLippu, OstostapahtumaLippuId> {
    List<OstostapahtumaLippu> findByOstostapahtumaId(Long ostostapahtumaId);
    
    List<OstostapahtumaLippu> findByLippuId(Long lippuId);
}
