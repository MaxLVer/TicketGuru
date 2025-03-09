# Muokkaa tapahtumaa

Toiminnon avulla voidaan muokata yhtä tietokannassa olevaa tapahtumaa


**URL** : `/tapahtumat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtumat-taulun primary keytä.

---

**Metodi**: `PUT`

**Data ehdot**

-

## Onnistunut response

**Tila** : Tietue löytyy ja sitä on muokattu onnistuneesti

**Koodi** : `200 OK`

**Sisältö** :
```json
{
  "tapahtumapaikka": {
    "lahiosoite": string,
    "postinumero": {
      "postinumero": string,
      "kaupunki": string
    },
    "tapahtumapaikanNimi": string,
    "kapasiteetti": int,
    "id": 1
  },
  "tapahtumaAika": date,
  "tapahtumaNimi": string,
  "kuvaus": string,
  "kokonaislippumaara": int,
  "jaljellaOlevaLippumaara": int,
  "id": 3,
  "tapahtumatLipputyyppi": []
}
```

## TAI

**Tila** : Tapahtumaa ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö** : `{}`
