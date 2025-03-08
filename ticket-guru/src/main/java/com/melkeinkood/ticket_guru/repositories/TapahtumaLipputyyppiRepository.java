package com.melkeinkood.ticket_guru.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;

@Repository
public interface TapahtumaLipputyyppiRepository extends JpaRepository<TapahtumaLipputyyppi, Long> {
    List<TapahtumaLipputyyppi> findByTapahtumaLipputyyppiId(Long tapahtumalipputyyppiId);
}
