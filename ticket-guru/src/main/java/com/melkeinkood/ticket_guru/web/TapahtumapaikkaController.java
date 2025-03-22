package com.melkeinkood.ticket_guru.web;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.model.dto.TapahtumapaikkaDTO;
import com.melkeinkood.ticket_guru.repositories.TapahtumapaikkaRepository;
import com.melkeinkood.ticket_guru.repositories.PostinumeroRepository;

@RestController
public class TapahtumapaikkaController {

    @Autowired
    TapahtumapaikkaRepository tapahtumapaikkaRepository;

    @Autowired
    PostinumeroRepository postinumeroRepository;

    @GetMapping("/tapahtumapaikat")
    public Iterable<Tapahtumapaikka> haeKaikkiTapahtumapaikat() {
        return tapahtumapaikkaRepository.findAll();
    }

    @GetMapping("/tapahtumapaikat/{id}")
    public ResponseEntity<Object> getTapahtumapaikka(@PathVariable Long id) {
        Tapahtumapaikka tapahtumapaikka = tapahtumapaikkaRepository.findById(id).orElse(null);
        return ResponseEntity.ok(tapahtumapaikka != null ? tapahtumapaikka : Collections.emptyMap());
    }

    @PostMapping("/tapahtumapaikat")
    public ResponseEntity<Tapahtumapaikka> lisaaTapahtumapaikka(@RequestBody TapahtumapaikkaDTO tapahtumapaikkaDTO) {

        Tapahtumapaikka uusiTapahtumapaikka = new Tapahtumapaikka(
                tapahtumapaikkaDTO.getLahiosoite(),
                null, // Joudun hieman säätämään tämän kanssa...
                tapahtumapaikkaDTO.getTapahtumapaikanNimi(),
                tapahtumapaikkaDTO.getKapasiteetti());
        Tapahtumapaikka savedTapahtumapaikka = tapahtumapaikkaRepository.save(uusiTapahtumapaikka);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTapahtumapaikka);
    }

    @PutMapping("/tapahtumapaikat/{id}")
    public ResponseEntity<Tapahtumapaikka> muokkaaTapahtumapaikka(@RequestBody Tapahtumapaikka tapahtumapaikka,
            @PathVariable Long id) {
        if (tapahtumapaikkaRepository.existsById(id)) {
            tapahtumapaikka.setId(id); 
            Tapahtumapaikka savedTapahtumapaikka = tapahtumapaikkaRepository.save(tapahtumapaikka);
            return ResponseEntity.ok(savedTapahtumapaikka); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    /*
DELETE MAPPING WHENEVER NECESSARY
    */

}
