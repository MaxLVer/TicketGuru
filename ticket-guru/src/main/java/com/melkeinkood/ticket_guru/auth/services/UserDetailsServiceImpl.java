package com.melkeinkood.ticket_guru.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.model.Kayttaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private KayttajaRepository kayttajaRepository;

    // Loggeri virheiden ja tapahtumien kirjaamista varten
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        Kayttaja kayttaja = kayttajaRepository.findByKayttajanimi(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Could not find user with username: " + username));

        logger.info("User Authenticated Successfully..!!!");
        
        return new CustomUserDetails(kayttaja);
    }
}
