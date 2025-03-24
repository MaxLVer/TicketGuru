package com.melkeinkood.ticket_guru.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class LippuController {
    @Autowired
    TapahtumaRepository tapahtumaRepository;

    @Autowired
    OstostapahtumaRepository ostostapahtumaRepository;

    @Autowired
    LippuRepository lippuRepository;

    @Autowired
    TapahtumaLipputyyppiRepository tapahtumaLipputyyppiRepository;

    private EntityModel<LippuDTO> toEntityModel(LippuDTO lippuDTO){
        
        Link selfLink = linkTo(methodOn(LippuController.class).haeLippu(lippuDTO.getLippuId())).withSelfRel();
        Link tapahtumaLink = linkTo(methodOn(TapahtumaController.class).haeTapahtuma(lippuDTO.getTapahtumaId())).withRel("tapahtuma");
        Link tapahtumaLipputyyppiLink = linkTo(methodOn(TapahtumaLipputyyppiController.class).haeTapahtumaLipputyyppi(lippuDTO.getTapahtumaLipputyyppiId())).withRel("tapahtumalipputyyppi");
        Link ostostapahtumaLink = linkTo(methodOn(OstostapahtumaController.class).haeOstostapahtuma(lippuDTO.getOstostapahtumaId())).withRel("ostostapahtuma");
        
        return EntityModel.of(lippuDTO, selfLink, tapahtumaLink, tapahtumaLipputyyppiLink, ostostapahtumaLink);
    }

    private LippuDTO convertToDTO(Lippu lippu){
        LippuDTO dto = new LippuDTO();
        dto.setLippuId(lippu.getLippuId());
        dto.setTunniste(lippu.getTunniste());
        dto.setVoimassaoloaika(lippu.getVoimassaoloaika());
        dto.setOstostapahtumaId(lippu.getOstostapahtuma().getOstostapahtumaId());
        dto.setTapahtumaId(lippu.getTapahtuma().getTapahtumaId());
        dto.setTapahtumaLipputyyppiId(lippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId());
        return dto;

    }
    
    /* @PostMapping("/liput")
    public ResponseEntity<LippuDTO> luoLippu(@RequestBody Lippu uusiLippu) {

        TapahtumaLipputyyppi tapahtumaLipputyyppi = tapahtumaLipputyyppiRepository
                .findById(uusiLippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId())
                .orElseThrow(() -> new IllegalArgumentException("Lipputyyppiä ei löydy"));

        Tapahtuma tapahtuma = tapahtumaRepository
                .findById(uusiLippu.getTapahtuma().getTapahtumaId())
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
    } */

    /* @GetMapping("/liput")
    public List<LippuDTO> haeKaikkiLiput() {

        List<Lippu> liput = lippuRepository.findAll();

        List<LippuDTO> lippuDTOt = liput.stream()
                .map(LippuDTO::new)
                .collect(Collectors.toList());

        return lippuDTOt;
    } */

    @GetMapping("/liput/{id}")
    public ResponseEntity<Object> haeLippu(@PathVariable Long id){
        Lippu lippu = lippuRepository.findById(id).orElse(null);
        if (lippu != null){
            LippuDTO lippuDTO = new LippuDTO(lippu);
            return ResponseEntity.ok(toEntityModel(lippuDTO));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/liput")
    public ResponseEntity<List<EntityModel<LippuDTO>>> haeKaikkiLiput(){
        List<Lippu> liput = lippuRepository.findAll();
        List<EntityModel<LippuDTO>> liputModel = liput.stream()
            .map(lippu -> toEntityModel(convertToDTO(lippu)))
            .collect(Collectors.toList());
        if(liputModel != null){
            return ResponseEntity.ok(liputModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/liput")
    public ResponseEntity<EntityModel<LippuDTO>> luoLippu(@Valid @RequestBody LippuDTO lippuDTO){
        
        Tapahtuma tapahtuma = tapahtumaRepository.findByTapahtumaId(lippuDTO.getTapahtumaId());

        TapahtumaLipputyyppi tapahtumaLipputyyppi = tapahtumaLipputyyppiRepository.findById(lippuDTO.getTapahtumaLipputyyppiId())
            .orElseThrow(() -> new ResourceNotFoundException("tapahtumalipputyyppiä ei löydy ID:llä: " + lippuDTO.getTapahtumaLipputyyppiId()));

        Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(lippuDTO.getOstostapahtumaId())
            .orElseThrow(() -> new ResourceNotFoundException("Ostostapahtumaa ei löydy ID:llä: " + lippuDTO.getOstostapahtumaId()));

        Lippu uusiLippu = new Lippu(
            lippuDTO.getTunniste(),
            ostostapahtuma,
            tapahtumaLipputyyppi,
            lippuDTO.getVoimassaoloaika(),
            tapahtuma
        );
        Lippu tallenttuLippu = lippuRepository.save(uusiLippu);
        
        
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(convertToDTO(tallenttuLippu)));
    }

    @DeleteMapping("/liput/{id}")
    public ResponseEntity<Void> poistaLippu(@PathVariable("id") Long lippuId){
        if (lippuRepository.existsById(lippuId)){
            lippuRepository.deleteById(lippuId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/liput/{id}")
    public ResponseEntity<EntityModel<LippuDTO>> muokkaaLippua(@Valid @RequestBody Lippu lippu, @PathVariable("id") Long lippuId){
        if(lippuRepository.existsById(lippuId)){
            lippu.setLippuId(lippuId);
            Lippu muokattuLippu = lippuRepository.save(lippu);
            return ResponseEntity.status(HttpStatus.OK).body(toEntityModel(convertToDTO(muokattuLippu)));
        } else {
            return ResponseEntity.notFound().build();
        }
    
    }


}
