// package com.melkeinkood.ticket_guru.service;

// import java.util.ArrayList;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
// import com.melkeinkood.ticket_guru.model.Kayttaja;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {
//     @Autowired
//     private KayttajaRepository kayttajaRepository;

//     @Override
//     public UserDetails loadUserByUsername(String kayttajanimi) throws UsernameNotFoundException {
//         Kayttaja kayttaja = kayttajaRepository.findByUsername(kayttajanimi);
//         UserDetails user = new org.springframework.security.core.userdetails.User(kayttajanimi, kayttaja.getSalasana(),
//         new ArrayList<>());
//         return user;
//     }
// }
