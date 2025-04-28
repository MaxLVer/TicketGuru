package com.melkeinkood.ticket_guru;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


import com.melkeinkood.ticket_guru.auth.services.JwtService;
import com.melkeinkood.ticket_guru.auth.services.RefreshTokenService;
import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.model.Rooli;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.repositories.RooliRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class KayttajaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KayttajaRepository kayttajaRepository;

    @MockBean
    private RooliRepository rooliRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private RefreshTokenService refreshTokenService;
    

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testHaeKaikkiKayttajat() throws Exception {
        //Testaa kaikkien käyttäjien hakemista
        Rooli rooli = new Rooli();
        rooli.setRooliId(1L);
        rooli.setNimike("ADMIN");
        rooli.setRooliSelite("Testaaja");

        Kayttaja kayttaja = new Kayttaja();
        kayttaja.setKayttajaId(1L);
        kayttaja.setKayttajanimi("testikäyttäjä");
        kayttaja.setSalasana("salasana");
        kayttaja.setRooli(rooli); 
        kayttaja.setEtunimi("Testi");
        kayttaja.setSukunimi("Testaaja");
        when(kayttajaRepository.findAll()).thenReturn(List.of(kayttaja));
    
        mockMvc.perform(get("/kayttajat"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }


    @Test
    @WithMockUser(authorities = "ADMIN")
    void testHaeKayttajaById() throws Exception {
        //Testaa yhden käyttäjän hakemista id:llä
        Long kayttajaId = 1L;
        Rooli rooli = new Rooli();
        rooli.setRooliId(1L);
        rooli.setNimike("ADMIN");
        rooli.setRooliSelite("Testaaja");

        Kayttaja kayttaja = new Kayttaja();
        kayttaja.setKayttajaId(1L);
        kayttaja.setKayttajanimi("testikäyttäjä");
        kayttaja.setSalasana("salasana");
        kayttaja.setRooli(rooli); 
        kayttaja.setEtunimi("Testi");
        kayttaja.setSukunimi("Testaaja");
    
        when(kayttajaRepository.findById(kayttajaId)).thenReturn(Optional.of(kayttaja));
    
        mockMvc.perform(get("/kayttajat/{id}", kayttajaId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.kayttajaId").value(kayttajaId));
    }

@Test
@WithMockUser(authorities = "ADMIN")
void testPoistaKayttaja() throws Exception {
    //Testaa yhden käyttäjän poistamista id:llä
    Long kayttajaId = 1L;

    Rooli rooli = new Rooli();
    rooli.setRooliId(1L);
    rooli.setNimike("ADMIN");
    rooli.setRooliSelite("Testaaja");

    Kayttaja kayttaja = new Kayttaja();
    kayttaja.setKayttajaId(1L);
    kayttaja.setKayttajanimi("testikäyttäjä");
    kayttaja.setSalasana("salasana");
    kayttaja.setRooli(rooli); 
    kayttaja.setEtunimi("Testi");
    kayttaja.setSukunimi("Testaaja");

    when(kayttajaRepository.findById(kayttajaId)).thenReturn(Optional.of(kayttaja));
    when(kayttajaRepository.existsById(kayttajaId)).thenReturn(true); 

    mockMvc.perform(delete("/kayttajat/{id}", kayttajaId))
        .andExpect(status().isNoContent());

    verify(kayttajaRepository).deleteById(kayttajaId);
}

    
}
