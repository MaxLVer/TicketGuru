package com.melkeinkood.ticket_guru.auth.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.model.Kayttaja;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private KayttajaRepository kayttajaRepository;

    @Override
    public UserDetails loadUserByUsername(String kayttajanimi) throws UsernameNotFoundException {
        Kayttaja kayttaja = kayttajaRepository.findByKayttajanimi(kayttajanimi);

        if (kayttaja == null) {
            throw new UsernameNotFoundException("Käyttäjää ei löydy: " + kayttajanimi);
        }

        return new org.springframework.security.core.userdetails.User(
        kayttaja.getKayttajanimi(),
        kayttaja.getSalasana(),
        new ArrayList<>()
    );
}
}
