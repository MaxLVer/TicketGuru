package com.melkeinkood.ticket_guru.web;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

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
        if (tapahtumapaikka == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tapahtumapaikka);
    }

    @PostMapping("/tapahtumapaikat")
    public ResponseEntity<Tapahtumapaikka> lisaaTapahtumapaikka(@RequestBody TapahtumapaikkaDTO tapahtumapaikkaDTO) {

        Optional<Postinumero> postinumero = postinumeroRepository
                .findByPostinumeroId(tapahtumapaikkaDTO.getPostinumeroId());
        if (postinumero.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Palautta 404, jos postinumeron ID:tä ei ole
        }                                                               // olemassa.
        

        Tapahtumapaikka uusiTapahtumapaikka = new Tapahtumapaikka(
                tapahtumapaikkaDTO.getLahiosoite(),
                postinumero.get(),
                tapahtumapaikkaDTO.getTapahtumapaikanNimi(),
                tapahtumapaikkaDTO.getKapasiteetti());
        Tapahtumapaikka savedTapahtumapaikka = tapahtumapaikkaRepository.save(uusiTapahtumapaikka);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTapahtumapaikka);
    }

    @PutMapping("/tapahtumapaikat/{id}")
    public ResponseEntity<Tapahtumapaikka> muokkaaTapahtumapaikka(@RequestBody TapahtumapaikkaDTO tapahtumapaikkaDTO,
            @PathVariable Long id) {
        Optional<Tapahtumapaikka> loytynytTapahtumapaikka = tapahtumapaikkaRepository.findById(id);
        if (loytynytTapahtumapaikka.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Postinumero> postinumero = postinumeroRepository
                .findByPostinumeroId(tapahtumapaikkaDTO.getPostinumeroId());
        if (postinumero.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Palauttaa 400, jos väärä postinumero
        }
        Tapahtumapaikka tapahtumapaikka = loytynytTapahtumapaikka.get();
        tapahtumapaikka.setLahiosoite(tapahtumapaikkaDTO.getLahiosoite());
        tapahtumapaikka.setTapahtumapaikanNimi(tapahtumapaikkaDTO.getTapahtumapaikanNimi());
        tapahtumapaikka.setKapasiteetti(tapahtumapaikkaDTO.getKapasiteetti());
        tapahtumapaikka.setPostinumero(postinumero.get());

        Tapahtumapaikka paivitettyTapahtumapaikka = tapahtumapaikkaRepository.save(tapahtumapaikka);
        return ResponseEntity.ok(paivitettyTapahtumapaikka);
    }

    @DeleteMapping("/tapahtumapaikat/{id}")
    public ResponseEntity<Void> poistaTapahtumapaikka(@PathVariable Long id) {
        if (!tapahtumapaikkaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tapahtumapaikkaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    //Koodi päivitetään seuraavalla sprintillä.
}
