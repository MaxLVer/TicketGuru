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

    public RefreshToken createRefreshToken(String username){
        Kayttaja user = userRepository.findByKayttajanimi(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        RefreshToken refreshToken = RefreshToken.builder()
                .kayttaja(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;

    }

}
