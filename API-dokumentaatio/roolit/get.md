# Roolien/roolin haku

Toiminto listaa kaikki roolit tai näyttää yhden roolin

**Listaa kaikki roolit**

**URL** : `/roolit/` 

---

**Hakee Rooli-taulun ID:n avulla tietyn tapahtuman**

**URL** : `/rooli/{id}`

**URL Parameters** : `id` vastaa tietokannassa Rooli-taulun primary keytä.

---

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE
  
**Vaatii hyväksyntää** : TBD  

**Data ehdot**

-

## Onnistunut response

**Tila** : Yksi tai useampi rooli on näkyvissä käyttäjälle

**Koodi** : `200 OK`

**Sisältö** : 
```json
[
  {
    "id": INTEGER,
    "nimike": INTEGER,
    "RooliSelite": DATE,
  }
]
```

## TAI

**Tila** : Tapahtumalle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`
