package com.melkeinkood.ticket_guru.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.melkeinkood.ticket_guru.model.Postinumero;

@Repository
public interface PostinumeroRepository extends JpaRepository<Postinumero, Long>{
    List<Postinumero> findByPostinumero (String postinumero);
    Optional<Postinumero> findByPostinumeroId(Long postinumeroId);

}
