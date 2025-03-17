package com.melkeinkood.ticket_guru.web;

import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
public class TapahtumaLippuTyyppiController {
    @Autowired
    private TapahtumaLipputyyppiRepository tapahtumaLipputyyppiRepo;

    @Autowired
    private TapahtumaRepository tapahtumaRepo;

    @Autowired
    private AsiakastyyppiRepository asiakastyyppiRepo;

    private TapahtumaLipputyyppiDTO toDTO(TapahtumaLipputyyppi tapahtumaLipputyyppi) {
        return new TapahtumaLipputyyppiDTO(
                tapahtumaLipputyyppi.getTapahtumaLipputyyppiId(),
                tapahtumaLipputyyppi.getTapahtuma().getTapahtumaId(),
                tapahtumaLipputyyppi.getAsiakastyyppi().getAsiakastyyppiId(),
                tapahtumaLipputyyppi.getHinta()
        );
    }

    @GetMapping("/tapahtumalipputyypit")
    public List<TapahtumaLipputyyppiDTO> getTapahtumaLipputyypit() {
        return tapahtumaLipputyyppiRepo.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/tapahtumalipputyypit/{id}")
    public ResponseEntity<TapahtumaLipputyyppiDTO> haeTapahtumaLipputyyppi(@PathVariable Long id) {
        TapahtumaLipputyyppi tapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.findById(id).orElse(null);
        return (tapahtumaLipputyyppi != null) ? 
            ResponseEntity.ok(toDTO(tapahtumaLipputyyppi)) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/tapahtumalipputyypit")
    public ResponseEntity<TapahtumaLipputyyppiDTO> lisaaTapahtumaLipputyyppi(@RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
        Tapahtuma tapahtuma = tapahtumaRepo
                .findByTapahtumaId(tapahtumaLipputyyppiDTO.getTapahtumaId());

        //Toimii kun asiakastyyppi DTO on lisätty
        Asiakastyyppi asiakastyyppi = asiakastyyppiRepo
                .findByAsiakastyyppiId(tapahtumaLipputyyppiDTO.getAsiakastyyppiId())
                .orElseThrow(() -> new RuntimeException("Asiakastyyppi not found with id: " + tapahtumaLipputyyppiDTO.getAsiakastyyppiId()));


        TapahtumaLipputyyppi uusiTapahtumaLipputyyppi = new TapahtumaLipputyyppi(
                tapahtuma,
                asiakastyyppi,
                tapahtumaLipputyyppiDTO.getHinta());

        TapahtumaLipputyyppi savedTapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.save(uusiTapahtumaLipputyyppi);

        TapahtumaLipputyyppiDTO responseDTO = toDTO(savedTapahtumaLipputyyppi);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/tapahtumalipputyypit/{id}")
    public ResponseEntity<?> muokkaaTapahtumaLipputyyppi(
        @PathVariable Long id,
        @RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
    
    Optional<TapahtumaLipputyyppi> optionalTapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.findById(id);

    if (optionalTapahtumaLipputyyppi.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: TapahtumaLipputyyppi with id " + id + " not found.");
    }

    TapahtumaLipputyyppi tapahtumaLipputyyppi = optionalTapahtumaLipputyyppi.get();

    tapahtumaLipputyyppi.setHinta(tapahtumaLipputyyppiDTO.getHinta());

    tapahtumaLipputyyppiRepo.save(tapahtumaLipputyyppi);

    return ResponseEntity.ok("TapahtumaLipputyyppi updated successfully.");
}

     @DeleteMapping("/tapahtumalipputyypit/{id}")
     public ResponseEntity<String> deleteTapahtumaLipputyyppi(@PathVariable Long id) {
        if (!tapahtumaLipputyyppiRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: TapahtumaLipputyyppi with id " + id + " not found.");
        }
    
        tapahtumaLipputyyppiRepo.deleteById(id);
        return ResponseEntity.ok("TapahtumaLipputyyppi with id " + id + " deleted successfully.");
    }
    }
