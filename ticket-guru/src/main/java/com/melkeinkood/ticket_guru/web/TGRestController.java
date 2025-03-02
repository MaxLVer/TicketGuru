package com.melkeinkood.ticket_guru.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TGRestController {
    @Autowired
    TapahtumaRepository tapahtumaRepository;

    @PostMapping("/tapahtumat/lisaa")
    public ResponseEntity<Tapahtuma> lisaaTapahtuma(@RequestBody Tapahtuma tapahtuma) {
        Tapahtuma tallennettuTapahtuma = tapahtumaRepository.save(tapahtuma);
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuTapahtuma);
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
