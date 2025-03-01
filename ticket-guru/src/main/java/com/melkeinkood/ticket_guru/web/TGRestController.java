package com.melkeinkood.ticket_guru.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.*;
import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;

@RestController
public class TGRestController {
    @Autowired
    TapahtumaRepository tapahtumaRepository;

    @DeleteMapping("/tapahtumat/{id}")
    public Iterable<Tapahtuma> poistaTapahtuma(@PathVariable("id") Long tapahtumaId){
        tapahtumaRepository.deleteById(tapahtumaId);
        return tapahtumaRepository.findAll();
    }

    
    
}
