package com.melkeinkood.ticket_guru.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.repositories.*;
import com.melkeinkood.ticket_guru.model.dto.AsiakastyyppiDTO;

@RestController
public class AsiakastyyppiController {

    @Autowired
    AsiakastyyppiRepository asiakastyyppiRepository;

    @GetMapping("/asiakastyypit")
    public List<AsiakastyyppiDTO> haeKaikkiAsiakastyypit() {
        List<Asiakastyyppi> asiakastyypit = asiakastyyppiRepository.findAll();
        List<AsiakastyyppiDTO> asiakastyyppiDTOs = asiakastyypit.stream()
                .map(AsiakastyyppiDTO::new)
                .collect(Collectors.toList());
        return asiakastyyppiDTOs;
    }

    @GetMapping("/asiakastyypit/{id}")
    public ResponseEntity<AsiakastyyppiDTO> haeAsiakastyyppi(@PathVariable Long id) {
        Asiakastyyppi asiakasTyyppi = asiakastyyppiRepository.findById(id).orElse(null);
        if (asiakasTyyppi != null) {
            AsiakastyyppiDTO asiakastyyppiDTO = new AsiakastyyppiDTO(asiakasTyyppi);
            return ResponseEntity.ok(asiakastyyppiDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/asiakastyypit")
    public ResponseEntity<AsiakastyyppiDTO> lisaaAsiakastyyppi(@RequestBody AsiakastyyppiDTO asiakastyyppiDTO) {
        Asiakastyyppi uusiAsiakastyyppi = new Asiakastyyppi();
        uusiAsiakastyyppi.setAsiakastyyppi(asiakastyyppiDTO.getAsiakastyyppi());
        asiakastyyppiRepository.save(uusiAsiakastyyppi);
        asiakastyyppiDTO.setAsiakastyypiId(uusiAsiakastyyppi.getAsiakastyyppiId());
        return ResponseEntity.status(HttpStatus.CREATED).body(asiakastyyppiDTO);

    }

    @DeleteMapping("/asiakastyypit/{id}")
    public ResponseEntity<Void> poistaAsiakastyyppi(@PathVariable("id") Long asiakastyyppiId){
        if (asiakastyyppiRepository.existsById(asiakastyyppiId)){
            asiakastyyppiRepository.deleteById(asiakastyyppiId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    } 

    @PutMapping("/asiakastyypit/{id}")
    public ResponseEntity<Asiakastyyppi> muokkaaAsiakastyyppiä(@RequestBody Asiakastyyppi asiakastyyppi, @PathVariable("id") Long asiakastyyppiId){
        if(asiakastyyppiRepository.existsById(asiakastyyppiId)){
            asiakastyyppi.setAsiakastyyppiId(asiakastyyppiId);
            Asiakastyyppi tallennettuAsiakastyyppi = asiakastyyppiRepository.save(asiakastyyppi);
            return ResponseEntity.status(HttpStatus.OK).body(tallennettuAsiakastyyppi);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}