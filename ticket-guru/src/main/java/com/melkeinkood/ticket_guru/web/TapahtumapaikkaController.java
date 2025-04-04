package com.melkeinkood.ticket_guru.web;

//import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.model.dto.TapahtumapaikkaDTO;
import com.melkeinkood.ticket_guru.repositories.TapahtumapaikkaRepository;

import jakarta.validation.Valid;

import com.melkeinkood.ticket_guru.repositories.PostinumeroRepository;

@RestController
public class TapahtumapaikkaController {

    @Autowired
    TapahtumapaikkaRepository tapahtumapaikkaRepository;

    @Autowired
    PostinumeroRepository postinumeroRepository;

    private TapahtumapaikkaDTO toDTO(Tapahtumapaikka tapahtumapaikka) {
        return new TapahtumapaikkaDTO(
            tapahtumapaikka.getTapahtumapaikkaId(),
            tapahtumapaikka.getLahiosoite(),
            tapahtumapaikka.getTapahtumapaikanNimi(),
            tapahtumapaikka.getKapasiteetti(),
            tapahtumapaikka.getPostinumero().getPostinumeroId()
        );
    }

    private EntityModel<TapahtumapaikkaDTO> toEntityModel(Tapahtumapaikka tapahtumapaikka) {
        TapahtumapaikkaDTO tapahtumapaikkaDTO = toDTO(tapahtumapaikka);
    
        Link selfLink = linkTo(
                methodOn(TapahtumapaikkaController.class)
                        .getTapahtumapaikka(tapahtumapaikka.getTapahtumapaikkaId()))
                .withSelfRel();
    
        Link postinumeroLink = linkTo(
                methodOn(PostinumeroController.class)
                        .haePostinumero(tapahtumapaikka.getPostinumero().getPostinumeroId()))
                .withRel("postinumero");
    
        return EntityModel.of(tapahtumapaikkaDTO, selfLink, postinumeroLink);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/tapahtumapaikat")
    public ResponseEntity<List<EntityModel<TapahtumapaikkaDTO>>> haeKaikkiTapahtumapaikat() {
        List<Tapahtumapaikka> tapahtumapaikat = (List<Tapahtumapaikka>) tapahtumapaikkaRepository.findAll();
        List<EntityModel<TapahtumapaikkaDTO>> tapahtumapaikkaModel = tapahtumapaikat.stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tapahtumapaikkaModel);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/tapahtumapaikat/{id}")
    public ResponseEntity<EntityModel<TapahtumapaikkaDTO>> getTapahtumapaikka(@PathVariable Long id) {
        return tapahtumapaikkaRepository.findById(id)
                .map(this::toEntityModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/tapahtumapaikat")
    public ResponseEntity<EntityModel<TapahtumapaikkaDTO>> lisaaTapahtumapaikka(@Valid @RequestBody TapahtumapaikkaDTO tapahtumapaikkaDTO) {
        Optional<Postinumero> postinumero = postinumeroRepository.findByPostinumeroId(tapahtumapaikkaDTO.getPostinumeroId());
        if (postinumero.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Tapahtumapaikka uusiTapahtumapaikka = new Tapahtumapaikka(
                tapahtumapaikkaDTO.getLahiosoite(),
                postinumero.get(),
                tapahtumapaikkaDTO.getTapahtumapaikanNimi(),
                tapahtumapaikkaDTO.getKapasiteetti());
        Tapahtumapaikka savedTapahtumapaikka = tapahtumapaikkaRepository.save(uusiTapahtumapaikka);
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(savedTapahtumapaikka));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/tapahtumapaikat/{id}")
    public ResponseEntity<EntityModel<TapahtumapaikkaDTO>> muokkaaTapahtumapaikka(@Valid @RequestBody TapahtumapaikkaDTO tapahtumapaikkaDTO,
                                                                                   @PathVariable Long id) {
        Optional<Tapahtumapaikka> loytynytTapahtumapaikka = tapahtumapaikkaRepository.findById(id);
        if (loytynytTapahtumapaikka.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Postinumero> postinumero = postinumeroRepository.findByPostinumeroId(tapahtumapaikkaDTO.getPostinumeroId());
        if (postinumero.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Tapahtumapaikka tapahtumapaikka = loytynytTapahtumapaikka.get();
        tapahtumapaikka.setLahiosoite(tapahtumapaikkaDTO.getLahiosoite());
        tapahtumapaikka.setTapahtumapaikanNimi(tapahtumapaikkaDTO.getTapahtumapaikanNimi());
        tapahtumapaikka.setKapasiteetti(tapahtumapaikkaDTO.getKapasiteetti());
        tapahtumapaikka.setPostinumero(postinumero.get());

        Tapahtumapaikka paivitettyTapahtumapaikka = tapahtumapaikkaRepository.save(tapahtumapaikka);
        return ResponseEntity.ok(toEntityModel(paivitettyTapahtumapaikka));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/tapahtumapaikat/{id}")
    public ResponseEntity<Void> poistaTapahtumapaikka(@PathVariable Long id) {
        if (!tapahtumapaikkaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tapahtumapaikkaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
