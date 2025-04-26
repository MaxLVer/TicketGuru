package com.melkeinkood.ticket_guru;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import org.springframework.http.MediaType;

import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.model.Lippu;
import com.melkeinkood.ticket_guru.model.Ostostapahtuma;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.repositories.LippuRepository;
import com.melkeinkood.ticket_guru.repositories.OstostapahtumaRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OstostapahtumaControllerTest {
     @Autowired
    private MockMvc mockMvc;

    @MockBean
   private OstostapahtumaRepository ostostapahtumaRepository;

    @MockBean
    private KayttajaRepository kayttajaRepository;

    @MockBean
    private LippuRepository lippuRepository;


    

    @Test
    @WithMockUser(authorities = {"ADMIN"}) // Tämä lisää "feikki-käyttäjän" testille
    void testHaeKaikkiOstostapahtumat_Returns200AndList() throws Exception {
    // Mockataan yksi ostostapahtuma
    Ostostapahtuma tapahtuma = new Ostostapahtuma();
    tapahtuma.setOstostapahtumaId(1L);
    tapahtuma.setMyyntiaika(LocalDateTime.now());
    Kayttaja kayttaja = new Kayttaja();
    kayttaja.setKayttajaId(1L);
    tapahtuma.setKayttaja(kayttaja);

    when(ostostapahtumaRepository.findAll()).thenReturn(List.of(tapahtuma));

    mockMvc.perform(get("/ostostapahtumat"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].ostostapahtumaId").value(1L));
}

@Test
@WithMockUser(authorities = {"ADMIN"}) 
void testHaeOstostapahtuma_EiLoydy_Returns404() throws Exception {
    when(ostostapahtumaRepository.findById(99L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/ostostapahtumat/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Ostostapahtumaa ei löytynyt"));
}

@Test
@WithMockUser(authorities = {"ADMIN"}) 
void testLisaaOstostapahtuma_Onnistuu_Returns201() throws Exception {
    Kayttaja kayttaja = new Kayttaja();
    kayttaja.setKayttajaId(1L);

    when(kayttajaRepository.findByKayttajaId(1L)).thenReturn(Optional.of(kayttaja));

    Ostostapahtuma uusiOstostapahtuma = new Ostostapahtuma();
    uusiOstostapahtuma.setOstostapahtumaId(1L);
    uusiOstostapahtuma.setMyyntiaika(LocalDateTime.now());
    uusiOstostapahtuma.setKayttaja(kayttaja);

    when(ostostapahtumaRepository.save(any(Ostostapahtuma.class))).thenReturn(uusiOstostapahtuma);

    String ostostapahtumaJson = """
        {
            "myyntiaika": "2024-04-26T12:00:00",
            "kayttajaId": 1,
            "lippuIdt": []
        }
    """;

    mockMvc.perform(post("/ostostapahtumat")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ostostapahtumaJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.kayttajaId").value(1));
}


}
