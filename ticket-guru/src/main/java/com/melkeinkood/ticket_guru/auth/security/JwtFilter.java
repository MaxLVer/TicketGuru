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
        System.out.println(" Requested path: " + request.getRequestURI());
        // Get the request path
        String path = request.getRequestURI();

    // List of endpoints that do not require token validation
        if (path.equals("/kayttajat/luo") || path.equals("/kayttajat/kirjaudu") || path.equals("/kayttajat/uloskirjaudu")) {
            System.out.println("Skipping JWT validation for: " + path);
            System.out.println("Requested path: " + request.getRequestURI());
        filterChain.doFilter(request, response);
            return;
    }
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
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);  // Extract token from header
            }
            }
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token puuttuu\"}");
                System.out.println(" Token missing in the request");
                return;
            }
    
            try {
                // Extract username from the token
                kayttajanimi = jwtService.extractUsername(token);
                System.out.println("Token received: " + token);
                System.out.println("Username from token: " + kayttajanimi);
    
                // If the token is valid, set the authentication context
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(kayttajanimi);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("Token validated successfully");
                } else {
                    // If the token is invalid, respond with 403 Forbidden
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Virheellinen token\"}");
                    System.out.println("Invalid token");
                    return;
                }
            } catch (Exception e) {
                // If there's any exception (like token parsing issues), respond with 401 Unauthorized
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Virheellinen token\"}");
                System.out.println("Token validation failed: " + e.getMessage());
                return;
            }
        //jatka filterchainiä
        filterChain.doFilter(request, response);
    }
}

        
      
