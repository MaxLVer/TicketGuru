package com.melkeinkood.ticket_guru.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;

@Controller
public class TGController {
    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    //@Autowired
    //private TapahtumapaikkaRepository tapahtumapaikkaRepository;

    @GetMapping("/etusivu")
    public String etsivu(){
        return "etusivu";
    }

    @GetMapping("/tapahtumaLista")
    public String kaikkiTapahtumat(Model model){
        model.addAttribute("tapahtumat", tapahtumaRepository.findAll());
        return "tapahtumaLista";
    }

    // Vain ADMIN saa käyttää
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/only")
    public ResponseEntity<String> adminOnlyEndpoint() {
        return ResponseEntity.ok("Tervetuloa Admin!");
    }

    // Vain SALESPERSON saa käyttää
    @PreAuthorize("hasAuthority('SALESPERSON')")
    @GetMapping("/sales/only")
    public ResponseEntity<String> salespersonOnlyEndpoint() {
        return ResponseEntity.ok("Tervetuloa Myyjä!");
    }

    // Molemmat: ADMIN ja SALESPERSON
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/common")
        public ResponseEntity<String> adminOrSales() {
        return ResponseEntity.ok("Tervetuloa roolillinen käyttäjä!");
}
 
}
