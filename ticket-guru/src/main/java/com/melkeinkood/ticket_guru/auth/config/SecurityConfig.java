// package com.melkeinkood.ticket_guru.auth.config;

// import com.melkeinkood.ticket_guru.auth.services.JwtService;
// import com.melkeinkood.ticket_guru.auth.security.JwtFilter;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.ProviderManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// public class SecurityConfig {

//     private final JwtService jwtService;
//     private final UserDetailsService userDetailsService;

//     public SecurityConfig(JwtService jwtService, UserDetailsService userDetailsService) {
//         this.jwtService = jwtService;
//         this.userDetailsService = userDetailsService;
//     }

//     @Bean
//     public JwtFilter jwtFilter() {
//         return new JwtFilter(jwtService, userDetailsService);
//     }

//     @Bean
//     public AuthenticationManager authenticationManager() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//         authProvider.setUserDetailsService(userDetailsService);
//         authProvider.setPasswordEncoder(passwordEncoder());
//         return new ProviderManager(authProvider);
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/auth/**").permitAll()
//                 .anyRequest().authenticated() 
//             )
//             .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class); 

//         return http.build();
//     }
// }
