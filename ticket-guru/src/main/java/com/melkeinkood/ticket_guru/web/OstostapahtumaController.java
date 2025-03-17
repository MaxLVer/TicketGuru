package com.melkeinkood.ticket_guru.web;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.model.dto.OstostapahtumaDTO;
import com.melkeinkood.ticket_guru.repositories.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class OstostapahtumaController {
    @Autowired
    OstostapahtumaRepository ostostapahtumaRepository;

    @Autowired
    KayttajaRepository kayttajaRepository;

    @GetMapping("/ostostapahtumat")
    public List<Ostostapahtuma> haeKaikkiOstostapahtumat() {
        return ostostapahtumaRepository.findAll();
    }

    @GetMapping("/ostostapahtumat/{id}")
    public ResponseEntity<Object> haeOstostapahtuma(@PathVariable Long id) {
        Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(id).orElse(null);
        return ostostapahtuma != null ? ResponseEntity.ok(ostostapahtuma)
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Ostostapahtumaa ei löytynyt"));
    }

    @PostMapping("/ostostapahtumat")
    // public ResponseEntity<Ostostapahtuma> lisaaOstostapahtuma(@RequestBody
    // OstostapahtumaDTO ostostapahtumaDTO) {
    public ResponseEntity<OstostapahtumaDTO> lisaaOstostapahtuma(@RequestBody OstostapahtumaDTO ostostapahtumaDTO) {
        Kayttaja kayttaja = kayttajaRepository
                .findByKayttajaId(ostostapahtumaDTO.getKayttajaId());
        Ostostapahtuma uusiOstostapahtuma = new Ostostapahtuma(
                ostostapahtumaDTO.getMyyntiaika(),
                kayttaja);
        ostostapahtumaRepository.save(uusiOstostapahtuma);
        ostostapahtumaDTO.setOstostapahtumaId(uusiOstostapahtuma.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ostostapahtumaDTO);
    }

    @PutMapping("/ostostapahtumat/{id}/myyntiaika")
    public ResponseEntity<Ostostapahtuma> paivitaMyyntiaika(@PathVariable Long id,
            @RequestBody Map<String, LocalDateTime> haeMyyntiaika) {
        Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(id).orElse(null);
        if (ostostapahtuma == null) {
            return ResponseEntity.notFound().build();
        }
        LocalDateTime myyntiaika = haeMyyntiaika.get("myyntiaika");
        ostostapahtuma.setMyyntiaika(myyntiaika);
        Ostostapahtuma savedOstostapahtuma = ostostapahtumaRepository.save(ostostapahtuma);
        return ResponseEntity.ok(savedOstostapahtuma);
    }
}
