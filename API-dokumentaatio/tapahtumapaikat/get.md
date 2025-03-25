# Tapahtumapaikan haku

Tämä toiminto listaa kaikki tapahtumapaikat tai hakee yhden.

## Listaa kaikki tapahtumalipputyypit

**URL** : `/tapahtumapaikat/`

---

## Hakee tietyn tapahtuma lipputyypin ID:n avulla

**URL** : `/tapahtumapaikat/{id}`

**URL Parameters** :  
- `id` – Vastaa tietokannassa Tapahtumapaikka-taulun primary keytä.

---

## HTTP-metodi

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TBD  
**Vaatii hyväksyntää** : TBD  

---

## Response

### Onnistunut pyyntö – kaikki tapahtumapaikat

**Koodi** : `200 OK`

```json
{
  "lahiosoite": "STRING",
  "postinumero": {
    "postinumeroId": INT,
    "postinumero": "STRING",
    "kaupunki": "STRING"
  },
  "tapahtumapaikanNimi": "STRING",
  "kapasiteetti": INT,
  "id": INT
}
{
  "lahiosoite": "STRING",
  "postinumero": {
    "postinumeroId": INT,
    "postinumero": "STRING",
    "kaupunki": "STRING"
  },
  "tapahtumapaikanNimi": "STRING",
  "kapasiteetti": INT,
  "id": INT
}
```

## TAI
### Onnistunut pyyntö – yksi

**Koodi** : `200 OK`

```json
{
  "lahiosoite": "STRING",
  "postinumero": {
    "postinumeroId": INT,
    "postinumero": "STRING",
    "kaupunki": "STRING"
  },
  "tapahtumapaikanNimi": "STRING",
  "kapasiteetti": INT,
  "id": INT
}
```

## Virhe Response

**Tila** : Jos tapahtumapaikkaa ei löydy

**Koodi** : `404 NOT FOUND`

## Virhe Response

**Tila** : Jos JSON on virheellinen/puuttellinen.

**Koodi** : `400 BAD REQUEST`
