# Uuden tapahtuma lipputyypin lisääminen

Toiminto lisää uuden tapahtuma lipputyypin.

**URL** : `/tapahtumalipputyypit`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TRUE

**Data ehdot**

- Lisää tapahtuman id
- Lisää asiakastyypin id
- Lisää hinta. Hinta ei voi olla negatiivinen.
```json
{
  "tapahtumaId": INT,
  "asiakastyyppiId": INT,
  "hinta": DOUBLE
}
```

## Onnistunut response

**Tila** : Jos kaikki on ok

**Koodi** : `201 CREATED`

**Sisältö esimerkki:**
```json
{
    "tapahtumaLipputyyppiId": 4,
    "tapahtumaId": 2,
    "asiakastyyppiId": 2,
    "hinta": 43,
    "_links": {
        "self": {
            "href": "http://localhost:8080/tapahtumalipputyypit/4"
        },
        "tapahtuma": {
            "href": "http://localhost:8080/tapahtumat/2"
        },
        "asiakastyyppi": {
            "href": "http://localhost:8080/asiakastyypit/2"
        }
    }
}
```

## Virhe Response

**Tila** : Jos tapahtumaa ei löydy

**Koodi** : `404 Not Found`

**Tila** : Jos asiakastyyppiä ei löydy

**Koodi** : `404 Not Found`


## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```