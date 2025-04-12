# Olemassa olevan lipun muokkaaminen

Toiminto muokkaa lippua.

**URL** : `/liput/{id}`

**Metodi**: `PUT`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää lipun tunniste, voimassaoloaika, ostostapahtuman id, tapahtuman id ja tapahtumalipputyypin id
```json
{
  "tunniste": STRING,
  "voimassaoloaika": DATETIME,
  "ostostapahtumaId": INTEGER,
  "tapahtumaId": INTEGER,
  "tapahtumaLipputyyppiId": INTEGER

}
```


## Onnistunut response

**Tila** : Jos kaikki on OK ja muokkaus onnistuu.

**Koodi** : `200 OK`

**Sisältö esimerkki**
```json
{
    "lippuId": 1,
    "tunniste": "LIPPU123",
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

## Virhe Response

**Tila** : Jos kenttiä puuttuu tai niissä on virheitä.

**Koodi** : `400 BAD REQUEST`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```