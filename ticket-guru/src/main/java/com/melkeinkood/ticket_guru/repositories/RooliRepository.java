package com.melkeinkood.ticket_guru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melkeinkood.ticket_guru.model.Rooli;

@Repository
public interface RooliRepository extends JpaRepository<Rooli, Long> {
    List<Rooli> findByNimike(String nimike);
    Rooli findByRooliId(Long rooliId);
}