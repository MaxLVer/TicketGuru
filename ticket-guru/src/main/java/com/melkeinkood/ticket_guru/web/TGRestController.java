package com.melkeinkood.ticket_guru.web;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.model.dto.LippuDTO;
import com.melkeinkood.ticket_guru.model.dto.TapahtumaDTO;
import com.melkeinkood.ticket_guru.repositories.*;

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

    @Autowired
    OstostapahtumaRepository ostostapahtumaRepository;

    @Autowired
    KayttajaRepository kayttajaRepository;

    @Autowired
    TapahtumaLipputyyppiRepository tapahtumaLipputyyppiRepository;

    @Autowired
    LippuRepository lippuRepository;

    @GetMapping("/tapahtumat")
    public Iterable<Tapahtuma> haeKaikkiTapahtumat() {
        return tapahtumaRepository.findAll();
    }

    @GetMapping("/tapahtumat/{id}")
    public ResponseEntity<Object> haeTapahtuma(@PathVariable Long id) {
        Tapahtuma tapahtuma = tapahtumaRepository.findById(id).orElse(null);
        return ResponseEntity.ok(tapahtuma != null ? tapahtuma : Collections.emptyMap());
    }

    @PostMapping("/tapahtumat")
    public ResponseEntity<Tapahtuma> lisaaTapahtuma(@RequestBody TapahtumaDTO tapahtumaDTO) {
        Tapahtumapaikka tapahtumapaikka = tapahtumapaikkaRepository
                .findByTapahtumapaikkaId(tapahtumaDTO.getTapahtumapaikkaId());

        Tapahtuma uusiTapahtuma = new Tapahtuma(
                tapahtumapaikka,
                tapahtumaDTO.getTapahtumaAika(),
                tapahtumaDTO.getTapahtumaNimi(),
                tapahtumaDTO.getKuvaus(),
                tapahtumaDTO.getKokonaislippumaara(),
                tapahtumaDTO.getJaljellaOlevaLippumaara());
        Tapahtuma savedTapahtuma = tapahtumaRepository.save(uusiTapahtuma);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTapahtuma);
    }

    @PutMapping("/tapahtumat/{id}")
    public ResponseEntity<Tapahtuma> muokkaaTapahtuma(@RequestBody Tapahtuma tapahtuma, @PathVariable Long id) {
        if (tapahtumaRepository.existsById(id)) {
            tapahtuma.setId(id);
            Tapahtuma savedTapahtuma = tapahtumaRepository.save(tapahtuma);
            return ResponseEntity.status(HttpStatus.OK).body(savedTapahtuma);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Palauttaa koodin 204 jos tapahtuma löytyy ja poistetaan onnistuneesti, tai
    // koodin 404 jos tapahtumaa ei löydy
    @DeleteMapping("/tapahtumat/{id}")
    public ResponseEntity<Void> poistaTapahtuma(@PathVariable("id") Long tapahtumaId) {
        if (tapahtumaRepository.existsById(tapahtumaId)) {
            tapahtumaRepository.deleteById(tapahtumaId);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ostostapahtumat")
    public List<Ostostapahtuma> haeKaikkiOstostapahtumat() {
    return ostostapahtumaRepository.findAll();
    }

    @GetMapping("/ostostapahtumat/{id}")
    public ResponseEntity<Object> haeOstostapahtuma(@PathVariable Long id) {
    Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(id).orElse(null);
    return ostostapahtuma != null ? ResponseEntity.ok(ostostapahtuma) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Ostostapahtumaa ei löytynyt"));
    }


    @PostMapping("/ostostapahtumat")
    public ResponseEntity<Ostostapahtuma> lisaaOstosTapahtuma(@RequestBody Ostostapahtuma ostostapahtuma) {
        Kayttaja kayttaja = kayttajaRepository.findByKayttajaId(ostostapahtuma.getKayttaja().getKayttajaId());
        ostostapahtuma.setKayttaja(kayttaja);
        Ostostapahtuma savedOstostapahtuma = ostostapahtumaRepository.save(ostostapahtuma);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOstostapahtuma);
    }

    @PostMapping("/liput")
    public ResponseEntity<LippuDTO> luoLippu(@RequestBody Lippu uusiLippu) {

        TapahtumaLipputyyppi tapahtumaLipputyyppi = tapahtumaLipputyyppiRepository
                .findById(uusiLippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId())
                .orElseThrow(() -> new IllegalArgumentException("Lipputyyppiä ei löydy"));

        Tapahtuma tapahtuma = tapahtumaRepository
                .findById(uusiLippu.getTapahtuma().getId())
                .orElseThrow(() -> new IllegalArgumentException("Tapahtumaa ei löydy"));

        Ostostapahtuma ostostapahtuma = ostostapahtumaRepository
                .findById(uusiLippu.getOstostapahtuma().getOstostapahtumaId())
                .orElseThrow(() -> new IllegalArgumentException("Ostostapahtumaa ei löydy"));

        uusiLippu.setTapahtumaLipputyyppi(tapahtumaLipputyyppi);
        uusiLippu.setTapahtuma(tapahtuma);
        uusiLippu.setOstostapahtuma(ostostapahtuma);

        Lippu tallennettuLippu = lippuRepository.save(uusiLippu);

        LippuDTO lippuDTO = new LippuDTO(tallennettuLippu);

        return ResponseEntity.status(HttpStatus.CREATED).body(lippuDTO);
    }

    @GetMapping("/liput")
    public List<LippuDTO> haeKaikkiLiput() {

        List<Lippu> liput = lippuRepository.findAll();

        List<LippuDTO> lippuDTOt = liput.stream()
                .map(LippuDTO::new)
                .collect(Collectors.toList());

        return lippuDTOt;
    }

}
