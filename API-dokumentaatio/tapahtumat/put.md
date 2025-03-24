# Muokkaa tapahtumaa

Toiminnon avulla voidaan muokata yhtä tietokannassa olevaa tapahtumaa


**URL** : `/tapahtumat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtumat-taulun primary keytä.

**Metodi**: `PUT`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

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

## Virhe Response

**Tila** : Tapahtumaa ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`

