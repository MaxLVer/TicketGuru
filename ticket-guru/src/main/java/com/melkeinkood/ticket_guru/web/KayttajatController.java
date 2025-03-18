package com.melkeinkood.ticket_guru.web;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.model.Rooli;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.repositories.OstostapahtumaRepository;
import com.melkeinkood.ticket_guru.model.dto.KayttajaDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class KayttajatController {


    @Autowired
    private KayttajaRepository kayttajaRepo;

    @Autowired
    private OstostapahtumaRepository ostostapahtumaRepo;

    @GetMapping("/kayttajat")
    public Iterable<Kayttaja> haeKaikkiKayttajat() {
        return kayttajaRepo.findAll();
    }

    @GetMapping("/kayttajat/{id}")
    public ResponseEntity<Object> haeKayttaja(@PathVariable Long id) {
        Kayttaja kayttaja = kayttajaRepo.findById(id).orElse(null);
        return ResponseEntity.ok(kayttaja != null ? kayttaja : Collections.emptyMap());
    }


    @PostMapping("/kayttajat")
    public ResponseEntity<KayttajaDTO> lisaaKayttaja(@RequestBody KayttajaDTO kayttajaDTO){
        //Toimii kun rooli on lisätty
        Rooli rooli = rooliRepo.findByRooliId(kayttajaDTO.getRooliId());

         Kayttaja uusiKayttaja = new Kayttaja(
            rooli,
            kayttajaDTO.getKayttajanimi(),
            kayttajaDTO.getSalasana(),
            kayttajaDTO.getEtunimi(),
            kayttajaDTO.getSukunimi()
         );

         Kayttaja savedKayttaja = kayttajaRepo.save(uusiKayttaja);

         KayttajaDTO responseDTO = new KayttajaDTO(
        savedKayttaja.getKayttajaId(),
         savedKayttaja.getRooli().getRooliId(),
         savedKayttaja.getKayttajanimi(),
         savedKayttaja.getSalasana(),
         savedKayttaja.getEtunimi(),
         savedKayttaja.getSukunimi()
      );

         return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    } 

    @PutMapping("kayttajat/{id}")
    public ResponseEntity<Kayttaja> muokkaaKayttajaa(@RequestBody Kayttaja kayttaja, @PathVariable Long id) {
        if(kayttajaRepo.existsById(id)) {
            kayttaja.setKayttajaId(id);
            Kayttaja savedKayttaja = kayttajaRepo.save(kayttaja);
            return ResponseEntity.status(HttpStatus.OK).body(savedKayttaja);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @DeleteMapping("/kayttajat/{id}")
    public ResponseEntity<Void> poistaTapahtuma(@PathVariable("id") Long kayttajaId) {
        if (kayttajaRepo.existsById(kayttajaId)) {
            kayttajaRepo.deleteById(kayttajaId);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}
