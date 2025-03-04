# TicketGuru

Tiimi: Liisa Davydov, Max Lindqvist, Mikko Vitikka, Ossi Lukkarinen, Marjo Ek

## Johdanto

Projektin tarkoituksena on tuottaa asiakkaan tilaama lipunmyyntijärjestelmä.
 Kyseistä järjestelmää hyödynnetään asiakkaan myyntipisteissä, joissa lipunmyyjä myy ja tulostaa asiakkaalle liput.

Projekti toteutetaan hyödyntämällä Scrum-mentelmää, minkä avulla tiimi pystyy tuottamaan tasasin väliajoin uusia toiminnallisuuksia sovellukseen.
 Viikottaisten sprinttien avulla tiimi pystyy hyvin kartoittamaan työn etenemistä, ja tunnistamaan mahdolliset ongelmakohdat tuotteen kehittämisessä.

## Järjestelmän määrittely

### Käytettävät teknologiat
- Frontend:React
- Backend: Spring Boot
- Tietokanat: PostreSQL/MySql
- Päälaitteet: Tietokone, tabletti, älypuhelin

### Järjestelmän keskeiset käyttäjät
-   Lipunmyyjä
-   Tapahtuman järjestäjä
-   Järjestelmävalvoja

### Järjestelmän perustoiminnot
-   Tapahtumien hallinta
-   Lippujen myynti ja tulostus
-   Myyntiraporttien tarkastelu

![User case diagram](./kuvat/user_case_diagram.jpg)

## Käyttöliittymä

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

![tietokantakaavio](./kuvat/tietokantakaavio.jpg)

> ### _Käyttäjät_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> käyttäjä_id | int PK | Käyttäjän id
> käyttäjänimi | varchar(50) |  Käyttäjän nimi
> salasana | varchar(50) | Salasana
> etunimi | varchar(30) | Etunimi
> sukunimi | varchar(50) | Sukunimi
> rooli_id | int FK | Rooli, viittaus [Roolit](#Roolit)-tauluun

> ### _Roolit_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> rooli_id | int PK | Roolin id
> nimike | varchar(50) |  Roolin nimi
> rooli_selite | varchar(100) | Roolin kuvaus


> ### _Tapahtumat_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> tapahtuma_id | int PK | Tapahtuman id
> paikka_id | int FK |  Tapahtumapaikka, viittaus [Tapahtumapaikat](#Tapahtumapaikat)-tauluun
> tapahtuma_aika | Date | Tapahtuman päivämäärä ja kellonaika
> tapahtuma_nimi | varchar(50) | Tapahtuman nimi

> ### _Tapahtumapaikat_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> paikka_id | int PK | Tapahtumapaikan id
> lahiosoite | varchar(100) |  Tapahtumapaikan lähiosoite
> kaupunki | varchar(50) | Tapahtumapaikan kaupunki
> tapahtumapaikan_nimi | varchar (50) | Tapahtumapaikan nimi

> ### _Ostostapahtumat_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> ostostapahtuma_id | int PK | Ostotapahtuman id
> tapahtuma_id | int FK |  Tapahtuma, viittaus [Tapahtumat](#Tapahtumat)-tauluun
> myyntiaika | Date | Ostotapahtuman aika
> kayttaja_id | int FK | Käyttäjä, viittaus [Käyttäjät](#Käyttäjät)-tauluun

> ### _Tapahtuma_Lipputyypit_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> tapahtuma_lipputyyppi_id | int PK | Tapahtuma lipputyypin id
> tapahtuma_id | int PK |  Tapahtuma, viittaus [Tapahtumat](#Tapahtumat)-tauluun
> asiakastyyppi | varchar(30) | Asiakastyyppi, viittaus [Asiakastyypit](#Asiakastyypit)-tauluun
> hinta | DECIMAL(10,2) | hinta

> ### _Liput_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> lippu_id | int PK | Lipun id
> ostostapahtuma_id | int fk |  Ostostapahtuma, viittaus [Ostostapahtumat](#Ostostapahtumat)-tauluun
> tunniste | varchar(20) | Lipuntarkastus tunniste
> voimassaoloaika | Date | Lipun voimassaoloaika
> tapahtuma_lipputyyppi_id | int FK | Tapahtuma Lipputyyppi, viittaus [Tapahtuma Lipputyyppit](#Tapahtuma_Lipputyypit)-tauluun
> status | varchar(10) | Lipun status (myytävänä, myyty, myytävänä ovella)

> ### _Asiakastyypit_
> 
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> asiakastyyppi_id | int PK | Asiakastyypin id
> asiakastyyppi | varchar(20) |  Asikastyypin selite


## API-dokumentaatio
Kaikki endpointit on kuvattu erillisessä dokumentaatiossa:
- [API-dokumentaatio](API-dokumentaatio/README.md)

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
