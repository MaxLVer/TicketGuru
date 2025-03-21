package com.melkeinkood.ticket_guru.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;



@RestController
@RequestMapping("/tapahtumalipputyypit")
public class TapahtumaLippuTyyppiController {
    @Autowired
    private TapahtumaLipputyyppiRepository tapahtumaLipputyyppiRepo;

    @Autowired
    private TapahtumaRepository tapahtumaRepo;

    @Autowired
    private AsiakastyyppiRepository asiakastyyppiRepo;

    private EntityModel<TapahtumaLipputyyppiDTO> toDTO(TapahtumaLipputyyppi tapahtumaLipputyyppi) {
        TapahtumaLipputyyppiDTO dto = new TapahtumaLipputyyppiDTO(
                tapahtumaLipputyyppi.getTapahtumaLipputyyppiId(),
                tapahtumaLipputyyppi.getTapahtuma().getTapahtumaId(),
                tapahtumaLipputyyppi.getAsiakastyyppi().getAsiakastyyppiId(),
                tapahtumaLipputyyppi.getHinta()
        );
//Testaa vain
        return EntityModel.of(dto,
                linkTo(methodOn(TapahtumaLippuTyyppiController.class).haeTapahtumaLipputyyppi(dto.getTapahtumaLipputyyppiId())).withSelfRel(),
                linkTo(methodOn(TapahtumaLippuTyyppiController.class).getTapahtumaLipputyypit()).withRel("all-tapahtumalipputyypit")
        );
    }

    @GetMapping
    public List<EntityModel<TapahtumaLipputyyppiDTO>> getTapahtumaLipputyypit() {
        return tapahtumaLipputyyppiRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TapahtumaLipputyyppiDTO>> haeTapahtumaLipputyyppi(@PathVariable Long id) {
        return tapahtumaLipputyyppiRepo.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<EntityModel<TapahtumaLipputyyppiDTO>> lisaaTapahtumaLipputyyppi(@RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
        Tapahtuma tapahtuma = tapahtumaRepo
                .findByTapahtumaId(tapahtumaLipputyyppiDTO.getTapahtumaId());

        Asiakastyyppi asiakastyyppi = asiakastyyppiRepo
                .findByAsiakastyyppiId(tapahtumaLipputyyppiDTO.getAsiakastyyppiId())
                .orElseThrow(() -> new RuntimeException("Asiakastyyppi not found with id: " + tapahtumaLipputyyppiDTO.getAsiakastyyppiId()));

        TapahtumaLipputyyppi uusiTapahtumaLipputyyppi = new TapahtumaLipputyyppi(
                tapahtuma,
                asiakastyyppi,
                tapahtumaLipputyyppiDTO.getHinta());

        TapahtumaLipputyyppi savedTapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.save(uusiTapahtumaLipputyyppi);

        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(savedTapahtumaLipputyyppi));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> muokkaaTapahtumaLipputyyppi(
        @PathVariable Long id,
        @RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
    
            return tapahtumaLipputyyppiRepo.findById(id)
            .map(tapahtumaLipputyyppi -> {
                tapahtumaLipputyyppi.setHinta(tapahtumaLipputyyppiDTO.getHinta());
                tapahtumaLipputyyppiRepo.save(tapahtumaLipputyyppi);
                return ResponseEntity.ok("TapahtumaLipputyyppi updated successfully.");
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: TapahtumaLipputyyppi with id " + id + " not found."));
}

     @DeleteMapping("/{id}")
     public ResponseEntity<String> deleteTapahtumaLipputyyppi(@PathVariable Long id) {
        if (!tapahtumaLipputyyppiRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: TapahtumaLipputyyppi with id " + id + " not found.");
        }
    
        tapahtumaLipputyyppiRepo.deleteById(id);
        return ResponseEntity.ok("TapahtumaLipputyyppi with id " + id + " deleted successfully.");
    }
    }
