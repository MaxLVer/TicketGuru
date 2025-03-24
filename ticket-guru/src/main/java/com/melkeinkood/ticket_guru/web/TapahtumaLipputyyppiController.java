package com.melkeinkood.ticket_guru.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import java.util.stream.Collectors;

import com.melkeinkood.ticket_guru.model.Asiakastyyppi;
import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;
import com.melkeinkood.ticket_guru.model.dto.TapahtumaLipputyyppiDTO;
import com.melkeinkood.ticket_guru.repositories.AsiakastyyppiRepository;
import com.melkeinkood.ticket_guru.repositories.TapahtumaLipputyyppiRepository;
import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class TapahtumaLipputyyppiController {
    @Autowired
    private TapahtumaLipputyyppiRepository tapahtumaLipputyyppiRepo;

    @Autowired
    private TapahtumaRepository tapahtumaRepo;

    @Autowired
    private AsiakastyyppiRepository asiakastyyppiRepo;

    private EntityModel<TapahtumaLipputyyppiDTO> toEntityModel(TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
        Link selfLink = linkTo(
                methodOn(TapahtumaLipputyyppiController.class).haeTapahtumaLipputyyppi(tapahtumaLipputyyppiDTO.getTapahtumaLipputyyppiId()))
                .withSelfRel();
    
        Link tapahtumaLink = linkTo(
                methodOn(TapahtumaController.class).haeTapahtuma(tapahtumaLipputyyppiDTO.getTapahtumaId()))
                .withRel("tapahtuma");
    
        Link asiakastyyppiLink = linkTo(
                methodOn(AsiakastyyppiController.class).haeAsiakastyyppi(tapahtumaLipputyyppiDTO.getAsiakastyyppiId()))
                .withRel("asiakastyyppi");
    
        return EntityModel.of(tapahtumaLipputyyppiDTO, selfLink, tapahtumaLink, asiakastyyppiLink);
    }

    private TapahtumaLipputyyppiDTO toDTO(TapahtumaLipputyyppi tapahtumaLipputyyppi) {
        return new TapahtumaLipputyyppiDTO(
                tapahtumaLipputyyppi.getTapahtumaLipputyyppiId(),
                tapahtumaLipputyyppi.getTapahtuma().getTapahtumaId(),
                tapahtumaLipputyyppi.getAsiakastyyppi().getAsiakastyyppiId(),
                tapahtumaLipputyyppi.getHinta()
        );
    }

    @GetMapping("/tapahtumalipputyypit")
public ResponseEntity<List<EntityModel<TapahtumaLipputyyppiDTO>>> haeKaikkiTapahtumaLipputyypit() {
    List<TapahtumaLipputyyppi> tapahtumaLipputyypit = tapahtumaLipputyyppiRepo.findAll();

    List<EntityModel<TapahtumaLipputyyppiDTO>> tapahtumaLipputyyppiModel = tapahtumaLipputyypit.stream()
        .map(tapahtumaLipputyyppi -> toEntityModel(toDTO(tapahtumaLipputyyppi)))
        .collect(Collectors.toList());

    if (tapahtumaLipputyyppiModel.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(tapahtumaLipputyyppiModel);
}

    @GetMapping("/tapahtumalipputyypit/{id}")
    public ResponseEntity<EntityModel<TapahtumaLipputyyppiDTO>> haeTapahtumaLipputyyppi(@PathVariable Long id) {

        Optional<TapahtumaLipputyyppi> optionalTapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.findById(id);
    
    if (optionalTapahtumaLipputyyppi.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO = toDTO(optionalTapahtumaLipputyyppi.get());
    EntityModel<TapahtumaLipputyyppiDTO> entityModel = toEntityModel(tapahtumaLipputyyppiDTO);

    return ResponseEntity.ok(entityModel);
}
    
    @PostMapping("/tapahtumalipputyypit")
    public ResponseEntity<EntityModel<TapahtumaLipputyyppiDTO>> lisaaTapahtumaLipputyyppi(@RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
        Optional<Tapahtuma> tapahtumaOptional = tapahtumaRepo.findById(tapahtumaLipputyyppiDTO.getTapahtumaId());
    if (tapahtumaOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
    Tapahtuma tapahtuma = tapahtumaOptional.get();

        Optional <Asiakastyyppi> asiakastyyppiOptional = asiakastyyppiRepo.findByAsiakastyyppiId(tapahtumaLipputyyppiDTO.getAsiakastyyppiId());
        if (asiakastyyppiOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Asiakastyyppi asiakastyyppi = asiakastyyppiOptional.get();

        if (tapahtumaLipputyyppiDTO.getHinta() == null || tapahtumaLipputyyppiDTO.getHinta().doubleValue() < 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null); 
        }

        TapahtumaLipputyyppi uusiTapahtumaLipputyyppi = new TapahtumaLipputyyppi(
                tapahtuma,
                asiakastyyppi,
                tapahtumaLipputyyppiDTO.getHinta());

        TapahtumaLipputyyppi savedTapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.save(uusiTapahtumaLipputyyppi);

        TapahtumaLipputyyppiDTO responseDTO = toDTO(savedTapahtumaLipputyyppi);
    return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(responseDTO));
}
    @PutMapping("/tapahtumalipputyypit/{id}")
    public ResponseEntity<?> muokkaaTapahtumaLipputyyppi(@PathVariable Long id,@RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
    
    Optional<TapahtumaLipputyyppi> optionalTapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.findById(id);

    if (optionalTapahtumaLipputyyppi.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    TapahtumaLipputyyppi tapahtumaLipputyyppi = optionalTapahtumaLipputyyppi.get();
    tapahtumaLipputyyppi.setHinta(tapahtumaLipputyyppiDTO.getHinta());
    tapahtumaLipputyyppiRepo.save(tapahtumaLipputyyppi);

    return ResponseEntity.ok("TapahtumaLipputyyppi updated successfully.");
}

     @DeleteMapping("/tapahtumalipputyypit/{id}")
     public ResponseEntity<String> deleteTapahtumaLipputyyppi(@PathVariable Long id) {
        if (!tapahtumaLipputyyppiRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
    
        tapahtumaLipputyyppiRepo.deleteById(id);
        return ResponseEntity.ok("TapahtumaLipputyyppi with id " + id + " deleted successfully.");
    }
    }