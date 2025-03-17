package com.melkeinkood.ticket_guru.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.Asiakastyyppi;


@Repository
public interface AsiakastyyppiRepository extends JpaRepository <Asiakastyyppi, Long>{

    Optional<Asiakastyyppi> findByAsiakastyyppiId(Long asiakastyyppiId);
} 