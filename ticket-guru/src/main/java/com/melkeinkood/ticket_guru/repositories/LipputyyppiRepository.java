package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.melkeinkood.ticket_guru.model.Lipputyyppi;

@Repository
public interface LipputyyppiRepository extends JpaRepository <Lipputyyppi, Long>{

    List<Lipputyyppi> findByLipputyyppiId(Long lipputyyppiId);
} 