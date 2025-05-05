package com.melkeinkood.ticket_guru.web;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.model.dto.OstostapahtumaDTO;
import com.melkeinkood.ticket_guru.repositories.*;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class OstostapahtumaController {
    @Autowired
    OstostapahtumaRepository ostostapahtumaRepository;

    @Autowired
    KayttajaRepository kayttajaRepository;

    @Autowired
    LippuRepository lippuRepository;

    // Muuntaa DTO:n EntityModel-muotoon ja liittää siihen HATEOAS-linkit
    private EntityModel<OstostapahtumaDTO> toEntityModel(OstostapahtumaDTO ostostapahtumaDTO) {
        List<Long> lippuIdt = ostostapahtumaDTO.getLippuIdt();
        EntityModel<OstostapahtumaDTO> entityModel = EntityModel.of(ostostapahtumaDTO);
        Link kayttajaLink = linkTo(
                methodOn(KayttajatController.class).haeKayttaja(ostostapahtumaDTO.getKayttajaId()))
                .withRel("kayttaja");
        Link selfLink = linkTo(
                methodOn(OstostapahtumaController.class).haeOstostapahtuma(ostostapahtumaDTO.getOstostapahtumaId()))
                .withSelfRel();
        entityModel.add(selfLink);
        entityModel.add(kayttajaLink);
        if (lippuIdt != null) {

            for (Long lippuId : lippuIdt) {
                Link lipunLink = linkTo(methodOn(LippuController.class).haeLippu(lippuId)).withRel("liput");
                entityModel.add(lipunLink);
            }
        }
        return entityModel;

    }
    // Muuntaa entiteetin DTO:ksi
    private OstostapahtumaDTO toDTO(Ostostapahtuma ostostapahtuma) {
        List<Long> lippuIdt = new ArrayList<>();
        BigDecimal summa = BigDecimal.ZERO;
        if (ostostapahtuma.getLiput() != null) {
            List<Lippu> liput = ostostapahtuma.getLiput();
            for (Lippu lippu : liput) {
                lippuIdt.add(lippu.getLippuId());
                summa = summa.add(lippu.getTapahtumaLipputyyppi().getHinta());
            }
        }
        return new OstostapahtumaDTO(
                ostostapahtuma.getOstostapahtumaId(),
                ostostapahtuma.getMyyntiaika(),
                ostostapahtuma.getKayttaja().getKayttajaId(),
                summa,
                lippuIdt);
    }

    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Hakee kaikki ostostapahtumat
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/ostostapahtumat")
    public ResponseEntity<Object> haeKaikkiOstostapahtumat() {
        List<Ostostapahtuma> ostostapahtumat = ostostapahtumaRepository.findAll();
        List<EntityModel<OstostapahtumaDTO>> ostostapahtumaModel = ostostapahtumat.stream()
                .map(ostostapahtuma -> toEntityModel(toDTO(ostostapahtuma))) // Muunnetaan DTO-muotoon ja lisätään linkit
                .collect(Collectors.toList());
        if (ostostapahtumaModel != null) {
            return ResponseEntity.ok(ostostapahtumaModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Hakee yksittäisen ostostapahtuman ID:n perusteella
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/ostostapahtumat/{id}")
    public ResponseEntity<Object> haeOstostapahtuma(@PathVariable Long id) {
        Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(id).orElse(null);
        if (ostostapahtuma == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Ostostapahtumaa ei löytynyt"));
        } else {
            ostostapahtuma.getLiput();
            return ResponseEntity.ok(toEntityModel(toDTO(ostostapahtuma)));
        }
    }


    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Lisää uuden ostostapahtuman
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @PostMapping("/ostostapahtumat")
    public ResponseEntity<?> lisaaOstostapahtuma(
            @Valid @RequestBody OstostapahtumaDTO ostostapahtumaDTO, BindingResult bindingResult) {
        // Tarkistetaan validointi
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        // Haetaan käyttäjä ja luodaan uusi ostostapahtuma
        Kayttaja kayttaja = kayttajaRepository
                .findByKayttajaId(ostostapahtumaDTO.getKayttajaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "KayttajaId:tä " + ostostapahtumaDTO.getKayttajaId() + " ei löydy"));
        Ostostapahtuma uusiOstostapahtuma = new Ostostapahtuma(
                ostostapahtumaDTO.getMyyntiaika(),
                kayttaja);
        ostostapahtumaRepository.save(uusiOstostapahtuma);
        ostostapahtumaDTO.setOstostapahtumaId(uusiOstostapahtuma.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(ostostapahtumaDTO));
    }
    

    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Päivittää myyntiajan (vain kyseinen kenttä PATCH-muotoisesti)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @PatchMapping("/ostostapahtumat/{id}/myyntiaika")
    public ResponseEntity<EntityModel<OstostapahtumaDTO>> paivitaMyyntiaika(@PathVariable Long id,
            @RequestBody Map<String, LocalDateTime> haeMyyntiaika) {
        Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(id).orElse(null);
        if (ostostapahtuma == null) {
            return ResponseEntity.notFound().build();
        }
        LocalDateTime myyntiaika = haeMyyntiaika.get("myyntiaika");
        ostostapahtuma.setMyyntiaika(myyntiaika);
        Ostostapahtuma savedOstostapahtuma = ostostapahtumaRepository.save(ostostapahtuma);
        EntityModel<OstostapahtumaDTO> savedOstostapahtumaDTO = toEntityModel(toDTO(savedOstostapahtuma));
        return ResponseEntity.ok(savedOstostapahtumaDTO);
    }


    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Muokkaa koko ostostapahtuman (PUT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @PutMapping("/ostostapahtumat/{id}")
    public ResponseEntity<?> muokkaaOstostapahtumaa(
            @Valid @RequestBody OstostapahtumaDTO ostostapahtumaDTO, @PathVariable Long id,
            BindingResult bindingResult) {
        //Tarkistetaan validointi
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Optional<Ostostapahtuma> optionalOstostpahtuma = ostostapahtumaRepository.findById(id);
        if (optionalOstostpahtuma.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Ostostapahtuma ostostapahtuma = optionalOstostpahtuma.get();
        if (ostostapahtuma.getOstostapahtumaId() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("OstostapahtumaId ei voi olla tyhjä")));
        }
        // Jos ostostapahtuma löytyy päivitetään tiedot ja tallennetaan muutokset
        if (ostostapahtumaRepository.existsById(id))

        {
            Ostostapahtuma muokattuOstostapahtuma = ostostapahtumaRepository.findById(id).get();
            Kayttaja kayttaja = kayttajaRepository
                    .findByKayttajaId(ostostapahtumaDTO.getKayttajaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "KayttajaId:tä " + ostostapahtumaDTO.getKayttajaId() + " ei löydy"));
            muokattuOstostapahtuma.setKayttaja(kayttaja);
            ostostapahtumaDTO.setOstostapahtumaId(id);
            return ResponseEntity.status(HttpStatus.OK).body(toEntityModel(toDTO(muokattuOstostapahtuma)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Poistaa ostostapahtuman, jos siihen ei liity lippuja - Sallittu vain ADMIN-roolille
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/ostostapahtumat/{id}")
    public ResponseEntity<Object> poistaOstostapahtuma(@PathVariable Long id) {
        if (ostostapahtumaRepository.existsById(id)) {
            //Tarkastetaan liittyykö ostostapahtumaan lippuja
            List<Lippu> liput = ostostapahtumaRepository.findById(id).get().getLiput();
            if (liput.isEmpty()) {
                ostostapahtumaRepository.deleteById(id);
                return ResponseEntity.ok("Ostostapahtuma " + id + " on poistettu.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: Ostostapahtumaan liittyy lippuja, ostostapahtumaa ei voi poistaa");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: OstostapahtumaId:tä ei löydetty");
        }
    }
}
