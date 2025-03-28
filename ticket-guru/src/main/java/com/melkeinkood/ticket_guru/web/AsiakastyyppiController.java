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

    private EntityModel<AsiakastyyppiDTO> toEntityModel(AsiakastyyppiDTO asiakastyyppiDTO) {
        Link selfLink = linkTo(
                methodOn(AsiakastyyppiController.class).haeAsiakastyyppi(asiakastyyppiDTO.getAsiakastyypiId()))
                .withSelfRel();

        return EntityModel.of(asiakastyyppiDTO, selfLink);
    }

    private AsiakastyyppiDTO convertToDTO(Asiakastyyppi asiakastyyppi) {
        AsiakastyyppiDTO dto = new AsiakastyyppiDTO();
        dto.setAsiakastyypiId(asiakastyyppi.getAsiakastyyppiId());
        dto.setAsiakastyyppi(asiakastyyppi.getAsiakastyyppi());
        return dto;
    }

    @GetMapping("/asiakastyypit")
    public ResponseEntity<List<EntityModel<AsiakastyyppiDTO>>> haeKaikkiAsiakastyypit() {
        List<Asiakastyyppi> asiakastyypit = asiakastyyppiRepository.findAll();
        List<EntityModel<AsiakastyyppiDTO>> asiakastyypitModel = asiakastyypit.stream()
                .map(asiakastyyppi -> toEntityModel(convertToDTO(asiakastyyppi)))
                .collect(Collectors.toList());

        if (asiakastyypitModel != null) {
            return ResponseEntity.ok(asiakastyypitModel);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/asiakastyypit/{id}")
    public ResponseEntity<Object> haeAsiakastyyppi(@PathVariable Long id) {
        Asiakastyyppi asiakasTyyppi = asiakastyyppiRepository.findById(id).orElse(null);
        if (asiakasTyyppi != null) {
            AsiakastyyppiDTO asiakastyyppiDTO = new AsiakastyyppiDTO(asiakasTyyppi);
            return ResponseEntity.ok(toEntityModel(asiakastyyppiDTO));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/asiakastyypit")
    public ResponseEntity<?> lisaaAsiakastyyppi(@Valid @RequestBody AsiakastyyppiDTO asiakastyyppiDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        if (asiakastyyppiDTO.getAsiakastyyppi().isEmpty() || asiakastyyppiDTO.getAsiakastyyppi() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("Asiakastyyppi ei voi olla tyhjä")));}
        Asiakastyyppi uusiAsiakastyyppi = new Asiakastyyppi();
        uusiAsiakastyyppi.setAsiakastyyppi(asiakastyyppiDTO.getAsiakastyyppi());
        asiakastyyppiRepository.save(uusiAsiakastyyppi);
        asiakastyyppiDTO.setAsiakastyypiId(uusiAsiakastyyppi.getAsiakastyyppiId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(asiakastyyppiDTO));

    }

    @DeleteMapping("/asiakastyypit/{id}")
    public ResponseEntity<Void> poistaAsiakastyyppi(@PathVariable("id") Long asiakastyyppiId) {
        if (asiakastyyppiRepository.existsById(asiakastyyppiId)) {
            asiakastyyppiRepository.deleteById(asiakastyyppiId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/asiakastyypit/{id}")
    public ResponseEntity<?> muokkaaAsiakastyyppiä(
            @Valid @RequestBody Asiakastyyppi asiakastyyppi,
            @PathVariable("id") Long asiakastyyppiId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        if (asiakastyyppi.getAsiakastyyppi().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("Asiakastyyppi ei voi olla tyhjä")));
        }
        if (asiakastyyppiRepository.existsById(asiakastyyppiId)) {
            asiakastyyppi.setAsiakastyyppiId(asiakastyyppiId);
            Asiakastyyppi muokattuAsiakastyyppi = asiakastyyppiRepository.save(asiakastyyppi);
            return ResponseEntity.status(HttpStatus.OK).body(toEntityModel(convertToDTO(muokattuAsiakastyyppi)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}