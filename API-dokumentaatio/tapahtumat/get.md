# Tapahtumien/Tapahtuman haku

Toiminto listaa kaikki tapahtumat tai näyttää yhden tapahtuman

**Listaa kaikki tapahtumat**

**URL** : `/tapahtumat/` 

---

**Hakee Tapahtumat-taulun ID:n avulla tietyn tapahtuman**

**URL** : `/tapahtumat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtumat-taulun primary keytä.

---

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**

-

## Onnistunut response

**Tila** : Yksi tai useampi tapahtuma on näkyvissä käyttäjälle

**Koodi** : `200 OK`

**Sisältö** : 
```json
{
  "tapahtumapaikka": {
    "lahiosoite": STRING,
    "postinumero": {
      "postinumero": STRING,
      "kaupunki": STRING
    },
    "tapahtumapaikanNimi": STRING,
    "kapasiteetti": INTEGER,
    "id": INTEGER
  },
  "tapahtumaAika": DATETIME,
  "tapahtumaNimi": STRING,
  "kuvaus": STRING,
  "kokonaislippumaara": INTEGER,
  "jaljellaOlevaLippumaara": INTEGER,
  "id": INTEGER,
  "tapahtumatLipputyyppi": []
}
```

## TAI

**Tila** : Tapahtumalle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`
