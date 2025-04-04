package com.melkeinkood.ticket_guru.auth.security;

import com.melkeinkood.ticket_guru.auth.services.JwtService;
import com.melkeinkood.ticket_guru.auth.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    //Metodia käytetään per request filterointiin ja JWT validointiin
    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
        ) throws ServletException, IOException {

        String token = null;
        String kayttajanimi = null;

        //tarkista onko requestilla cookie
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("accessToken")){
                    token = cookie.getValue(); //
                }
            }
        }

        //jos tokenia ei löydy, jatka autentikaatio asetuksilla
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        //extract käyttäjänimi tokenista
        kayttajanimi = jwtService.extractUsername(token);

        //jos kättäjänimi on saatu onnistuneesti niin lataa käyttäjän tiedot
        if(kayttajanimi != null){
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(kayttajanimi);

            //validoi token käyttäjän tiedoilla
            if(jwtService.validateToken(token, userDetails)){
            //jos validi niin luo autentikointi token ja lisää se suojakontekstiin
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        //jatka filterchainiä
        filterChain.doFilter(request, response);
    }
}
}
        
      
