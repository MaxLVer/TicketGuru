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

    @Autowired
    private TapahtumaRepository tapahtumaRepo;

    @Autowired
    private TapahtumaLipputyyppiRepository lipputyyppiRepo;

    // Muuntaa DTO:n EntityModel-muotoon ja liittää siihen HATEOAS-linkit
    private EntityModel<OstostapahtumaDTO> toEntityModel(OstostapahtumaDTO ostostapahtumaDTO) {
        List<Long> lippuIdt = ostostapahtumaDTO.getLiput();
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

        Set<Long> tapahtumaIdt = new HashSet<>();

        if (ostostapahtuma.getLiput() != null) {
        for (Lippu lippu : ostostapahtuma.getLiput()) {
            lippuIdt.add(lippu.getLippuId());
            summa = summa.add(lippu.getTapahtumaLipputyyppi().getHinta());
            tapahtumaIdt.add(lippu.getTapahtumaLipputyyppi().getTapahtuma().getTapahtumaId());
        }
    }
    OstostapahtumaDTO dto = new OstostapahtumaDTO(
            ostostapahtuma.getOstostapahtumaId(),
            ostostapahtuma.getMyyntiaika(),
            ostostapahtuma.getKayttaja().getKayttajaId(),
            lippuIdt,
            summa);

            dto.setTapahtumaIdt(tapahtumaIdt);

            return dto;
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
@GetMapping("/ostostapahtumat/laajennettu/{id}")
public ResponseEntity<Object> haeOstostapahtuma(@PathVariable Long id) {
    Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ostostapahtumaa ei löytynyt"));

    Set<String> tapahtumaNimet = new HashSet<>();
    Set<String> lipputyyppienNimet = new HashSet<>();
    BigDecimal summa = BigDecimal.ZERO;
    List<Long> lippuIdt = new ArrayList<>();

    if (ostostapahtuma.getLiput() != null) {
        for (Lippu lippu : ostostapahtuma.getLiput()) {
            TapahtumaLipputyyppi tlt = lippu.getTapahtumaLipputyyppi();
            Tapahtuma tapahtuma = tlt.getTapahtuma();
            Asiakastyyppi asiakastyyppi = tlt.getAsiakastyyppi();
        
            tapahtumaNimet.add(tapahtuma != null ? tapahtuma.getTapahtumaNimi() : "Tuntematon");
            lipputyyppienNimet.add(asiakastyyppi != null ? asiakastyyppi.getAsiakastyyppi() : "Tuntematon");
        
            summa = summa.add(tlt.getHinta());
            lippuIdt.add(lippu.getLippuId());
        }
    }

    OstostapahtumaDTO dto = new OstostapahtumaDTO();
    dto.setOstostapahtumaId(ostostapahtuma.getOstostapahtumaId());
    dto.setMyyntiaika(ostostapahtuma.getMyyntiaika());
    dto.setKayttajaId(ostostapahtuma.getKayttaja().getKayttajaId());
    dto.setLiput(lippuIdt);
    dto.setSumma(summa);
    dto.setTapahtumaNimet(tapahtumaNimet);
    dto.setLipputyyppienNimet(lipputyyppienNimet);

    return ResponseEntity.ok(dto);
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
