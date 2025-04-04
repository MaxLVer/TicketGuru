package com.melkeinkood.ticket_guru.auth.config;

import com.melkeinkood.ticket_guru.auth.services.UserDetailsServiceImpl;
import com.melkeinkood.ticket_guru.auth.security.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
            .requestMatchers("/kayttajat/luo", "/kayttajat/kirjaudu").permitAll()  // Hyväksy spesifiset endpointit
            .anyRequest().authenticated()  // Vaadi eutentikointia muissa endpointeissa
        )
        .sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Session luonti stateless
        )
        .authenticationProvider(authProvider())  
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)  // Lisää JWT autentikaatio filtteri ennen defaulttia UsernamePasswordAuthenticationFilter
        .build();  
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
    }

    // public SecurityConfig(JwtService jwtService, UserDetailsService userDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
    //     this.jwtService = jwtService;
    //     this.userDetailsService = userDetailsService;
    //     this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    // }

    // @Bean
    // public JwtFilter jwtFilter() {
    //     return new JwtFilter(jwtService, userDetailsService);
    // }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable())
    //         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/kayttajat/luo").permitAll()
    //             .requestMatchers("/kayttajat/kirjaudu").permitAll()
    //             .anyRequest().authenticated() 
    //         )
    //         .exceptionHandling(exceptionHandling -> 
    //             exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
    //         )
    //         .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class); 

    //     //return http.build();
    // }

    
}
