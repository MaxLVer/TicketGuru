package com.melkeinkood.ticket_guru.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.melkeinkood.ticket_guru.model.Lippu;
import com.melkeinkood.ticket_guru.model.LippuStatus;
import com.melkeinkood.ticket_guru.model.Ostostapahtuma;
import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;
import com.melkeinkood.ticket_guru.model.dto.LippuDTO;
import com.melkeinkood.ticket_guru.model.dto.LuoLippuDTO;
import com.melkeinkood.ticket_guru.repositories.*;

import jakarta.validation.Valid;

@RestController
public class LippuController {
    @Autowired
    TapahtumaRepository tapahtumaRepository;

    @Autowired
    OstostapahtumaRepository ostostapahtumaRepository;

    @Autowired
    LippuRepository lippuRepository;

    @Autowired
    TapahtumaLipputyyppiRepository tapahtumaLipputyyppiRepository;

    // Muuntaa LippuDTO:n EntityModel-muotoon, lisäten HATEOAS-linkit
    private EntityModel<LippuDTO> toEntityModel(LippuDTO lippuDTO) {

        Link selfLink = linkTo(methodOn(LippuController.class).haeLippu(lippuDTO.getLippuId())).withSelfRel();
        Link tapahtumaLink = linkTo(methodOn(TapahtumaController.class).haeTapahtuma(lippuDTO.getTapahtumaId()))
                .withRel("tapahtuma");
        Link tapahtumaLipputyyppiLink = linkTo(methodOn(TapahtumaLipputyyppiController.class)
                .haeTapahtumaLipputyyppi(lippuDTO.getTapahtumaLipputyyppiId())).withRel("tapahtumalipputyyppi");
        Link ostostapahtumaLink = linkTo(
                methodOn(OstostapahtumaController.class).haeOstostapahtuma(lippuDTO.getOstostapahtumaId()))
                .withRel("ostostapahtuma");

        return EntityModel.of(lippuDTO, selfLink, tapahtumaLink, tapahtumaLipputyyppiLink, ostostapahtumaLink);
    }

    // Muuntaa Lippu-entiteetin DTO:ksi
    private LippuDTO convertToDTO(Lippu lippu) {
        LippuDTO dto = new LippuDTO();
        dto.setLippuId(lippu.getLippuId());
        dto.setOstostapahtumaId(lippu.getOstostapahtuma().getOstostapahtumaId());
        dto.setTapahtumaId(lippu.getTapahtuma().getTapahtumaId());
        dto.setTapahtumaLipputyyppiId(lippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId());
        dto.setKoodi(lippu.getKoodi());
        dto.setStatus(lippu.getStatus());
        return dto;

    }

    // Generoi satunnainen merkkijono: 8 merkkiä, kirjaimia ja numeroita
    public String generoiSatunnainenLippuKoodi() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Hakee lipun ID:n perusteella
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/liput/{id}")
    public ResponseEntity<Object> haeLippu(@PathVariable Long id) {
        Lippu lippu = lippuRepository.findById(id).orElse(null);
        if (lippu != null) {
            LippuDTO lippuDTO = convertToDTO(lippu);
            return ResponseEntity.ok(toEntityModel(lippuDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lippua ei löydy id:llä " + id);
        }
    }

    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Hakee kaikki liput tietokannasta
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/liput")
    public ResponseEntity<List<EntityModel<LippuDTO>>> haeKaikkiLiput() {
        List<Lippu> liput = lippuRepository.findAll();
        List<EntityModel<LippuDTO>> liputModel = liput.stream()
                .map(lippu -> toEntityModel(convertToDTO(lippu))) // Muunnetaan DTO-muotoon ja lisätään linkit
                .collect(Collectors.toList());
        if (liputModel != null) {
            return ResponseEntity.ok(liputModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Hakee lippukoodilla
    @CrossOrigin(origins = "http://localhost:3000") // Tai muu frontin osoite
    @GetMapping("/liput/koodi/{koodi}")
    public ResponseEntity<?> haeLippuKoodilla(@PathVariable("koodi") String koodi) {
        Optional<Lippu> optionalLippu = lippuRepository.findByKoodi(koodi);
        if (optionalLippu.isPresent()) {
            Lippu lippu = optionalLippu.get();
            LippuDTO dto = convertToDTO(lippu);
            return ResponseEntity.ok(toEntityModel(dto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Lippua ei löydy annetulla koodilla: " + koodi));
        }
    }

    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Luo uuden lipun
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @PostMapping("/liput")
    public ResponseEntity<?> luoLippu(@Valid @RequestBody LuoLippuDTO lippuDTO, BindingResult bindingResult) {
        // Tarkistaa validointivirheet
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Tapahtuma tapahtuma = tapahtumaRepository.findByTapahtumaId(lippuDTO.getTapahtumaId());
        if (tapahtuma == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("Tapahtumaa ei löydy id:llä " + lippuDTO.getTapahtumaId())));
        }

        // Tarkistaa löytyykö tapahtuma, lipputyyppi ja ostostapahtuma annetulla ID:llä
        Optional<TapahtumaLipputyyppi> tapahtumaLipputyyppiOptional = tapahtumaLipputyyppiRepository
                .findById(lippuDTO.getTapahtumaLipputyyppiId());
        if (tapahtumaLipputyyppiOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error", ("Tapahtumalipputyyppiä ei löydy id:llä " + lippuDTO.getTapahtumaLipputyyppiId())));
        }
        TapahtumaLipputyyppi tapahtumaLipputyyppi = tapahtumaLipputyyppiOptional.get();

        Optional<Ostostapahtuma> ostostapahtumaOptional = ostostapahtumaRepository
                .findById(lippuDTO.getOstostapahtumaId());
        if (ostostapahtumaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ("Ostostapahtumaa ei löydy id:llä " + lippuDTO.getOstostapahtumaId())));
        }

        Ostostapahtuma ostostapahtuma = ostostapahtumaOptional.get();
        String koodi = generoiSatunnainenLippuKoodi();
        // Luo ja tallentaa uuden lipun tietokantaan
        Lippu uusiLippu = new Lippu(
                ostostapahtuma,
                tapahtumaLipputyyppi,
                tapahtuma);
        uusiLippu.setKoodi(koodi);
        Lippu tallenttuLippu = lippuRepository.save(uusiLippu);

        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(convertToDTO(tallenttuLippu)));
    }

    // Poistaa lipun ID:n perusteella – sallittu vain ADMIN-roolille
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/liput/{id}")
    public ResponseEntity<?> poistaLippu(@PathVariable("id") Long lippuId) {
        if (lippuRepository.existsById(lippuId)) {
            lippuRepository.deleteById(lippuId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lippua ei löydy id:llä " + lippuId);
        }
    }

    // Päivittää olemassa olevan lipun tietoja – sallittu vain ADMIN-roolille
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/liput/{id}")
    public ResponseEntity<?> muokkaaLippua(@Valid @RequestBody LippuDTO lippuDTO, BindingResult bindingResult,
            @PathVariable("id") Long lippuId) {
        // Tarkistetaan validointi
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        // Tarkistetaan että lippu löytyy
        Lippu olemassaOlevaLippu = lippuRepository.findById(lippuId).orElse(null);
        if (olemassaOlevaLippu == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lippua ei löydy id:llä " + lippuId);
        }
        // Tarkistetaan liittyvät entiteetit
        Tapahtuma tapahtuma = tapahtumaRepository.findByTapahtumaId(lippuDTO.getTapahtumaId());
        if (tapahtuma == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("Tapahtumaa ei löydy ID:llä " + lippuDTO.getTapahtumaId())));
        }

        Optional<TapahtumaLipputyyppi> tapahtumaLipputyyppiOptional = tapahtumaLipputyyppiRepository
                .findById(lippuDTO.getTapahtumaLipputyyppiId());
        if (tapahtumaLipputyyppiOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error", ("Tapahtumalipputyyppiä ei löydy ID:llä " + lippuDTO.getTapahtumaLipputyyppiId())));
        }
        TapahtumaLipputyyppi tapahtumaLipputyyppi = tapahtumaLipputyyppiOptional.get();

        Optional<Ostostapahtuma> ostostapahtumaOptional = ostostapahtumaRepository
                .findById(lippuDTO.getOstostapahtumaId());
        if (ostostapahtumaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ("Ostostapahtumaa ei löydy ID:llä: " + lippuDTO.getOstostapahtumaId())));
        }
        Ostostapahtuma ostostapahtuma = ostostapahtumaOptional.get();
        // Päivitetään olemassa olevan lipun kentät
        olemassaOlevaLippu.setTapahtuma(tapahtuma);
        olemassaOlevaLippu.setTapahtumaLipputyyppi(tapahtumaLipputyyppi);
        olemassaOlevaLippu.setOstostapahtuma(ostostapahtuma);
        // Tallennetaan muutokset
        Lippu muokattuLippu = lippuRepository.save(olemassaOlevaLippu);

        return ResponseEntity.ok(toEntityModel(convertToDTO(muokattuLippu)));

    }

    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Päivittää lipun statuksen (vain kyseinen kenttä PATCH-muotoisesti)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @PatchMapping("/liput/{id}")
    public ResponseEntity<EntityModel<LippuDTO>> paivitaStatus(@PathVariable Long id) {
        Lippu lippu = lippuRepository.findById(id).orElse(null);
        if (lippu == null) {
            return ResponseEntity.notFound().build();
        }
        lippu.setStatus(LippuStatus.KÄYTETTY);
        Lippu savedLippu = lippuRepository.save(lippu);
        EntityModel<LippuDTO> savedLippuDTO = toEntityModel(convertToDTO(savedLippu));
        return ResponseEntity.ok(savedLippuDTO);
    }

}
