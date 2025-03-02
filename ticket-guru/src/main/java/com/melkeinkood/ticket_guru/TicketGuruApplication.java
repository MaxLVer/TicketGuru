package com.melkeinkood.ticket_guru;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;
import com.melkeinkood.ticket_guru.model.Tapahtumapaikka;
import com.melkeinkood.ticket_guru.repositories.TapahtumapaikkaRepository;
import com.melkeinkood.ticket_guru.model.Postinumero;
import com.melkeinkood.ticket_guru.repositories.PostinumeroRepository;

@SpringBootApplication
public class TicketGuruApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketGuruApplication.class, args);
	}

	@Bean
	public CommandLineRunner tapahtumaData(TapahtumaRepository tapahtumaRepository,
			TapahtumapaikkaRepository tapahtumapaikkaRepository, PostinumeroRepository postinumeroRepository) {

		return (args) -> {
			Postinumero testinumero = new Postinumero("00500", "Helsinki");
			postinumeroRepository.save(testinumero);

			Tapahtumapaikka korjaamo = new Tapahtumapaikka("Töölönkatu 51 B", testinumero, "Kulttuuritehdas Korjaamo",
					500);
			tapahtumapaikkaRepository.save(korjaamo);

			Tapahtuma tapahtuma1 = new Tapahtuma(korjaamo,
					LocalDateTime.of(2025, 2, 28, 20, 0),
					"Rock-konsertti",
					"Rokkia Korjaamolla",
					300,
					300);
			tapahtumaRepository.save(tapahtuma1);

			Tapahtuma tapahtuma2 = new Tapahtuma(
				korjaamo,
				LocalDateTime.of(2025, 2, 27, 20, 0),
				"Pop-konsertti",
				"Poppia Korjaamolla",
				300,
				300

			);
			tapahtumaRepository.save(tapahtuma2);

		};
	}
}
