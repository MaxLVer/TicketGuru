/*package com.melkeinkood.ticket_guru.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;

@Service
public class UserServise {

    @Autowired
    private KayttajaRepository kayttajaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Kayttaja rekisteroiKayttaja(String kayttajanimi, String salasana) {
        String hashedSalasana = passwordEncoder.encode(salasana);
        Kayttaja uusiKayttaja = new Kayttaja(kayttajanimi, hashedSalasana);
        return kayttajaRepository.save(uusiKayttaja);
    }

    public boolean tarkistaSalsana(String perusSalasana, String hashedSalasana) {
        return passwordEncoder.matches(perusSalasana, hashedSalasana);
    }
    
} */
