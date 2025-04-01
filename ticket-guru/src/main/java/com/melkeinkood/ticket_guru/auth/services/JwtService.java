package com.melkeinkood.ticket_guru.auth.services;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtService {

    @Value("${jwt.expiration}")
    private Long jwtExpirationInMS;

    private SecretKey key =  Jwts.SIG.HS384.key().build();

    public String generateToken(String kayttajanimi) {
        Date now = new Date ();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMS);
        return Jwts.builder()
                .subject(kayttajanimi)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
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
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid token signature: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("Token is malformed: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            System.out.println("Token has expired: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("Token is unsupported: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is empty or null: " + ex.getMessage());
        }
        return false;
    }
    
}
