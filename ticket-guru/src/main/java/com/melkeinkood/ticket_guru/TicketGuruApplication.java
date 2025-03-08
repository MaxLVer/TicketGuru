package com.melkeinkood.ticket_guru;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.melkeinkood.ticket_guru.model.Asiakastyypit;
import com.melkeinkood.ticket_guru.repositories.AsiakastyypitRepository;
import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.model.Lippu;
import com.melkeinkood.ticket_guru.model.LippuStatus;
import com.melkeinkood.ticket_guru.repositories.LippuRepository;
import com.melkeinkood.ticket_guru.model.Ostostapahtuma;
import com.melkeinkood.ticket_guru.repositories.OstostapahtumaRepository;
import com.melkeinkood.ticket_guru.model.Postinumero;
import com.melkeinkood.ticket_guru.repositories.PostinumeroRepository;
import com.melkeinkood.ticket_guru.model.Rooli;
import com.melkeinkood.ticket_guru.repositories.RooliRepository;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyypit;
import com.melkeinkood.ticket_guru.repositories.TapahtumaLipputyypitRepository;
import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;
import com.melkeinkood.ticket_guru.model.Tapahtumapaikka;
import com.melkeinkood.ticket_guru.repositories.TapahtumapaikkaRepository;

@SpringBootApplication
public class TicketGuruApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketGuruApplication.class, args);
	}

	@Bean
	public CommandLineRunner tapahtumaData(TapahtumaRepository tapahtumaRepository, TapahtumapaikkaRepository tapahtumapaikkaRepository, PostinumeroRepository postinumeroRepository, AsiakastyypitRepository asiakastyypitRepository, KayttajaRepository kayttajaRepository, LippuRepository lippuRepository, OstostapahtumaRepository ostostapahtumaRepository, RooliRepository rooliRepository, TapahtumaLipputyypitRepository tapahtumaLipputyypitRepository){
		
		return (args) -> {
			Postinumero p00250 = new Postinumero("00250", "Helsinki");
			postinumeroRepository.save(p00250);

			Tapahtumapaikka korjaamo = new Tapahtumapaikka("Töölönkatu 51 B", p00250, "Kulttuuritehdas Korjaamo", 1000);
			tapahtumapaikkaRepository.save(korjaamo);

			Tapahtuma tapahtuma1 = new Tapahtuma(
				korjaamo,
				LocalDateTime.of(2025, 2, 28, 20, 0),
				"Rock-konsertti",
				"Iloinen rock-tapahtuma",
				500,
				500
			);
			tapahtumaRepository.save(tapahtuma1);	

			Tapahtuma tapahtuma2 = (new Tapahtuma(
				korjaamo,
				LocalDateTime.of(2025, 2, 27, 20, 0),
				"Pop-konsertti",
				"Hurja pop-tapahtuma",
				200,
				200
			));
			tapahtumaRepository.save(tapahtuma2);

			Asiakastyypit peruslippu = (new Asiakastyypit (
				"peruslippu"
			));
			asiakastyypitRepository.save(peruslippu);

			Asiakastyypit lastenlippu = (new Asiakastyypit (
				"lastenlippu"
			));
			asiakastyypitRepository.save(lastenlippu);

			TapahtumaLipputyypit lipputyyppi1 = (new TapahtumaLipputyypit(
				tapahtuma1,
				peruslippu,
				new BigDecimal("10.50")));
			tapahtumaLipputyypitRepository.save(lipputyyppi1);

			TapahtumaLipputyypit lipputyyppi2 = (new TapahtumaLipputyypit(
				tapahtuma1,
				lastenlippu, 
				new BigDecimal("7.50")));
			tapahtumaLipputyypitRepository.save(lipputyyppi2);

//			peruslippu.getTapahtumaLipputyypit().add(lipputyyppi1);
//			lastenlippu.getTapahtumaLipputyypit().add(lipputyyppi2);

			Rooli rooli1 = (new Rooli ("yllapito", "Ylläpitäjät hallitsevat järjestelmää."));
			rooliRepository.save(rooli1);

			Kayttaja kayttaja1 = (new Kayttaja(rooli1, "test1", "test1234", "Teppo", "Testaaja"));
			kayttajaRepository.save(kayttaja1);

			Ostostapahtuma ostostapahtuma1 = (new Ostostapahtuma(kayttaja1, null));
			ostostapahtumaRepository.save(ostostapahtuma1);

			Lippu lippu1 = (new Lippu("xxx", ostostapahtuma1, lipputyyppi1, LocalDateTime.of(2025, 6, 1, 20, 0), LippuStatus.MYYTAVANA, tapahtuma1));
			lippuRepository.save(lippu1);

		};
	}
}
