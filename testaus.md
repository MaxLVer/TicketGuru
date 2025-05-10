# Testisuunnitelma: 

## Testauksen tavoite

Testauksen tavoitteena varmistaa, että ohjelmisto toimii oikein. Tuotetulla käyttöliittymällä tulisi olla mahdollista myydä, muokata ja tarkistaa lippuja sekä lisätä tapahtumia ja muokata niiden tietoja. Järjestelmään pitää myös pystyä kirjatumaan tietoturvallisesti tunnuksilla

## Testit

 **Testattava asia**                | **Testin suoritus**
 ---------------------------------- | -------------------
*\<Varmistaa, että automaattisesti generoitu lippukoodi täyttää ehdot: Pituus 8 merkkiä, jokainen koodi on yksilöllinen ja koodi koostuu vain isoista kirjaimista ja numeroista. \>* | *\<ohjeet, miten testaus suoritetaan: Suoritetaan testi LippuControllerTest.java käyttämällä Visual Studio Codea. \>*
*\<Onnistunut kirjautuminen palauttaa access-tokenin ja refresh-tokenin>* | *\<Suoritetaan testi AuthTest.java käyttämällä Visual Studio Codea. Testi lähettää kirjautumispyynnön tunnuksilla ja tarkisetaan, että HTTP-status on 200 OK ja palautettu JSON sisältää access token ja token kentät sekä otsikossa on accessToken-cookie.\>*
*\<Varmistaa, että kaikkien käyttäjien haku, köyttäjien haku id:llä sekä käyttäjän poistaminen id:llä onnistuu* | *\<Suoritetaan testi kayttajaControllerTest.java käyttämällä Visual Studio Codea. Mockauksen avulla tarkisetaan, että GET/kayttajat palauttaa HTTP200 OK ja JSON on taulukkomuotinen. Mockataan tietty käyttäjä KayttajaRepository.findById8(id) -kutsuun ja tarkisetaan että GET/kayttajat/{id} palauttaa HTTP 200 OK ja, että JSON sisältää oikean käyttäjän id:n. Määritetään mockilla käyttäjä ID:llä ja että existsById(id) palauttaa true. Suoritetaan DELETE /kayttajat/{id} ja tarkistetaan että status on 204 No Content. Varmistetaan myös että deleteById kutsutaan.>* 
*\<Varmistaa, että kirjautuminen, uuden linpun luominen, lippujen hakeminen ja testissä luodun lipun poisto onnistuu* | *\<Suoritetaan testi lippuIntegratioTest.java käyttämällä Visual Studio Codea. @BeforeEach-metodissa suoritetaan POST-pyyntö /kayttajat/kirjaudu käyttäjällä kayttaja ja salasanalla testaaja123. Tarkistetaan, että vastaus on 200 OK, ja JWT access token saadaan talteen jatkotesteihin. Suoritetaan testiLuoUusiLippu. Lähetetään POST /liput pyyntö LippuDTO:lla ja Authorization-headerissa Bearer token. Tarkistetaan, että vastaus on 201 Created ja että vastausrungossa on tietoa. Suoritetaan testiHaeLiput. Lähetetään GET /liput pyyntö JWT-tokenilla. Tarkistetaan, että vastaus on 200 OK ja tulostetaan vastausrungon sisältö. @AfterEach-metodissa haetaan testilippu GET /liput?koodi=testauslipukekoodi12345. Jos löytyy, poistetaan se DELETE /liput/{id} -pyynnöllä. Tulostetaan poiston status.>*
*\<Varmistaa, että kaikkien ostostapahtumien haku onnistuu, yksittäisen ostostapahtuman haku epäonnistuu jos tapahtumaa ei ole ja uuden ostostapahtuman luominen onnistuu* | *\<Suoritetaan testi OstostapahtumaControllerTest.java käyttämällä Visual Studio Codea. testHaeKaikkiOstostapahtumat_Returns200AndList-testissä mockataan yksi ostostapahtuma. Suoritetaan GET /ostostapahtumat ja odotetaan 200 OK. Varmistetaan, että JSON-vastauksessa ensimmäisen objektin ostostapahtumaId on 1. testHaeOstostapahtuma_EiLoydy_Returns404-testissä simuloidaan tilanne, jossa findById palauttaa tyhjän. Suoritetaan GET /ostostapahtumat/99. Varmistetaan, että status on 404 Not Found ja virheviesti näytetään. testLisaaOstostapahtuma_Onnistuu_Returns201-testissä mockataan käyttäjä ja tallennettava ostostapahtuma. Suoritetaan POST /ostostapahtumat JSON-rungolla, jossa annetaan myyntiaika, käyttäjä-ID ja tyhjä lippulista. Odotetaan 201 Created ja varmistetaan, että vastausrungossa kayttajaId on 1.>*
*\<Varmistaa, että kirjautuminen onnistuu ja JWT-token saadaan, uuden tapahtuman luonti onnistuu , kaikkien tapahtumien haku onnistuu, tapahtuman haku id:llä onnistuu sekä tapahtuman poistaminen onnistuu* | *\<Suoritetaan testi TapahtumaIntegrationTest.java käyttämällä Visual Studio Codea. @BeforeEach kirjauduSisaan-metodi suorittaa POST /kayttajat/kirjaudu -pyynnön käyttäjätunnuksella kayttaja/testaaja123. Varmistetaan, että status on 200 OK ja JWT-token ei ole null. testLisaaTapahtuma-testissä luodaan uusi tapahtuma POST /tapahtumat -pyynnöllä. Varmistetaan 201 Created ja tulostetaan vastauksen runko. testHaeKaikkiTapahtumat-testissä tehdään GET /tapahtumat -kutsu JWT-tokenin kanssa. Varmistetaan, että status on 200 OK ja vastauksena tulee JSON-lista tapahtumista. testHaeTapahtumaIdlla-testissä tehdään GET /tapahtumat/{id} -kutsu olemassa olevalla ID:llä (2L). Varmistetaan 200 OK ja tulostetaan tapahtuman tiedot. testPoistaTapahtuma-testissä ensin luodaan uusi tapahtuma ja sen jälkeen poistetaan se DELETE /tapahtumat/{id} -kutsulla. Varmistetaan, että poiston HTTP-status on 204 No Content tai 200 OK.>*

Testiloki 10.5.2025
=======================

**Testaaja:Marjo Ek

**Ympäristö:** Visual Studio Code, Cypress

Löydökset: Kaikki testit toimivat 
----------

*\<Tämä osa dokumenttia liittyy testauksen suoritukseen. Listaa tähän löytämäsi
ongelmat ja muut kommenttisi\>*
