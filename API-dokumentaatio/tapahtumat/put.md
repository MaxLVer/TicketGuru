# Muokkaa tapahtumaa

Toiminnon avulla voidaan muokata yhtä tietokannassa olevaa tapahtumaa


**URL** : `/tapahtumat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtumat-taulun primary keytä.

**Metodi**: `PUT`

**Vaatii tunnistautumisen** : TRUE

**Data ehdot**

- Joko tapahtumapaikan id:n, tapahtumapaikan nimi, tapahtumaaika, tapahtuman nimi, kuvaus, kokonaislippumäärän muokkaaminen.

```json
{
  "tapahtumapaikkaId": INT
}
```
tai
```json
{
  "tapahtumaAika": DATE
}
```
tai
```json
{
  "tapahtumaNimi": STRING
}
```
tai
```json
{
  "kuvaus": STRING
}
```
tai
```json
{
  "kokonaislippumäärä": INT
}
```

## Onnistunut response

**Tila** : Tapahtuma löytyy ja sitä on muokattu onnistuneesti

**Koodi** : `200 OK`

**Sisältö esimerkki**
```json
{
    "tapahtumaId": INTEGER,
    "tapahtumapaikkaId": INTEGER,
    "tapahtumaAika": DATETIME,
    "tapahtumaNimi": STRING,
    "kuvaus": STRING,
    "kokonaislippumaara": INTEGER,
    "jaljellaOlevaLippumaara": INTEGER,
    "_links": {
        "self": {
            "href": "http://localhost:8080/tapahtumat/1"
        },
        "tapahtumapaikka": {
            "href": "http://localhost:8080/tapahtumapaikat/1"
        }
    }
}
```

## Virhe Response

**Tila** : Tapahtumaa ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`

