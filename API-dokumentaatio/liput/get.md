# Lippujen haku

Toiminto listaa kaikki liput tai näyttää yhden lipun

**Listaa kaikki liput**

**URL** : `/liput/` 

---


**Hakee liput-taulun ID:n avulla tietyn lipun**

**URL** : `/liput/{id}`

**URL Parameters** : `id` vastaa tietokannassa liput-taulun primary keytä.

---


**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot**

-

## Onnistunut response

**Tila** : Yksi tai useampi lippu on näkyvissä käyttäjälle

**Koodi** : `200 OK`

**Sisältö esimerkki** : 
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

**Tila** : lipulle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`

## Virhe Response

**Tila** : Lippua ei löydy.

**Koodi** : `404 NOT FOUND`