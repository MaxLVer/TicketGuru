package com.melkeinkood.ticket_guru;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.melkeinkood.ticket_guru.model.Asiakastyyppi;
import com.melkeinkood.ticket_guru.repositories.AsiakastyyppiRepository;
import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.model.Lippu;
import com.melkeinkood.ticket_guru.repositories.LippuRepository;
import com.melkeinkood.ticket_guru.model.Ostostapahtuma;
import com.melkeinkood.ticket_guru.repositories.OstostapahtumaRepository;
import com.melkeinkood.ticket_guru.model.Postinumero;
import com.melkeinkood.ticket_guru.repositories.PostinumeroRepository;
import com.melkeinkood.ticket_guru.model.Rooli;
import com.melkeinkood.ticket_guru.repositories.RooliRepository;
import com.melkeinkood.ticket_guru.model.TapahtumaLipputyyppi;
import com.melkeinkood.ticket_guru.repositories.TapahtumaLipputyyppiRepository;
import com.melkeinkood.ticket_guru.model.Tapahtuma;
import com.melkeinkood.ticket_guru.repositories.TapahtumaRepository;
import com.melkeinkood.ticket_guru.model.Tapahtumapaikka;
import com.melkeinkood.ticket_guru.repositories.TapahtumapaikkaRepository;

@SpringBootApplication
public class TicketGuruApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketGuruApplication.class, args);
	}
/*

	@Bean
	public CommandLineRunner tapahtumaData(
			TapahtumaRepository tapahtumaRepository, 
			TapahtumapaikkaRepository tapahtumapaikkaRepository, 
			PostinumeroRepository postinumeroRepository, 
			AsiakastyyppiRepository asiakastyyppiRepository, 
			KayttajaRepository kayttajaRepository, 
			LippuRepository lippuRepository, 
			OstostapahtumaRepository ostostapahtumaRepository, 
			RooliRepository rooliRepository, 
			TapahtumaLipputyyppiRepository tapahtumaLipputyyppiRepository){
		
		return (args) -> {
			//Lisätään testidataa
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

			Asiakastyyppi peruslippu = (new Asiakastyyppi (
				"peruslippu"
			));
			asiakastyyppiRepository.save(peruslippu);

			Asiakastyyppi lastenlippu = (new Asiakastyyppi (
				"lastenlippu"
			));
			asiakastyyppiRepository.save(lastenlippu);

			TapahtumaLipputyyppi lipputyyppi1 = (new TapahtumaLipputyyppi(
				tapahtuma1,
				peruslippu,
				new BigDecimal("10.50")));
			tapahtumaLipputyyppiRepository.save(lipputyyppi1);

			TapahtumaLipputyyppi lipputyyppi2 = (new TapahtumaLipputyyppi(
				tapahtuma1,
				lastenlippu, 
				new BigDecimal("7.50")));
			tapahtumaLipputyyppiRepository.save(lipputyyppi2);

//			peruslippu.getTapahtumaLipputyyppi().add(lipputyyppi1);
//			lastenlippu.getTapahtumaLipputyyppi().add(lipputyyppi2);

			Rooli rooli1 = (new Rooli ("admin", "Ylläpitäjät hallitsevat järjestelmää."));
			rooliRepository.save(rooli1);

			Rooli rooli2 = (new Rooli ("salesperson", "Myyjän tehtävät"));
			rooliRepository.save(rooli2);

			Kayttaja kayttaja1 = (new Kayttaja(rooli1, "test1", "test1234", "Teppo", "Testaaja"));
			kayttajaRepository.save(kayttaja1);

			Ostostapahtuma ostostapahtuma1 = (new Ostostapahtuma(null, kayttaja1));
			ostostapahtumaRepository.save(ostostapahtuma1);

			String koodi = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

			Lippu lippu1 = new Lippu(ostostapahtuma1, lipputyyppi1, tapahtuma1);
			lippu1.setKoodi(koodi);
			lippuRepository.save(lippu1);

			Tapahtuma tapahtuma = tapahtumaRepository.findById(1L).orElseThrow(() -> new RuntimeException("Tapahtuma not found"));
        	Asiakastyyppi vipLippu = new Asiakastyyppi("VIP-lippu");
        	asiakastyyppiRepository.save(vipLippu);

        	TapahtumaLipputyyppi uusiLipputyyppi = new TapahtumaLipputyyppi(
                tapahtuma,
                vipLippu,
                new BigDecimal("50.00")
        	);

        	tapahtumaLipputyyppiRepository.save(uusiLipputyyppi);
		};
	}
		*/
}
