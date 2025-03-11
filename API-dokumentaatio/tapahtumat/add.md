# Uuden tapahtuman lisääminen

Toiminto lisää uuden tapahtuman.

**URL** : `/tapahtumat/`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää tapahtumapaikan id, tapahtuman nimi, aika, kuvaus, kokonaislippumäärä, jäljellä oleva lippumäärä
```json
{
  "tapahtumaId": INTEGER,
  "tapahtumaNimi": STRING,
  "tapahtumaAika": DATETIME ("[YYYY-MM-DDTHH:MM:SS]"),
  "kuvaus": STRING,
  "kokonaislippumaara": INTEGER,
  "jaljellaOlevaLippumaara": INTEGER
}
```

## Onnistunut response

**Tila** : Jos kaikki on OK ja tapahtuma ei toistu.

**Koodi** : `201 CREATED`

**Sisältö esimerkki**
```json
{
    "tapahtumapaikka": {
        "lahiosoite": "Töölönkatu 51 B",
        "postinumero": {
            "postinumero": "00250",
            "kaupunki": "Helsinki",
            "postinumeroID": 1
        },
        "tapahtumapaikanNimi": "Kulttuuritehdas Korjaamo",
        "kapasiteetti": 1000,
        "id": 1
    },
    "tapahtumaAika": "2025-03-10T15:00:00",
    "tapahtumaNimi": "Kevätkonsertti",
    "kuvaus": "Ihana kevätkonsertti kaupunginpuistossa",
    "kokonaislippumaara": 500,
    "jaljellaOlevaLippumaara": 250,
    "id": 3
}
```

## Virhe Response

**Tila** : Jos sama tapahtuma on jo olemassa.

**Koodi** : `303 SEE OTHER`

### Tai

**Tila** : Jos kenttiä puuttuu.

**Koodi** : `400 BAD REQUEST`
