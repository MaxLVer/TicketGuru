package com.melkeinkood.ticket_guru.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.melkeinkood.ticket_guru.model.Lippu;
import com.melkeinkood.ticket_guru.model.Ostostapahtuma;
import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;
import com.melkeinkood.ticket_guru.model.dto.LippuDTO;
import com.melkeinkood.ticket_guru.repositories.*;

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

    private EntityModel<Lippu> toEntityModel(Lippu lippu){
        
        Link selfLink = linkTo(methodOn(LippuController.class).haeLippu(lippu.getLippuId())).withSelfRel();
        Link tapahtumaLink = linkTo(methodOn(TapahtumaController.class).haeTapahtuma(lippu.getTapahtuma().getTapahtumaId())).withRel("tapahtuma");
        Link tapahtumaLipputyyppiLink = linkTo(methodOn(TapahtumaLippuTyyppiController.class).haeTapahtumaLipputyyppi(lippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId())).withRel("tapahtumalipputyyppi");
        Link ostostapahtumaLink = linkTo(methodOn(OstostapahtumaController.class).haeOstostapahtuma(lippu.getOstostapahtuma().getOstostapahtumaId())).withRel("ostostapahtuma");
        
        return EntityModel.of(lippu, selfLink, tapahtumaLink, tapahtumaLipputyyppiLink, ostostapahtumaLink);
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
            return ResponseEntity.ok(toEntityModel(lippu));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/liput")
    public ResponseEntity<CollectionModel<EntityModel<Lippu>>> haeKaikkiLiput(){
        List<Lippu> liput = lippuRepository.findAll();
        List<EntityModel<Lippu>> liputModel = liput.stream()
            .map(this::toEntityModel)
            .collect(Collectors.toList());
        if(liputModel != null){
            return ResponseEntity.ok(CollectionModel.of(liputModel));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/liput")
    public ResponseEntity<EntityModel<Lippu>> luoLippu(@RequestBody Lippu lippu){
        Tapahtuma tapahtuma = tapahtumaRepository.findByTapahtumaId(lippu.getTapahtuma().getTapahtumaId());

        TapahtumaLipputyyppi tapahtumaLipputyyppi = tapahtumaLipputyyppiRepository.findById(lippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId())
            .orElseThrow(() -> new ResourceNotFoundException("tapahtumalipputyyppiä ei löydy ID:llä: " + lippu.getTapahtumaLipputyyppi().getTapahtumaLipputyyppiId()));

        Ostostapahtuma ostostapahtuma = ostostapahtumaRepository.findById(lippu.getOstostapahtuma().getOstostapahtumaId())
            .orElseThrow(() -> new ResourceNotFoundException("Ostostapahtumaa ei löydy ID:llä: " + lippu.getOstostapahtuma().getOstostapahtumaId()));

        lippu.setTapahtumaLipputyyppi(tapahtumaLipputyyppi);
        lippu.setTapahtuma(tapahtuma);
        lippu.setOstostapahtuma(ostostapahtuma);
        lippuRepository.save(lippu);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(lippu));
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
    public ResponseEntity<EntityModel<Lippu>> muokkaaLippua(@RequestBody Lippu lippu, @PathVariable("id") Long lippuId){
        if(lippuRepository.existsById(lippuId)){
            lippu.setLippuId(lippuId);
            Lippu muokattuLippu = lippuRepository.save(lippu);
            return ResponseEntity.status(HttpStatus.OK).body(toEntityModel(muokattuLippu));
        } else {
            return ResponseEntity.notFound().build();
        }
    
    }


}
