# Käyttäjien haku

Toiminto listaa kaikki käyttäjät tai näyttää yhden käyttäjäm

**Listaa kaikki tapahtumat**

**URL** : `/kayttjat/` 

---

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE

**Data ehdot**

-

## Onnistunut response

**Tila** : Yksi tai useampi käyttäjä on näkyvissä

**Koodi** : `200 OK`

**Sisältö** : 
```json

{
  "kayttajaId": INTEGER,
  "kayttajanimi": STRING,
  "kayttajanimi": STRING,
  "etunimi": STRING,
  "sukunimi": STRING,
  "rooliId": INTEGER,
  },

  ```

## TAI

**Tila** : Tapahtumalle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```