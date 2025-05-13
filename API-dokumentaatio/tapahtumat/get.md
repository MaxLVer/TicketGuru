# Tapahtumien/Tapahtuman haku

Toiminto listaa kaikki tapahtumat tai näyttää yhden tapahtuman

## Listaa kaikki tapahtumat

**URL** : `/tapahtumat/` 

---

**Hakee Tapahtumat-taulun ID:n avulla tietyn tapahtuman**

**URL** : `/tapahtumat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtumat-taulun primary keytä.

---
## HTTP-metodi

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE

## Onnistunut response

**Tila** : Yksi tai useampi tapahtuma on näkyvissä käyttäjälle

**Koodi** : `200 OK`

```json
{
    "tapahtumaId": INTEGER,
    "tapahtumapaikkaId": INTEGER,
    "tapahtumaAika": DATETIME,
    "tapahtumaNimi": STRING,
    "kuvaus": STRING,
    "kokonaislippumaara": INTEGER,
    "jaljellaOlevaLippumaara": INTEGER,
    "_links": {
        "self": {
            "href": "http://localhost:8080/tapahtumat/1"
        },
        "tapahtumapaikka": {
            "href": "http://localhost:8080/tapahtumapaikat/1"
        }
    }
}
```

## TAI

**Tila** : Tapahtumalle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`
