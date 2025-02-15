# TicketGuru

Tiimi: Liisa Davydov, Max Lindqvist, Mikko Vitikka, Ossi Lukkarinen, Marjo Ek

## Johdanto

Projektin tarkoituksena on tuottaa asiakkaan tilaama lipunmyyntijärjestelmä.
 Kyseistä järjestelmää hyödynnetään asiakkaan myyntipisteissä, joissa lipunmyyjä myy ja tulostaa asiakkaalle liput.

Sovelluksen Backend tullaan toteuttamaan Spring Boot projektina, jossa kielenä käytetään Javaa.
 Sovelluksen Frontendissa tullaan hyödyntämään React Javascript-kirjastoa.

Projekti toteutetaan hyödyntämällä Scrum-mentelmää, minkä avulla tiimi pystyy tuottamaan tasasin väliajoin uusia toiminnallisuuksia sovellukseen.
 Viikottaisten sprinttien avulla tiimi pystyy hyvin kartoittamaan työn etenemistä, ja tunnistamaan mahdolliset ongelmakohdat tuotteen kehittämisessä.

## Järjestelmän määrittely

Määrittelyssä järjestelmää tarkastellaan käyttäjän näkökulmasta. Järjestelmän
toiminnot hahmotellaan käyttötapausten tai käyttäjätarinoiden kautta, ja kuvataan järjestelmän
käyttäjäryhmät.

-   Lyhyt kuvaus käyttäjäryhmistä (rooleista)
-   Käyttäjäroolit ja roolien tarvitsemat toiminnot, esim. käyttötapauskaaviona
    (use case diagram) tai käyttäjätarinoina.
-   Lyhyt kuvaus käyttötapauksista tai käyttäjätarinat

Kuvauksissa kannattaa harkita, mikä on toteuttajalle ja asiakkaalle oleellista
tietoa ja keskittyä siihen.

## Käyttöliittymä

Esitetään käyttöliittymän tärkeimmät (vain ne!) näkymät sekä niiden väliset siirtymät käyttöliittymäkaaviona. 

Jos näkymän tarkoitus ei ole itsestään selvä, se pitää kuvata lyhyesti.


### Lipunmyynti

Sivu jolla myyjä pystyy myymään asiakkaalle lippuja ja tulostamaan asiakkaalle liput heti maksun jälkeen. Myyntiin asetetuille lipuilla tulisi lähtökohtaisesti olla perustiedot tallennettuna järjestelmässä.

- Päivämäärä, kellonaika
- Tapahtuman nimi
- Hintaluokat (Aikuiset, lapset . . .)
- Myyntien summa


### Tapahtumien hallinta

Sivu jolla nähdään aikaisemmin luodut tapahtumat. Jokaiselle hallinta toiminnallisuudet (Uuden luonti, muokkaus).

Toiminnallisuudet
- Tapahtumien listaus
- Uuden tapahtuman lisäys
- Tapahtumien muokkaus
- Lipputyyppien hallinnointi (Ikäluokat, alennukset . . .)
- Tapahtumaraportti (siirtää sivulle Myyntiraportti)

### Myyntiraportti

Sivu jolla nähdään tapahtumien kaikki myyntitapahtumat listattuna.

Tapahtumakohtainen raportti jossa näkyy heti alkuun:
- Myydyt liput lipputyyppien mukaan ("Aikuiset", Kpl: . . ., Yhteensä: . . .)
- Totaalinen tuotto lipuista
- Listaus kaikista myyntitapahtumista (Avaa uuden näkymän)

## Tietokanta

Järjestelmään säilöttävä ja siinä käsiteltävät tiedot ja niiden väliset suhteet
kuvataan käsitekaaviolla. Käsitemalliin sisältyy myös taulujen välisten viiteyhteyksien ja avainten
määritykset. Tietokanta kuvataan käyttäen jotain kuvausmenetelmää, joko ER-kaaviota ja UML-luokkakaaviota.

Lisäksi kukin järjestelmän tietoelementti ja sen attribuutit kuvataan
tietohakemistossa. Tietohakemisto tarkoittaa yksinkertaisesti vain jokaisen elementin (taulun) ja niiden
attribuuttien (kentät/sarakkeet) listausta ja lyhyttä kuvausta esim. tähän tyyliin:

>### _Käyttäjä_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> käyttäjä_id | int PK | Käyttäjän id
> käyttäjänimi | LISÄÄ |  Käyttäjän nimi
> salasana | LISÄÄ | Salasana
> etunimi | LISÄÄ | etunimi
> sukunimi | LISÄÄ | Sukunimi
> rooli_id | int FK | Käyttäjäroolin id, viittaus [Roolit](#Roolit)-tauluun

>### _Roolit_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | -------
> rooli_id | int PK | Käyttäjäroolin id
> nimike | LISÄÄ |  Roolin nimi
> rooli_selite | LISÄÄ | Roolin kuvaus

>### _Lipputyypit_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> lipputyyppi_id | int PK | Lipputyypin id
> alennusprosentti | LISÄÄ |  Hinnalle laskettava alennus
> lipputyyppi_selite | LISÄÄ | Lipputyypin kuvaus

>### _Lippu_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> lippu_id | int PK | Lipun id
> lippu_hinta | LISÄÄ |  Yksittäisen lipun hinta
> tunniste | LISÄÄ | Lipuntarkastus tunniste
> voimassaoloaika | LISÄÄ | Lipun voimassaoloaika
> lipputyyppi_id | int FK | Lipputyyppi, viittaus [Lipputyyppi](#Lipputyyppi)-tauluun

>### _Tapahtuma_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> tapahtuma_id | int PK | Tapahtuman id
> paikka_id | int FK |  Tapahtumapaikka, viittaus [Tapahtumapaikka](#Tapahtumapaikka)-tauluun
> tapahtuman aika | Date | Tapahtuman päivämäärä ja kellonaika
> 

>### _Tapahtumapaikka_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> paikka_id | int PK | Tapahtumapaikan id
> tapahtumapaikan_nimi | LISÄÄ |  Tapahtumapaikan nimi
> lähiosoite | LISÄÄ | Paikan lähiosoite
> kaupunki | LISÄÄ | Kaupunki

>### _Ostotapahtuma_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> ostotapahtuma_id | int PK | Ostotapahtuman id
> tapahtuma_id | LISÄÄ |  Tapahtuma, viittaus [Tapahtuma](#Tapahtuma)-tauluun
> myyntiaika | DATE | Oston myyntiaika
> käyttäjä_id | in FK | Käyttäjä viittaus [Käyttäjä](#Käyttäjä)-tauluun

> ### _Ostotapahtuma_Lippu_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Ostotapahtuma_id | int PK | Ostotapahtuman id viittaus [Ostoapahtuma](#Ostoapahtuma)-tauluun
> Lippu_id | int PK |  Lipun id viittaus [Lippu](#Lippu)-tauluun
> Ostotapahtuma_lippu_hinta | LISÄÄ | Kokonaishinta

## Tekninen kuvaus

Teknisessä kuvauksessa esitetään järjestelmän toteutuksen suunnittelussa tehdyt tekniset
ratkaisut, esim.

-   Missä mikäkin järjestelmän komponentti ajetaan (tietokone, palvelinohjelma)
    ja komponenttien väliset yhteydet (vaikkapa tähän tyyliin:
    https://security.ufl.edu/it-workers/risk-assessment/creating-an-information-systemdata-flow-diagram/)
-   Palvelintoteutuksen yleiskuvaus: teknologiat, deployment-ratkaisut yms.
-   Keskeisten rajapintojen kuvaukset, esimerkit REST-rajapinta. Tarvittaessa voidaan rajapinnan käyttöä täsmentää
    UML-sekvenssikaavioilla.
-   Toteutuksen yleisiä ratkaisuja, esim. turvallisuus.

Tämän lisäksi

-   ohjelmakoodin tulee olla kommentoitua
-   luokkien, metodien ja muuttujien tulee olla kuvaavasti nimettyjä ja noudattaa
    johdonmukaisia nimeämiskäytäntöjä
-   ohjelmiston pitää olla organisoitu komponentteihin niin, että turhalta toistolta
    vältytään

## Testaus

Tässä kohdin selvitetään, miten ohjelmiston oikea toiminta varmistetaan
testaamalla projektin aikana: millaisia testauksia tehdään ja missä vaiheessa.
Testauksen tarkemmat sisällöt ja testisuoritusten tulosten raportit kirjataan
erillisiin dokumentteihin.

Tänne kirjataan myös lopuksi järjestelmän tunnetut ongelmat, joita ei ole korjattu.

## Asennustiedot

Järjestelmän asennus on syytä dokumentoida kahdesta näkökulmasta:

-   järjestelmän kehitysympäristö: miten järjestelmän kehitysympäristön saisi
    rakennettua johonkin toiseen koneeseen

-   järjestelmän asentaminen tuotantoympäristöön: miten järjestelmän saisi
    asennettua johonkin uuteen ympäristöön.

Asennusohjeesta tulisi ainakin käydä ilmi, miten käytettävä tietokanta ja
käyttäjät tulee ohjelmistoa asentaessa määritellä (käytettävä tietokanta,
käyttäjätunnus, salasana, tietokannan luonti yms.).

## Käynnistys- ja käyttöohje

Tyypillisesti tässä riittää kertoa ohjelman käynnistykseen tarvittava URL sekä
mahdolliset kirjautumiseen tarvittavat tunnukset. Jos järjestelmän
käynnistämiseen tai käyttöön liittyy joitain muita toimenpiteitä tai toimintajärjestykseen liittyviä asioita, nekin kerrotaan tässä yhteydessä.

Usko tai älä, tulet tarvitsemaan tätä itsekin, kun tauon jälkeen palaat
järjestelmän pariin !
