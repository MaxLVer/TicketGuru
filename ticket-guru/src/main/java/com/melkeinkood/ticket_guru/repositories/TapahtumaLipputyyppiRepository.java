package com.melkeinkood.ticket_guru.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;

@Repository
public interface TapahtumaLipputyyppiRepository extends JpaRepository<TapahtumaLipputyyppi, Long> {
    Optional<TapahtumaLipputyyppi> findByTapahtumaLipputyyppiId(Long tapahtumalipputyyppiId);
    List<TapahtumaLipputyyppi> findByTapahtuma_TapahtumaId(Long tapahtumaId);
}
