# Uuden tapahtuman lisääminen

Toiminto lisää uuden tapahtuman.

**URL** : `/tapahtumat/`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot**
- Lisää tapahtumapaikan id
- Tapahtuman nimi
- Tapahtuman aika
- Kuvaus
- Kokonaislippumäärä. Kokonaislippumäärä ei voi olla negatiivinen.
```json
{
  "tapahtumaId": INTEGER,
  "tapahtumaNimi": STRING,
  "tapahtumaAika": DATETIME ("[YYYY-MM-DDTHH:MM:SS]"),
  "kuvaus": STRING,
  "kokonaislippumaara": INTEGER,
}
```

## Onnistunut response

**Tila** : Jos kaikki on OK ja tapahtuma ei toistu.

**Koodi** : `201 CREATED`

**Sisältö esimerkki**
```json
{
    "tapahtumaId": 4,
    "tapahtumapaikkaId": 1,
    "tapahtumaAika": "2025-04-15T18:00:00",
    "tapahtumaNimi": "Rock Festival",
    "kuvaus": "Annual rock music festival.",
    "kokonaislippumaara": 1000,
    "jaljellaOlevaLippumaara": 1000,
    "_links": {
        "self": {
            "href": "http://localhost:8080/tapahtumat/4"
        },
        "tapahtumapaikka": {
            "href": "http://localhost:8080/tapahtumapaikat/1"
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
