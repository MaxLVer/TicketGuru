# Uuden tapahtuman lisääminen

Toiminto lisää uuden tapahtuman.

**URL** : `/tapahtumat/`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää tapahtuman nimi, aika ja kokonaislippumäärä
```json
{
  "tapahtumaId": 1,
  "tapahtumaNimi": "[100 chars max]",
  "tapahtumaAika": "[YYYY-MM-DDTHH:MM:SS]",
  "kuvaus": "[100 chars max]",
  "kokonaislippumaara": "[int]",
  "jaljellaOlevaLippumaara": "[int]"
}
```
Lisää myös tapahtumapaikkanimi, kapasiteetti, lähiosoite ja postinumero
```json
{
  "tapahtumapaikka": {
    "tapahtumapaikanNimi": "Esimerkki Areena",
    "kapasiteetti": 5000,
    "tapahtumapaikkaId": 1,
    "lahiosoite": "Esimerkkikatu 12",
    "postinumero": {
      "postinumero": "00100",
      "kaupunki": "Helsinki"
    }
  }
}
```

## Onnistunut response

**Tila** : Jos kaikki on OK ja tapahtuma ei toistu.

**Koodi** : `201 CREATED`

**Sisältö esimerkki**
```json
{
  "tapahtumaId": 1,
  "tapahtumaAika": "2025-05-01T18:00:00",
  "tapahtumaNimi": "Esimerkki Konsertti",
  "kuvaus": "Tämä on esimerkki konsertti",
  "kokonaislippumaara": 5000,
  "jaljellaOlevaLippumaara": 5000,
  "tapahtumapaikka": {
    "tapahtumapaikkaId": 1,
    "tapahtumapaikanNimi": "Esimerkki Areena",
    "kapasiteetti": 5000,
    "lahiosoite": "Esimerkkikatu 12",
    "postinumero": {
      "postinumero": "00100",
      "kaupunki": "Helsinki"
    }
  }
}
```

## Virhe Response

**Tila** : Jos sama tapahtuma on jo olemassa.

**Koodi** : `303 SEE OTHER`

### Tai

**Tila** : Jos kenttiä puuttuu.

**Koodi** : `400 BAD REQUEST`
