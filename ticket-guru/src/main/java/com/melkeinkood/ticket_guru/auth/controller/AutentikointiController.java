package com.melkeinkood.ticket_guru.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.melkeinkood.ticket_guru.auth.services.JwtService;

import jakarta.validation.Valid;


@RestController
public class AutentikointiController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/autentikointi")
    public String autentikointi(@Valid @RequestBody String kayttajanimi,
    @Valid @RequestBody String salasana) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kayttajanimi, salasana));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtService.generateToken(kayttajanimi);
    }
}
