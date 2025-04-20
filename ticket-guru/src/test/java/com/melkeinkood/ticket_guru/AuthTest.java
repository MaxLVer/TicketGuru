package com.melkeinkood.ticket_guru;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.melkeinkood.ticket_guru.auth.dto.JwtResponseDTO;
import com.melkeinkood.ticket_guru.auth.dto.LoginRequestDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void kirjatuminenPalauttaaJwtJaCookie() {
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .kayttajanimi("kayttaja")
                .salasana("testaaja123")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDTO> requestEntity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<JwtResponseDTO> response = testRestTemplate.postForEntity(
                "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/kayttajat/kirjaudu",
                requestEntity,
                JwtResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JwtResponseDTO jwt = response.getBody();
        assertNotNull(jwt);
        assertNotNull(jwt.getAccessToken(), "Access token puuttuu");
        assertNotNull(jwt.getToken(), "Refresh token puuttuu");

        List<String> cookies = response.getHeaders().get("Set-Cookie");
        assertTrue(
            cookies.stream().anyMatch(cookie -> cookie.startsWith("accessToken")), "AccesToken puuttuu"
        );
    }

}
