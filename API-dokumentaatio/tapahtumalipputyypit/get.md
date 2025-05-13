# Tapahtuma lipputyypin haku

Tämä toiminto listaa kaikki tapahtuma lipputyypit tai hakee yhden tietyn tapahtuma lipputyypin.

## Listaa kaikki tapahtumalipputyypit

**URL** : `/tapahtumalipputyypit/`

---

## Hakee tietyn tapahtuma lipputyypin ID:n avulla

**URL** : `/tapahtumalipputyypit/{id}`

**URL Parameters** :  
- `id` – Vastaa tietokannassa Tapahtuma lipputyypin-taulun primary keytä.

---

## HTTP-metodi

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE  

---

## Onnistunut Response

### Onnistunut pyyntö – kaikki tapahtuma lipputyypit

**Koodi** : `200 OK`

**Sisältö esimerkki**
```json
  {
    "tapahtumaLipputyyppiId": 3,
    "tapahtumaId": 5,
    "asiakastyyppiId": 1,
    "hinta": 100.00,
    "links": [
      {
        "rel": "self",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumalipputyypit/3"
      },
      {
        "rel": "tapahtuma",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumat/5"
      },
      {
        "rel": "asiakastyyppi",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/asiakastyypit/1"
      }
    ]
  },
  {
    "tapahtumaLipputyyppiId": 4,
    "tapahtumaId": 5,
    "asiakastyyppiId": 2,
    "hinta": 150.00,
    "links": [
      {
        "rel": "self",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumalipputyypit/4"
      },
      {
        "rel": "tapahtuma",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumat/5"
      },
      {
        "rel": "asiakastyyppi",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/asiakastyypit/2"
      }
    ]
  }
```

## TAI
### Onnistunut pyyntö – yksi

**Koodi** : `200 OK`

```json
{
    "tapahtumaLipputyyppiId": 4,
    "tapahtumaId": 5,
    "asiakastyyppiId": 2,
    "hinta": 150.00,
    "links": [
      {
        "rel": "self",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumalipputyypit/4"
      },
      {
        "rel": "tapahtuma",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumat/5"
      },
      {
        "rel": "asiakastyyppi",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/asiakastyypit/2"
      }
    ]
  }
```

## Virhe Response

**Tila** : Jos tapahtuma lipputyyppiä ei löydy

**Koodi** : `404 Not Found`

## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`
