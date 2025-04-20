package com.melkeinkood.ticket_guru;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.model.dto.KayttajaDTO;
import com.melkeinkood.ticket_guru.model.dto.LippuDTO;

@SpringBootTest
class TicketGuruApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
	}


	@Test
	public void testiKirjaudu(){
		String url = "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/kayttajat/kirjaudu";
		KayttajaDTO kayttajaDTO = new KayttajaDTO(null, null, "test1", "test12345678", null, null);
		ResponseEntity<ResponseEntity> response = restTemplate.postForEntity(url, kayttajaDTO, ResponseEntity.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testiTeeLippu() {
		String token = "aktiivinen token"; // tähän tulisi oikea aktiivinen token
		String url = "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/Liput";
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		LippuDTO lippuDTO = new LippuDTO(null, 1L, 1L, 1L, "testi123", null);
		HttpEntity<LippuDTO> request = new HttpEntity<>(lippuDTO, headers);

		ResponseEntity<ResponseEntity> response = restTemplate.postForEntity(url, request, ResponseEntity.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
}
