package com.melkeinkood.ticket_guru.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyypit;

@Repository
public interface TapahtumaLipputyypitRepository extends JpaRepository<TapahtumaLipputyypit, Long> {
    List<TapahtumaLipputyypit> findByTapahtumaLipputyyppiId(Long tapahtumalipputyyppiId);
}
