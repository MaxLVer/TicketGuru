package com.melkeinkood.ticket_guru.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpirationInMS;

    private Key getSingInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey key =  Jwts.SIG.HS256.key().build();

    

    public String generateToken(String kayttajanimi) {
        Date now = new Date ();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMS);
        return Jwts.builder()
                .subject(kayttajanimi)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSingInKey())
                .compact();
    }
    
    public String getUsernameFromToken (String token){
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex){
            System.out.println("Invalid token");
        }
        return false;
    }
}
