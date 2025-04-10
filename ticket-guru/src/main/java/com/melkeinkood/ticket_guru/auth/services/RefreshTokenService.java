package com.melkeinkood.ticket_guru.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.melkeinkood.ticket_guru.auth.model.RefreshToken;
import com.melkeinkood.ticket_guru.auth.repository.RefreshTokenRepository;
import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    KayttajaRepository userRepository;

    //luo uusi refresh token
    public RefreshToken createRefreshToken(String username){
        Kayttaja user = userRepository.findByKayttajanimi(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        refreshTokenRepository.findByKayttaja(user).ifPresent(existingToken -> {
            refreshTokenRepository.delete(existingToken);
        });

        // Luodaan uusi RefreshToken-olio käyttäjälle
        RefreshToken refreshToken = RefreshToken.builder()
                .kayttaja(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        // Tallennetaan token tietokantaan ja palautetaan se
        return refreshTokenRepository.save(refreshToken);
    }

    //Tokenin haku
    // Palauttaa refresh tokenin sen merkkijonon perusteella (jos löytyy)
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    //tokenin voimassaoloajan takrkistus
    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;

    }

}
