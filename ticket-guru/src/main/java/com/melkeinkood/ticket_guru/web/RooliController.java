package com.melkeinkood.ticket_guru.web;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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

    @GetMapping("/roolit")
    public List<RooliDTO> haeKaikkiRoolit() {
        List<Rooli> roolit = rooliRepo.findAll();
        List<RooliDTO> rooliDTO =roolit.stream()
        .map(RooliDTO::new)
        .collect(Collectors.toList());
        return rooliDTO;
    }
    
    @GetMapping("/roolit/{id}")
    public ResponseEntity<RooliDTO> haeRooli(@PathVariable Long id) {
        Rooli rooli = rooliRepo.findById(id).orElse(null);
        if(rooli != null){
            RooliDTO rooliDTO = new RooliDTO(rooli);
            return ResponseEntity.ok(rooliDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
