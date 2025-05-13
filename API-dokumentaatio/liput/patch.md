# Muokkaa lipun status

Toiminnon avulla voidaan päivittää lipun status tietokannassa.

**URL** : `/liput/{id}`

**URL Parameters** : `id` vastaa tietokannassa Liput-taulun primary keytä.

---

**Metodi**: `PATCH`

**Vaatii tunnistautumisen** : TRUE

**Data ehdot**: `{}`



## Onnistunut response

**Tila** : Tietue löytyy ja myyntiaika on päivitetty onnistuneesti(myyntiaika kesken)

**Koodi** : `200 OK`

**Esimerkki sisältö** :
```json
{
    "lippuId": 102,
    "tapahtumaId": 4,
    "ostostapahtumaId": 1,
    "tapahtumaLipputyyppiId": 4,
    "koodi": "B57E8B6C",
    "status": "KÄYTETTY",
    "_links": {
        "self": {
            "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/liput/102"
        },
        "tapahtuma": {
            "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumat/4"
        },
        "tapahtumalipputyyppi": {
            "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumalipputyypit/4"
        },
        "ostostapahtuma": {
            "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/ostostapahtumat/1"
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
