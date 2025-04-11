# Ostostapahtumien/Ostostapahtuman haku

Tämä toiminto listaa kaikki ostostapahtumat tai hakee yhden tietyn tapahtuman.

## Listaa kaikki ostostapahtumat

**URL** : `/ostostapahtumat/`

---

## Hakee tietyn ostostapahtuman ID:n avulla

**URL** : `/ostostapahtumat/{id}`

**URL Parameters** :  
- `id` – Vastaa tietokannassa Ostostapahtumat-taulun primary keytä.

---

## HTTP-metodi

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE
  
**Vaatii hyväksyntää** : TBD  

---

## Response

### Onnistunut pyyntö – kaikki ostostapahtumat

**Koodi** : `200 OK`

**Sisältö esimerkki** :  
```json
{
  "_embedded": {
    "ostostapahtumaDTOes": [
      {
        "ostostapahtumaId": 1,
        "myyntiaika": null,
        "kayttajaId": 1,
        "lippuIdt": [
          1
        ],
        "_links": {
          "self": {
            "href": "http://localhost:8080/ostostapahtumat/1"
          },
          "kayttaja": {
            "href": "http://localhost:8080/kayttajat/1"
         },
          "liput": {
            "href": "http://localhost:8080/liput/1"
          }
        }
      },
      {
        "ostostapahtumaId": 2,
        "myyntiaika": null,
        "kayttajaId": 1,
        "lippuIdt": [],
        "_links": {
          "self": {
            "href": "http://localhost:8080/ostostapahtumat/2"
          },
          "kayttaja": {
            "href": "http://localhost:8080/kayttajat/1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/ostostapahtumat"
    }
  }
}
```

## TAI
### Onnistunut pyyntö – yksi

**Koodi** : `200 OK`

**Sisältö esimerkki** :  
```json
{
  "ostostapahtumaId": 1,
  "myyntiaika": null,
  "kayttajaId": 1,
  "lippuIdt": [
   1
  ],
  "_links": {
    "self": {
      "href": "http://localhost:8080/ostostapahtumat/1"
    },
    "kayttaja": {
      "href": "http://localhost:8080/kayttajat/1"
    },
    "liput": {
      "href": "http://localhost:8080/liput/1"
    }
  }
}
```
## Virhe Response

**Tila** : Jos ostostapahtumaa ei löydy

**Koodi** : `404 Not Found`
## Virhe Response
**Tila** : Ostostapahtumia ei ole

**Koodi** : `200 OK`

**Sisältö** : `{}`

## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`
