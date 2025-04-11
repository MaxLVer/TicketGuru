package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.melkeinkood.ticket_guru.model.Lippu;


@Repository
public interface LippuRepository extends JpaRepository<Lippu, Long> {
    List<Lippu> findByLippuId (Long lippuId);
    boolean existsByTapahtumaLipputyyppi_TapahtumaLipputyyppiId(Long tapahtumaLipputyyppiId);
    Optional<Lippu> findByKoodi(String koodi);

}
    
