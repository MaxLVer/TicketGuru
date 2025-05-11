package com.melkeinkood.ticket_guru.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.repositories.*;

import jakarta.validation.Valid;

import com.melkeinkood.ticket_guru.model.dto.AsiakastyyppiDTO;

@RestController
public class AsiakastyyppiController {

    @Autowired
    AsiakastyyppiRepository asiakastyyppiRepository;
    // Muuntaa DTO:n HATEOAS-yhteensopivaksi EntityModeliksi, johon lisätään linkki kyseiseen resurssiin
    private EntityModel<AsiakastyyppiDTO> toEntityModel(AsiakastyyppiDTO asiakastyyppiDTO) {
        Link selfLink = linkTo(
                methodOn(AsiakastyyppiController.class).haeAsiakastyyppi(asiakastyyppiDTO.getAsiakastyyppiId()))
                .withSelfRel(); // Luo itseensä viittaava linkki

        return EntityModel.of(asiakastyyppiDTO, selfLink); // Palautetaan DTO + linkki
    }
    // Muuntaa Asiakastyyppi-entiteetin DTO-muotoon
    private AsiakastyyppiDTO convertToDTO(Asiakastyyppi asiakastyyppi) {
        AsiakastyyppiDTO dto = new AsiakastyyppiDTO();
        dto.setAsiakastyyppiId(asiakastyyppi.getAsiakastyyppiId());
        dto.setAsiakastyyppi(asiakastyyppi.getAsiakastyyppi());
        return dto;
    }
    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Haetaan kaikki asiakastyypit
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/asiakastyypit")
    public ResponseEntity<List<EntityModel<AsiakastyyppiDTO>>> haeKaikkiAsiakastyypit() {
        List<Asiakastyyppi> asiakastyypit = asiakastyyppiRepository.findAll();
        List<EntityModel<AsiakastyyppiDTO>> asiakastyypitModel = asiakastyypit.stream()
                .map(asiakastyyppi -> toEntityModel(convertToDTO(asiakastyyppi))) // Muunnetaan DTO-muotoon ja lisätään linkit
                .collect(Collectors.toList());

        if (asiakastyypitModel != null) {
            return ResponseEntity.ok(asiakastyypitModel);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    // Sallitaan vain ADMIN- ja SALESPERSON-rooleille pääsy tähän endpointiin
    // Hakee yksittäisen asiakastyypin ID:n perusteella
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/asiakastyypit/{id}")
    public ResponseEntity<Object> haeAsiakastyyppi(@PathVariable Long id) {
        Asiakastyyppi asiakastyyppi = asiakastyyppiRepository.findById(id).orElse(null);
        if (asiakastyyppi != null) {
            AsiakastyyppiDTO asiakastyyppiDTO = new AsiakastyyppiDTO(asiakastyyppi); // Muunnetaan DTO:ksi
            return ResponseEntity.ok(toEntityModel(asiakastyyppiDTO)); // Palautetaan DTO ja linkit
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asiakastyyppiä ei löydy id:llä " + id);
        }
    }
    // Luo uuden asiakastyypin – sallittu vain ADMIN-roolille
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/asiakastyypit")
    public ResponseEntity<?> lisaaAsiakastyyppi(@Valid @RequestBody AsiakastyyppiDTO asiakastyyppiDTO,
            BindingResult bindingResult) {
        // Tarkistetaan validointivirheet  
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        // Lisävarmistus ettei asiakastyyppi-kenttä ole tyhjä
        if (asiakastyyppiDTO.getAsiakastyyppi().isEmpty() || asiakastyyppiDTO.getAsiakastyyppi() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("Asiakastyyppi ei voi olla tyhjä")));}
        // Tallennetaan uusi asiakastyyppi
        Asiakastyyppi uusiAsiakastyyppi = new Asiakastyyppi();
        uusiAsiakastyyppi.setAsiakastyyppi(asiakastyyppiDTO.getAsiakastyyppi());
        asiakastyyppiRepository.save(uusiAsiakastyyppi);
        // Asetetaan tallennetun entiteetin ID takaisin DTO:lle
        asiakastyyppiDTO.setAsiakastyyppiId(uusiAsiakastyyppi.getAsiakastyyppiId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(asiakastyyppiDTO));

    }
    // Poistaa asiakastyypin annetun ID:n perusteella – vain ADMIN:lle
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/asiakastyypit/{id}")
    public ResponseEntity<?> poistaAsiakastyyppi(@PathVariable("id") Long asiakastyyppiId) {
        if(!asiakastyyppiRepository.existsById(asiakastyyppiId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asiakastyyppiä ei löydy id:llä " + asiakastyyppiId);
        }
        if (asiakastyyppiRepository.existsById(asiakastyyppiId)) {
            asiakastyyppiRepository.deleteById(asiakastyyppiId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Asiakastyyppi " + asiakastyyppiId + " poistettu");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Päivittää olemassa olevan asiakastyypin tiedot – vain ADMIN:ll
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/asiakastyypit/{id}")
    public ResponseEntity<?> muokkaaAsiakastyyppiä(
            @Valid @RequestBody Asiakastyyppi asiakastyyppi, BindingResult bindingResult,
            @PathVariable("id") Long asiakastyyppiId) {
        // Tarkistetaan validointivirheet
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        // Lisävarmistus ettei asiakastyyppi-kenttä ole tyhjä
        if (asiakastyyppi.getAsiakastyyppi().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("Asiakastyyppi ei voi olla tyhjä")));
        }
        // Jos ID:llä löytyy entiteetti, päivitetään se
        if (asiakastyyppiRepository.existsById(asiakastyyppiId)) {
            asiakastyyppi.setAsiakastyyppiId(asiakastyyppiId);
            Asiakastyyppi muokattuAsiakastyyppi = asiakastyyppiRepository.save(asiakastyyppi);
            return ResponseEntity.status(HttpStatus.OK).body(toEntityModel(convertToDTO(muokattuAsiakastyyppi)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asiakastyyppiä ei löydy id:llä " + asiakastyyppiId);
        }
    }

}
