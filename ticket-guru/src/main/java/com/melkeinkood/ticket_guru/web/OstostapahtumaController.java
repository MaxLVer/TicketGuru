package com.melkeinkood.ticket_guru.web;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.model.dto.OstostapahtumaDTO;
import com.melkeinkood.ticket_guru.repositories.*;

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

    private EntityModel<OstostapahtumaDTO> toEntityModel(Ostostapahtuma ostostapahtuma) {
        OstostapahtumaDTO ostostapahtumaDTO = new OstostapahtumaDTO(ostostapahtuma.getOstostapahtumaId(),
                ostostapahtuma.getMyyntiaika(), ostostapahtuma.getKayttaja().getKayttajaId());
        EntityModel<OstostapahtumaDTO> entityModel = EntityModel.of(ostostapahtumaDTO);
        Link kayttajaLink = linkTo(
                methodOn(KayttajatController.class).haeKayttaja(ostostapahtuma.getKayttaja().getKayttajaId()))
                .withRel("kayttaja");
        Link selfLink = linkTo(
                methodOn(OstostapahtumaController.class).haeOstostapahtuma(ostostapahtuma.getOstostapahtumaId()))
                .withSelfRel();
        entityModel.add(selfLink);
        entityModel.add(kayttajaLink);
        return entityModel;
    }

    @GetMapping("/ostostapahtumat")
    public ResponseEntity<CollectionModel<EntityModel<OstostapahtumaDTO>>> haeKaikkiOstostapahtumat() {
        List<Ostostapahtuma> ostostapahtumat = ostostapahtumaRepository.findAll();
        List<EntityModel<OstostapahtumaDTO>> ostostapahtumaDTOt = ostostapahtumat.stream().map(this::toEntityModel)
                .collect(Collectors.toList());
        Link kaikkiOstostapahtumatLink = linkTo(methodOn(OstostapahtumaController.class).haeKaikkiOstostapahtumat())
                .withSelfRel();
        if (ostostapahtumaDTOt != null) {
            return ResponseEntity.ok(CollectionModel.of(ostostapahtumaDTOt, kaikkiOstostapahtumatLink));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ostostapahtumat/{id}")
    public ResponseEntity<Object> haeOstostapahtuma(@PathVariable Long id) {
        Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(id).orElse(null);
        if (ostostapahtuma == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Ostostapahtumaa ei löytynyt"));
        } else {
            EntityModel<OstostapahtumaDTO> ostostapahtumaDTO = toEntityModel(ostostapahtuma);
            return ResponseEntity.ok(ostostapahtumaDTO);
        }
    }

    @PostMapping("/ostostapahtumat")
    public ResponseEntity<EntityModel<OstostapahtumaDTO>> lisaaOstostapahtuma(
            @RequestBody OstostapahtumaDTO ostostapahtumaDTO) {
        Kayttaja kayttaja = kayttajaRepository
                .findByKayttajaId(ostostapahtumaDTO.getKayttajaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "KayttajaId:tä " + ostostapahtumaDTO.getKayttajaId() + " ei löydy"));
        Ostostapahtuma uusiOstostapahtuma = new Ostostapahtuma(
                ostostapahtumaDTO.getMyyntiaika(),
                kayttaja);
        ostostapahtumaRepository.save(uusiOstostapahtuma);
        ostostapahtumaDTO.setOstostapahtumaId(uusiOstostapahtuma.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(uusiOstostapahtuma));
    }

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
        EntityModel<OstostapahtumaDTO> savedOstostapahtumaDTO = toEntityModel(savedOstostapahtuma);
        return ResponseEntity.ok(savedOstostapahtumaDTO);
    }

    @PutMapping("/ostostapahtumat/{id}")
    public ResponseEntity<EntityModel<OstostapahtumaDTO>> muokkaaOstostapahtumaa(
            @RequestBody OstostapahtumaDTO ostostapahtumaDTO, @PathVariable Long id) {
        if (ostostapahtumaRepository.existsById(id)) {
            Ostostapahtuma muokattuOstostapahtuma = ostostapahtumaRepository.findById(id).get();
            Kayttaja kayttaja = kayttajaRepository
                    .findByKayttajaId(ostostapahtumaDTO.getKayttajaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "KayttajaId:tä " + ostostapahtumaDTO.getKayttajaId() + " ei löydy"));
            muokattuOstostapahtuma.setKayttaja(kayttaja);
            ostostapahtumaDTO.setOstostapahtumaId(id);
            return ResponseEntity.status(HttpStatus.OK).body(toEntityModel(muokattuOstostapahtuma));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/ostostapahtumat/{id}")
    public ResponseEntity<Void> poistaOstostapahtuma(@PathVariable Long id) {
        if (ostostapahtumaRepository.existsById(id)) {
            List<Lippu> liput = ostostapahtumaRepository.findById(id).get().getLiput();
            System.out.println(liput);
            if (liput.isEmpty()) {
                ostostapahtumaRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
