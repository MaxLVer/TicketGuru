package com.melkeinkood.ticket_guru;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import com.melkeinkood.ticket_guru.auth.dto.JwtResponseDTO;
import com.melkeinkood.ticket_guru.auth.dto.LoginRequestDTO;
import com.melkeinkood.ticket_guru.model.dto.LippuDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LippuIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String BASE_URL = "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi";

    private String accessToken;



    @BeforeEach
    public void kirjauduSisaan() {
        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
                .kayttajanimi("kayttaja")
                .salasana("testaaja123")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDTO> loginEntity = new HttpEntity<>(loginRequestDTO, headers);

        ResponseEntity<JwtResponseDTO> vastaus = testRestTemplate.postForEntity(
                BASE_URL + "/kayttajat/kirjaudu",
                loginEntity,
                JwtResponseDTO.class);

        assertEquals(HttpStatus.OK, vastaus.getStatusCode(), "Kirjautuminen epäonnistui");
        assertNotNull(vastaus.getBody(), "Tokenia ei saatu vastauksena");

        accessToken = vastaus.getBody().getAccessToken();
        System.out.println("Access token haettu: " + accessToken);
    }

    @Test
    void testiLuoUusiLippu() {
        
        LippuDTO uusiLippu = new LippuDTO(null, 4L, 4L, 4L, "testi123", null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<LippuDTO> request = new HttpEntity<>(uusiLippu, headers);

        ResponseEntity<LippuDTO> vastaus = testRestTemplate.exchange(
        BASE_URL + "/liput",
        HttpMethod.POST,
        request,
        LippuDTO.class
);

        assertEquals(HttpStatus.CREATED, vastaus.getStatusCode(), "Lipun luonti epäonnistui");
        assertNotNull(vastaus.getBody(), "Vastaus on tyhjä");
        System.out.println("Luotu lippu: " + vastaus.getBody());
    }
    

    @Test
    public void testiHaeLiput() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> vastaus = testRestTemplate.exchange(
                BASE_URL + "/liput", HttpMethod.GET, request, String.class);

        assertEquals(HttpStatus.OK, vastaus.getStatusCode());
        System.out.println("Response: " + vastaus.getBody());
    }
}
