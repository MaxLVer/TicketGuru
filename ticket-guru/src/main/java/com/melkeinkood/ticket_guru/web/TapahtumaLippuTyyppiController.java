package com.melkeinkood.ticket_guru.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.*;

import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;
import com.melkeinkood.ticket_guru.model.dto.TapahtumaLipputyyppiDTO;
import com.melkeinkood.ticket_guru.repositories.TapahtumaLipputyyppiRepository;

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

    private TapahtumaLipputyyppiDTO toDTO(TapahtumaLipputyyppi tapahtumaLipputyyppi) {
        String tapahtumaHref = linkTo(methodOn(TapahtumaController.class)
                .haeTapahtuma(tapahtumaLipputyyppi.getTapahtuma().getTapahtumaId())).toUri().toString();

        String asiakastyyppiHref = linkTo(methodOn(AsiakastyyppiController.class)
                .haeAsiakastyyppi(tapahtumaLipputyyppi.getAsiakastyyppi().getAsiakastyyppiId())).toUri().toString();

        return new TapahtumaLipputyyppiDTO(
                tapahtumaLipputyyppi.getTapahtumaLipputyyppiId(),
                tapahtumaLipputyyppi.getTapahtuma().getTapahtumaId(),
                tapahtumaHref,
                tapahtumaLipputyyppi.getAsiakastyyppi().getAsiakastyyppiId(),
                asiakastyyppiHref,
                tapahtumaLipputyyppi.getHinta());
    }

    @GetMapping
    public ResponseEntity<List<TapahtumaLipputyyppiDTO>> haeKaikkiTapahtumaLipputyypit() {
        Iterable<TapahtumaLipputyyppi> tapahtumaLipputyyppiList = tapahtumaLipputyyppiRepo.findAll();

        List<TapahtumaLipputyyppiDTO> dtoList = new ArrayList<>();
        for (TapahtumaLipputyyppi tapahtumaLipputyyppi : tapahtumaLipputyyppiList) {
            TapahtumaLipputyyppiDTO dto = toDTO(tapahtumaLipputyyppi); 
            dtoList.add(dto); 
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TapahtumaLipputyyppiDTO> haeTapahtumaLipputyyppi(@PathVariable Long id) {
        return tapahtumaLipputyyppiRepo.findById(id)
                .map(this::toDTO)  
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<TapahtumaLipputyyppiDTO> luoTapahtumaLipputyyppi(@RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
        TapahtumaLipputyyppi tapahtumaLipputyyppi = new TapahtumaLipputyyppi();

        tapahtumaLipputyyppi.setTapahtumaLipputyyppiId(tapahtumaLipputyyppiDTO.getTapahtumaLipputyyppiId());

        TapahtumaLipputyyppi saved = tapahtumaLipputyyppiRepo.save(tapahtumaLipputyyppi);

        TapahtumaLipputyyppiDTO savedDto = toDTO(saved);
        return ResponseEntity.ok(savedDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TapahtumaLipputyyppiDTO> muokkaaTapahtumaLipputyyppi(
        @PathVariable Long id,
        @RequestBody TapahtumaLipputyyppiDTO tapahtumaLipputyyppiDTO) {
    
            Optional<TapahtumaLipputyyppi> existingEntity = tapahtumaLipputyyppiRepo.findById(id);
        if (existingEntity.isPresent()) {
            TapahtumaLipputyyppi tapahtumaLipputyyppi = existingEntity.get();
            tapahtumaLipputyyppi.setTapahtumaLipputyyppiId(tapahtumaLipputyyppiDTO.getTapahtumaLipputyyppiId());
            TapahtumaLipputyyppi updated = tapahtumaLipputyyppiRepo.save(tapahtumaLipputyyppi);
            TapahtumaLipputyyppiDTO updatedDto = toDTO(updated);
            return ResponseEntity.ok(updatedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> poistaTapahtumaLipputyyppi(@PathVariable Long id) {
        Optional<TapahtumaLipputyyppi> existingEntity = tapahtumaLipputyyppiRepo.findById(id);
        if (existingEntity.isPresent()) {
            tapahtumaLipputyyppiRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
