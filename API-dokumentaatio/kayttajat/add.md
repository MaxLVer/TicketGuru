# Uuden käyttäjän lisääminen

Toiminto lisää käyttäjän tapahtuman.

**URL** : `/kayttajat/luo`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : FALSE (Väliaikainen)

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää käyttäjän id, roolin id, kayttajanimen, salasanan, etunimen, sukunimen
```json
{
  "kayttajanimi": STRING,
  "salasana": STRING,
  "etunimi": STRING,
  "sukunimi": STRING,
  "rooli": {
    "rooliId": INTEGER
  }
}
```
## Onnistunut response

**Tila** : Jos kaikki on OK ja tapahtuma ei toistu.

**Koodi** : `201 CREATED`

**Sisältö esimerkki**
```json
{
  "kayttajaId": 1,
  "kayttajanimi": "Kayttaja123",
  "salasana": "Salasana123",
  "etunimi": "Kalle",
  "sukunimi": "Kayttaja",
  "rooli": {
      "rooliId": 2,
      "nimike": "myyjä",
      "rooli_selite": "Lipunmyynti"
    },
  },
```
## Virhe Response

**Tila** : Jos sama tapahtuma on jo olemassa.

**Koodi** : `303 SEE OTHER`

### Tai

**Tila** : Jos kenttiä puuttuu.

**Koodi** : `400 BAD REQUEST`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```