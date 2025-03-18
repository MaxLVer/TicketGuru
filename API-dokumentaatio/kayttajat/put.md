# Muokkaa käyttäjää

Toiminnon avulla voidaan muokata yhtä tietokannassa olevaa käyttäjää

**URL** : `/kayttajat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Kayttäjät-taulun primary keytä.

---

**Metodi**: `PUT`

**Data ehdot**

-

## Onnistunut response

**Tila** : Tietue löytyy ja sitä on muokattu onnistuneesti

**Koodi** : `200 OK`

**Sisältö** :
```json

{
  "kayttajaId": INTEGER,
  "kayttajanimi": STRING,
  "kayttajanimi": STRING,
  "etunimi": STRING,
  "sukunimi": STRING,
  "rooli":{
    "rooliId":INTEGER,
    "nimike": STRING,
    "selite": STRING,
  } 
  },

  ```

  ## TAI

**Tila** : Käyttäjää ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö** : `{}`