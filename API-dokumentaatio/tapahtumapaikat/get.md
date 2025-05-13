# Tapahtumapaikan haku

Tämä toiminto listaa kaikki tapahtumapaikat tai hakee yhden.

## Listaa kaikki tapahtumalipputyypit

**URL** : `/tapahtumapaikat/`

---

## Hakee tietyn tapahtuma lipputyypin ID:n avulla

**URL** : `/tapahtumapaikat/{id}`

**URL Parameters** :  
- `id` – Vastaa tietokannassa Tapahtumapaikka-taulun primary keytä.

---

## HTTP-metodi

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE

---

## Response

### Onnistunut pyyntö – kaikki tapahtumapaikat

**Koodi** : `200 OK`

```json
  {
    "tapahtumapaikkaId": INTEGER,
    "lahiosoite": STRING,
    "tapahtumapaikanNimi": STRING,
    "kapasiteetti": INTEGER,
    "postinumeroId": INTEGER,
    "links": [
      {
        "rel": "self",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumapaikat/1"
      },
      {
        "rel": "postinumero",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/postinumerot/1"
      }
    ]
  },
{
    "tapahtumapaikkaId": INTEGER,
    "lahiosoite": STRING,
    "tapahtumapaikanNimi": STRING,
    "kapasiteetti": INTEGER,
    "postinumeroId": INTEGER,
    "links": [
      {
        "rel": "self",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumapaikat/2"
      },
      {
        "rel": "postinumero",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/postinumerot/1"
      }
    ]
  }
```

## TAI
### Onnistunut pyyntö – yksi

**Koodi** : `200 OK`

```json
  {
    "tapahtumapaikkaId": INTEGER,
    "lahiosoite": STRING,
    "tapahtumapaikanNimi": STRING,
    "kapasiteetti": INTEGER,
    "postinumeroId": INTEGER,
    "links": [
      {
        "rel": "self",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumapaikat/1"
      },
      {
        "rel": "postinumero",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/postinumerot/1"
      }
    ]
  }
```


## Virhe Response

**Tila** : Jos tapahtumapaikkaa ei löydy

**Koodi** : `404 NOT FOUND`

## Virhe Response

**Tila** : Jos JSON on virheellinen/puuttellinen.

**Koodi** : `400 BAD REQUEST`
