package com.melkeinkood.ticket_guru.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.util.*;

import com.melkeinkood.ticket_guru.model.Asiakastyyppi;
import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;
import com.melkeinkood.ticket_guru.model.Tapahtumapaikka;
import com.melkeinkood.ticket_guru.model.dto.TapahtumaDTO;
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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TapahtumaLippuTyyppiController {
    @Autowired
    private TapahtumaLipputyyppiRepository tapahtumaLipputyyppiRepo;

    @Autowired
    private TapahtumaRepository tapahtumaRepo;

    @Autowired
    private AsiakastyyppiRepository asiakastyyppiRepo;

    @GetMapping("/tapahtumalipputyypit")
    public Iterable<TapahtumaLipputyyppi> haeKaikkiTapahtumaLipputyypit() {
        return tapahtumaLipputyyppiRepo.findAll();
    }

    @GetMapping("/tapahtumalipputyypit/{id}")
    public ResponseEntity<Object> haeTapahtumaLipputyyppi(@PathVariable Long id) {
        TapahtumaLipputyyppi tapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.findById(id).orElse(null);
        return ResponseEntity.ok(tapahtumaLipputyyppi != null ? tapahtumaLipputyyppi : Collections.emptyMap());
    }
    
    @PostMapping("/tapahtumalipputyypit")
    public ResponseEntity<TapahtumaLipputyyppiDTO> lisaaTapahtumaLipputyyppi(@RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
        Tapahtuma tapahtuma = tapahtumaRepo
                .findByTapahtumaId(tapahtumaLipputyyppiDTO.getTapahtumaId());

        //Toimii kun asiakastyyppi on lisätty
        Asiakastyyppi asiakastyyppi = asiakastyyppiRepo
                .findByAsiakastyyppiId(tapahtumaLipputyyppiDTO.getAsiakastyyppiId());


        TapahtumaLipputyyppi uusiTapahtumaLipputyyppi = new TapahtumaLipputyyppi(
                tapahtuma,
                asiakastyyppi,
                tapahtumaLipputyyppiDTO.getHinta());

        TapahtumaLipputyyppi savedTapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.save(uusiTapahtumaLipputyyppi);

        TapahtumaLipputyyppiDTO responseDTO = new TapahtumaLipputyyppiDTO(
            savedTapahtumaLipputyyppi.getTapahtumaLipputyyppiId(),
            savedTapahtumaLipputyyppi.getTapahtuma().getTapahtumaId(),
            savedTapahtumaLipputyyppi.getAsiakastyyppi().getAsiakastyyppiId(),
            savedTapahtumaLipputyyppi.getHinta()
    );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/tapahtumalipputyypit/{id}")
    public ResponseEntity<TapahtumaLipputyyppi> muokkaaTapahtumaLipputyyppi(@RequestBody TapahtumaLipputyyppi tapahtumaLipputyyppi, @PathVariable Long id) {
        if (tapahtumaLipputyyppiRepo.existsById(id)) {
            tapahtumaLipputyyppi.setTapahtumaLipputyyppiId(id);
            TapahtumaLipputyyppi savedTapahtumaLipputyyppi = tapahtumaLipputyyppiRepo.save(tapahtumaLipputyyppi);
            return ResponseEntity.status(HttpStatus.OK).body(savedTapahtumaLipputyyppi);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

     @DeleteMapping("/tapahtumat/{id}")
    public ResponseEntity<Void> poistaTapahtuma(@PathVariable("id") Long tapahtumaLipputyyppiId) {
        if (tapahtumaLipputyyppiRepo.existsById(tapahtumaLipputyyppiId)) {
            tapahtumaLipputyyppiRepo.deleteById(tapahtumaLipputyyppiId);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
