package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.Asiakastyypit;


@Repository
public interface AsiakastyypitRepository extends JpaRepository <Asiakastyypit, Long>{

    List<Asiakastyypit> findByAsiakastyyppiId(Long AsiakastyyppiId);
} 