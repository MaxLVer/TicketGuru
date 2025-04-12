# Muokkaa lipun status

Toiminnon avulla voidaan päivittää lipun status tietokannassa.

**URL** : `/liput/{id}`

**URL Parameters** : `id` vastaa tietokannassa Liput-taulun primary keytä.

---

**Metodi**: `PATCH`

**Vaatii tunnistautumisen** : TRUE
  
**Vaatii hyväksyntää** : TBD  

**Data ehdot**: `{}`



## Onnistunut response

**Tila** : Tietue löytyy ja myyntiaika on päivitetty onnistuneesti

**Koodi** : `200 OK`

**Esimerkki sisältö** :
```json
{
    "lippuId": 1,
    "tunniste": "LIPPU300",
    "voimassaoloaika": "2025-10-11T12:00:00",
    "tapahtumaId": 1,
    "ostostapahtumaId": 1,
    "tapahtumaLipputyyppiId": 1,
    "_links": {
        "self": {
            "href": "http://localhost:8080/liput/1"
        },
        "tapahtuma": {
            "href": "http://localhost:8080/tapahtumat/1"
        },
        "tapahtumalipputyyppi": {
            "href": "http://localhost:8080/tapahtumalipputyypit/1"
        },
        "ostostapahtuma": {
            "href": "http://localhost:8080/ostostapahtumat/1"
        }
    }
}
```

## TAI

**Tila** : Ostostapahtumaa ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö** : `{}`

## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`