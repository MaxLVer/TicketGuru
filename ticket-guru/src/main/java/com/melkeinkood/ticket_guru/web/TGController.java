package com.melkeinkood.ticket_guru.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;
import com.melkeinkood.ticket_guru.model.Tapahtumapaikka;
import com.melkeinkood.ticket_guru.repositories.TapahtumapaikkaRepository;

@Controller
public class TGController {
    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    @Autowired
    private TapahtumapaikkaRepository tapahtumapaikkaRepository;

    @GetMapping("/etusivu")
    public String etsivu(){
        return "etusivu";
    }

    @GetMapping("/tapahtumaLista")
    public String kaikkiTapahtumat(Model model){
        model.addAttribute("tapahtumat", tapahtumaRepository.findAll());
        return "tapahtumaLista";
    }

    
}
