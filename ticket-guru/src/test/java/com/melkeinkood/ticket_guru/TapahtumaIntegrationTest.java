package com.melkeinkood.ticket_guru;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;

import com.melkeinkood.ticket_guru.auth.dto.JwtResponseDTO;
import com.melkeinkood.ticket_guru.auth.dto.LoginRequestDTO;
import com.melkeinkood.ticket_guru.model.dto.TapahtumaDTO;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TapahtumaIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String BASE_URL = "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi";

    private String accessToken;

    @BeforeEach
    //kirjaudu sisään
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
   void testLisaaTapahtuma() {
    //luodaan uusi tapahtuma
    TapahtumaDTO uusiTapahtuma = new TapahtumaDTO(
        null,
        2L, // Käytä ID:tä joka on olemassa
        LocalDateTime.now().plusDays(5),
        "Testikeikka",
        "Testitapatuman kuvaus",
        100,
        100
    );


    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(accessToken);

    HttpEntity<TapahtumaDTO> request = new HttpEntity<>(uusiTapahtuma, headers);

    ResponseEntity<String> response = testRestTemplate.postForEntity(
        BASE_URL + "/tapahtumat",
        request,
        String.class
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    System.out.println("Lisätty tapahtuma: " + response.getBody());

    System.out.println("Vastaus (status): " + response.getStatusCode());
System.out.println("Virheviesti: " + response.getBody());
}

@Test
void testHaeKaikkiTapahtumat() {
    //Hae kaikki tapahtumat
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<Void> request = new HttpEntity<>(headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        BASE_URL + "/tapahtumat",
        HttpMethod.GET,
        request,
        String.class
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    System.out.println("Kaikki tapahtumat: " + response.getBody());
}
@Test
void testHaeTapahtumaIdlla() {
    Long tapahtumaId = 2L; // käytä olemassaolevaa ID:tä

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<Void> request = new HttpEntity<>(headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        BASE_URL + "/tapahtumat/" + tapahtumaId,
        HttpMethod.GET,
        request,
        String.class
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    System.out.println("Haettu tapahtuma: " + response.getBody());
}

@Test
void testPoistaTapahtuma() {
    // Ensin luodaan uusi tapahtuma
    TapahtumaDTO poistettavaTapahtuma = new TapahtumaDTO(
        null,
        3L,
        LocalDateTime.now().plusDays(10),
        "Poistettava keikka",
        "Tämä tapahtuma poistetaan testissä",
        50,
        50
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(accessToken);

    HttpEntity<TapahtumaDTO> postRequest = new HttpEntity<>(poistettavaTapahtuma, headers);
    ResponseEntity<TapahtumaDTO> postResponse = testRestTemplate.postForEntity(
        BASE_URL + "/tapahtumat",
        postRequest,
        TapahtumaDTO.class
    );

    assertEquals(HttpStatus.CREATED, postResponse.getStatusCode(), "Tapahtuman luonti epäonnistui");  // <-- lisätty tarkistus
    assertNotNull(postResponse.getBody(), "Luotu tapahtuma on null");  // <-- lisätty tarkistus

    Long tapahtumaId = postResponse.getBody().getTapahtumaId();
    assertNotNull(tapahtumaId, "Luodun tapahtuman ID on null");  // <-- varmistetaan että ID saatiin

    // Sitten poistetaan
    HttpHeaders deleteHeaders = new HttpHeaders();
    deleteHeaders.setBearerAuth(accessToken);

    HttpEntity<Void> deleteRequest = new HttpEntity<>(deleteHeaders);

    ResponseEntity<Void> deleteResponse = testRestTemplate.exchange(
        BASE_URL + "/tapahtumat/" + tapahtumaId,
        HttpMethod.DELETE,
        deleteRequest,
        Void.class
    );

    assertTrue(
    deleteResponse.getStatusCode() == HttpStatus.NO_CONTENT ||
    deleteResponse.getStatusCode() == HttpStatus.OK,
    "Tapahtuman poisto epäonnistui. Status: " + deleteResponse.getStatusCode()
);
}


}
