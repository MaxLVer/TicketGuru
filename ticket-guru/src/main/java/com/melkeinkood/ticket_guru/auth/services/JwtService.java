package com.melkeinkood.ticket_guru.auth.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.security.Key;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtService {

    //JWT voimassaoloaika
    @Value("${jwt.expiration}")
    private Long jwtExpirationInMS;

    //salainen avain JWT kirjautumiseen
    @Value("${jwt.secret}")
    private String secretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Yleiskäyttöinen vaatimusten poimija, jossa käytetään lambdaa
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Liittää tokenin, vahvistaa sen allekirjoitusavaimella ja palauttaa kaikki vaatimukset
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //tarkistaa jos token on vanhentunut
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //validoin tokenin 
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //generoi tokenin käyttäjänimelle
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    //Apumetodi joka auttaa rakentamaan JWT vaatimuksilla, aiheella, myöntämisajalla, vanhenemisella ja allekirjoituksella
    private String createToken(Map<String, Object> claims, String username) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMS);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .signWith(getSignKey())
                .compact();
    }

    //Purkaa base64-salaisen avaimen ja palauttaa sen HMAC SHA -avaimena
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public Long getJwtExpirationInMS() {
        return jwtExpirationInMS;
    }
}