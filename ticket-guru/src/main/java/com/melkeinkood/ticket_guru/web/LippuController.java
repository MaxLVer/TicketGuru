package com.melkeinkood.ticket_guru.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.melkeinkood.ticket_guru.model.Ostostapahtuma;
import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;
import com.melkeinkood.ticket_guru.model.dto.LippuDTO;
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

    private LippuDTO convertToDTO(Lippu lippu) {
        LippuDTO dto = new LippuDTO();
        dto.setLippuId(lippu.getLippuId());
        dto.setOstostapahtumaId(lippu.getOstostapahtuma().getOstostapahtumaId());
        dto.setTapahtumaId(lippu.getTapahtuma().getTapahtumaId());
        dto.setTapahtumaLipputyyppiId(lippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId());
        return dto;

    }


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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/liput")
    public ResponseEntity<List<EntityModel<LippuDTO>>> haeKaikkiLiput() {
        List<Lippu> liput = lippuRepository.findAll();
        List<EntityModel<LippuDTO>> liputModel = liput.stream()
                .map(lippu -> toEntityModel(convertToDTO(lippu)))
                .collect(Collectors.toList());
        if (liputModel != null) {
            return ResponseEntity.ok(liputModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @PostMapping("/liput")
    public ResponseEntity<?> luoLippu(@Valid @RequestBody LippuDTO lippuDTO, BindingResult bindingResult) {

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

        Lippu uusiLippu = new Lippu(
                ostostapahtuma,
                tapahtumaLipputyyppi,
                tapahtuma);
        Lippu tallenttuLippu = lippuRepository.save(uusiLippu);

        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(convertToDTO(tallenttuLippu)));
    }

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/liput/{id}")
    public ResponseEntity<?> muokkaaLippua(@Valid @RequestBody LippuDTO lippuDTO, BindingResult bindingResult, @PathVariable("id") Long lippuId
            ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Lippu olemassaOlevaLippu = lippuRepository.findById(lippuId).orElse(null);
        if (olemassaOlevaLippu == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lippua ei löydy id:llä " + lippuId);
        }

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

        olemassaOlevaLippu.setTapahtuma(tapahtuma);
        olemassaOlevaLippu.setTapahtumaLipputyyppi(tapahtumaLipputyyppi);
        olemassaOlevaLippu.setOstostapahtuma(ostostapahtuma);

        Lippu muokattuLippu = lippuRepository.save(olemassaOlevaLippu);

        return ResponseEntity.ok(toEntityModel(convertToDTO(muokattuLippu)));

        /*
         * if (lippuRepository.existsById(lippuId)) {
         * lippu.setLippuId(lippuId);
         * Lippu muokattuLippu = lippuRepository.save(lippu);
         * return ResponseEntity.status(HttpStatus.OK).body(toEntityModel(convertToDTO(
         * muokattuLippu)));
         * } else {
         * return ResponseEntity.notFound().build();
         * }
         */

    }

}
