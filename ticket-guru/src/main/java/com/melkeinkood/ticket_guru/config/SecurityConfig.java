// package com.melkeinkood.ticket_guru.config;

// import com.melkeinkood.ticket_guru.auth.services.JwtService;
// import com.melkeinkood.ticket_guru.auth.security.JwtFilter;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// public class SecurityConfig {

//     private final JwtService jwtService;

//     public SecurityConfig(JwtService jwtService) {
//         this.jwtService = jwtService;
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//         return authenticationConfiguration.getAuthenticationManager();
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/auth/**").permitAll() // Allow login and registration endpoints without authentication
//                 .anyRequest().authenticated() // Secure all other endpoints
//             )
//             .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class); // Add the JwtFilter before the default authentication filter

//         return http.build();
//     }
// }
