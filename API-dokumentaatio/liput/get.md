# Lippujen haku

Toiminto listaa kaikki tapahtumat tai näyttää yhden tapahtuman

**Listaa kaikki tapahtumat**

**URL** : `/liput/` 

---


**Metodi**: `GET`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**

-

## Onnistunut response

**Tila** : Yksi tai useampi lippu on näkyvissä käyttäjälle

**Koodi** : `200 OK`

**Sisältö** : 
```json
{
    "lippuId": INTEGER,
    "tunniste": STRING,
    "voimassaoloaika": DATETIME,
    "status": STRING,
    "tapahtumaId": INTEGER,
    "ostostapahtumaId": INTEGER,
    "tapahtumaLipputyyppiId": INTEGER
}

```

## TAI

**Tila** : Tapahtumalle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`