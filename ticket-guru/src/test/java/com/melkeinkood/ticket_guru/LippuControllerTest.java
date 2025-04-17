package com.melkeinkood.ticket_guru;

import com.melkeinkood.ticket_guru.web.LippuController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LippuControllerTest {

    @Test
    void generoiSatunnainenLippuKoodi_palauttaa8Merkkiä() {
        LippuController controller = new LippuController();
        String koodi = controller.generoiSatunnainenLippuKoodi();
        assertEquals(8, koodi.length(), "Koodin pituuden tulee olla 8 merkkiä");
    }

    @Test
    void generoiSatunnainenLippuKoodi_palauttaaEriKoodinJokaKerta() {
        LippuController controller = new LippuController();
        String koodi1 = controller.generoiSatunnainenLippuKoodi();
        String koodi2 = controller.generoiSatunnainenLippuKoodi();
        assertNotEquals(koodi1, koodi2, "Koodien tulisi olla erilaisia");
    }

    @Test
    void generoiSatunnainenLippuKoodi_sisältääVainKirjaimiaJaNumeroita() {
        LippuController controller = new LippuController();
        String koodi = controller.generoiSatunnainenLippuKoodi();
        assertTrue(koodi.matches("[A-Z0-9]{8}"), "Koodin tulisi sisältää vain isoja kirjaimia ja numeroita");
    }

    
}
