package com.melkeinkood.ticket_guru;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.melkeinkood.ticket_guru.auth.dto.JwtResponseDTO;
import com.melkeinkood.ticket_guru.auth.dto.LoginRequestDTO;
import com.melkeinkood.ticket_guru.model.dto.LippuDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketGuruApplicationTests {

	
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("server.port", () -> 5173);
	}


	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
	}


	@Test
	public void testiTeeLippu() {
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO("test1", "test12345678");
		ResponseEntity<JwtResponseDTO> response = testRestTemplate.postForEntity("https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/kayttajat/kirjaudu", loginRequestDTO, JwtResponseDTO.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String token = response.getBody().getAccessToken(); 
		String url = "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/Liput";
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		LippuDTO lippuDTO = new LippuDTO(null, 4L, 4L, 4L, "testi123", null);
		HttpEntity<LippuDTO> request = new HttpEntity<>(lippuDTO, headers);
		System.out.println(request);

		ResponseEntity<LippuDTO> vaste = testRestTemplate.postForEntity(url, request, LippuDTO.class);
		assertEquals(HttpStatus.CREATED, vaste.getStatusCode());
	}
}
