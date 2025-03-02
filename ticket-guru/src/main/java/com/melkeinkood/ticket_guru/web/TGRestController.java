package com.melkeinkood.ticket_guru.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.repositories.PostinumeroRepository;
import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;
import com.melkeinkood.ticket_guru.repositories.TapahtumapaikkaRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TGRestController {
    @Autowired
    TapahtumaRepository tapahtumaRepository;

    @Autowired
    TapahtumapaikkaRepository tapahtumapaikkaRepository;

    @Autowired
    PostinumeroRepository postinumeroRepository;

    @GetMapping("/tapahtumat")
    public Iterable<Tapahtuma> haeKaikkiTapahtumat(){
        return tapahtumaRepository.findAll();
    }

    @GetMapping("/tapahtumat/{id}")
    public Optional<Tapahtuma> haeTapahtuma(@PathVariable("tapahtumaId") Long id) {
        return tapahtumaRepository.findById(id);
    }

    @PostMapping("/tapahtumat/lisaa")
    public ResponseEntity<Tapahtuma> lisaaTapahtuma(@RequestBody Tapahtuma tapahtuma) {
        Postinumero postinumero = tapahtuma.getTapahtumapaikka().getPostinumero();
        Postinumero savedPostinumero = postinumeroRepository.save(postinumero); 
        Tapahtumapaikka tapahtumapaikka = tapahtuma.getTapahtumapaikka();
        tapahtumapaikka.setPostinumero(savedPostinumero); 
        Tapahtumapaikka savedTapahtumapaikka = tapahtumapaikkaRepository.save(tapahtumapaikka);
        tapahtuma.setTapahtumapaikka(savedTapahtumapaikka);
        Tapahtuma savedTapahtuma = tapahtumaRepository.save(tapahtuma);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTapahtuma);
    }
    
    @PutMapping("/tapahtumat/{id}")
    Tapahtuma muokkaaTapahtuma(@RequestBody Tapahtuma tapahtuma, @PathVariable Long id) {
        tapahtuma.setId(id);
        return tapahtumaRepository.save(tapahtuma);
    }


    //Palauttaa koodin 204 jos tapahtuma löytyy ja poistetaan onnistuneesti, tai koodin 404 jos tapahtumaa ei löydy
    @DeleteMapping("/tapahtumat/{id}")
    public ResponseEntity<Void> poistaTapahtuma(@PathVariable("id") Long tapahtumaId){
        if (tapahtumaRepository.existsById(tapahtumaId)) {
            tapahtumaRepository.deleteById(tapahtumaId);
            return ResponseEntity.noContent().build();
                    
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    
    
}
