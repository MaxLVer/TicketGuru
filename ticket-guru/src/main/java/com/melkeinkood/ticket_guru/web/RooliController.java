package com.melkeinkood.ticket_guru.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.melkeinkood.ticket_guru.repositories.RooliRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import com.melkeinkood.ticket_guru.model.Rooli;
import com.melkeinkood.ticket_guru.model.dto.RooliDTO;


@RestController
public class RooliController {

    @Autowired
    private RooliRepository rooliRepo;

    private EntityModel<RooliDTO> toEntityModel(RooliDTO rooliDTO){
        Link selfLink = linkTo(
            methodOn(RooliController.class).haeRooli(rooliDTO.getRooliId()))
            .withSelfRel();
            return EntityModel.of(rooliDTO, selfLink);
    }

    private RooliDTO convertToDTO(Rooli rooli){
        RooliDTO dto = new RooliDTO();
        dto.setRooliId(rooli.getRooliId());
        dto.setNimike(rooli.getNimike());
        dto.setRooliSelite(rooli.getRooliSelite());
        return dto;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roolit")
    public ResponseEntity<List<EntityModel<RooliDTO>>> haeKaikkiRoolit() {
        List<Rooli> roolit = rooliRepo.findAll();
        List<EntityModel<RooliDTO>> rooliModel =roolit.stream()
        .map(rooli -> toEntityModel(convertToDTO(rooli)))
        .collect(Collectors.toList());
        
        if(rooliModel != null){
            return ResponseEntity.ok(rooliModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roolit/{id}")
    public ResponseEntity<Object> haeRooli(@PathVariable Long id) {
        Rooli rooli = rooliRepo.findById(id).orElse(null);
        if(rooli != null){
            RooliDTO rooliDTO = new RooliDTO(rooli);
            return ResponseEntity.ok(toEntityModel(rooliDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonMap("error", "Roolia ei löytynyt"));
        }
    }
    
}
