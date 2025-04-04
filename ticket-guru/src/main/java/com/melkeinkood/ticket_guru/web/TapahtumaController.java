package com.melkeinkood.ticket_guru.web;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.model.dto.TapahtumaDTO;
import com.melkeinkood.ticket_guru.repositories.*;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class TapahtumaController {
    @Autowired
    TapahtumaRepository tapahtumaRepository;

    @Autowired
    TapahtumapaikkaRepository tapahtumapaikkaRepository;

    private EntityModel<TapahtumaDTO> toEntityModel(TapahtumaDTO tapahtumaDTO) {
        Link selfLink = linkTo(
                methodOn(TapahtumaController.class)
                        .haeTapahtuma(tapahtumaDTO.getTapahtumaId()))
                .withSelfRel();

        Link tapahtumapaikkaLink = linkTo(
                methodOn(TapahtumapaikkaController.class).getTapahtumapaikka(tapahtumaDTO.getTapahtumapaikkaId()))
                .withRel("tapahtumapaikka");

        return EntityModel.of(tapahtumaDTO, selfLink, tapahtumapaikkaLink);
    }

    private TapahtumaDTO toDTO(Tapahtuma tapahtuma) {
        return new TapahtumaDTO(
                tapahtuma.getTapahtumaId(),
                tapahtuma.getTapahtumapaikka().getTapahtumapaikkaId(),
                tapahtuma.getTapahtumaAika(),
                tapahtuma.getTapahtumaNimi(),
                tapahtuma.getKuvaus(),
                tapahtuma.getKokonaislippumaara(),
                tapahtuma.getJaljellaOlevaLippumaara());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/tapahtumat")
    public ResponseEntity<List<EntityModel<TapahtumaDTO>>> haeKaikkiTapahtumat() {
        List<Tapahtuma> tapahtumat = tapahtumaRepository.findAll();

        List<EntityModel<TapahtumaDTO>> tapahtumaModel = tapahtumat.stream()
                .map(tapahtuma -> toEntityModel(toDTO(tapahtuma)))
                .collect(Collectors.toList());

        if (tapahtumaModel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tapahtumaModel);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/tapahtumat/{id}")
    public ResponseEntity<?> haeTapahtuma(@PathVariable Long id) {

        Optional<Tapahtuma> optionalTapahtuma = tapahtumaRepository.findById(id);

        if (optionalTapahtuma.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Tapahtumaa ei löydy"));
        }

        TapahtumaDTO tapahtumaDTO = toDTO(optionalTapahtuma.get());
        EntityModel<TapahtumaDTO> entityModel = toEntityModel(tapahtumaDTO);

        return ResponseEntity.ok(entityModel);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/tapahtumat")
    public ResponseEntity<?> lisaaTapahtuma(
            @Valid @RequestBody TapahtumaDTO tapahtumaDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<Tapahtumapaikka> tapahtumapaikkaOptional = tapahtumapaikkaRepository
                .findById(tapahtumaDTO.getTapahtumapaikkaId());
        if (tapahtumapaikkaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Tapahtumapaikkaa ei löydy"));
        }
        Tapahtumapaikka tapahtumapaikka = tapahtumapaikkaOptional.get();

        Tapahtuma uusiTapahtuma = new Tapahtuma(
                tapahtumapaikka,
                tapahtumaDTO.getTapahtumaAika(),
                tapahtumaDTO.getTapahtumaNimi(),
                tapahtumaDTO.getKuvaus(),
                tapahtumaDTO.getKokonaislippumaara(),
                tapahtumaDTO.getJaljellaOlevaLippumaara());

        Tapahtuma savedTapahtuma = tapahtumaRepository.save(uusiTapahtuma);

        TapahtumaDTO responseDTO = toDTO(savedTapahtuma);
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(responseDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/tapahtumat/{id}")

    public ResponseEntity<?> muokkaaTapahtuma(@PathVariable Long id,
            @Valid @RequestBody TapahtumaDTO tapahtumaDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<Tapahtuma> optionalTapahtuma = tapahtumaRepository.findById(id);

        if (optionalTapahtuma.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Tapahtumaa ei löydy"));
        }

        Optional<Tapahtumapaikka> optionalTapahtumapaikka = tapahtumapaikkaRepository.findById(tapahtumaDTO.getTapahtumapaikkaId());
    if (optionalTapahtumapaikka.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Tapahtumapaikkaa ei löydy"));
    }

        Tapahtuma tapahtuma = optionalTapahtuma.get();
        tapahtuma.setTapahtumaAika(tapahtumaDTO.getTapahtumaAika());
        tapahtuma.setTapahtumaNimi(tapahtumaDTO.getTapahtumaNimi());
        tapahtuma.setKuvaus(tapahtumaDTO.getKuvaus());
        tapahtuma.setKokonaislippumaara(tapahtumaDTO.getKokonaislippumaara());
        tapahtuma.setJaljellaOlevaLippumaara(tapahtumaDTO.getJaljellaOlevaLippumaara());
        tapahtumaRepository.save(tapahtuma);

        return ResponseEntity.ok("Tapahtuma päivitetty.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/tapahtumat/{id}")
    public ResponseEntity<String> deleteTapahtuma(@PathVariable Long id) {
        if (!tapahtumaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        tapahtumaRepository.deleteById(id);
        return ResponseEntity.ok("Tapahtuma id:llä " + id + " on poistettu.");
    }
}
