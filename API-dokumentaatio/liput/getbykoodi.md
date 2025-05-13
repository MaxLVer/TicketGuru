
# Lipun haku koodin perusteella

Toiminto näyttää lipun koodin perusteella

**Listaa kaikki liput**

**URL** : `/liput?koodi=ESIM123` 

---

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE

**Data ehdot**

-

## Onnistunut response

**Tila** : Yksi tai useampi lippu on näkyvissä käyttäjälle

**Koodi** : `200 OK`

**Sisältö esimerkki** : 
```json
{
    "lippuId": 1,
    "tapahtumaId": 4,
    "ostostapahtumaId": 1,
    "tapahtumaLipputyyppiId": 4,
    "koodi": "B57E8B6C",
    "status": "MYYTY",
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

**Tila** : lipulle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`

## Virhe Response

**Tila** : Lippua ei löydy.

**Koodi** : `404 NOT FOUND`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```
