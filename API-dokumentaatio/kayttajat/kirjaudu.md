# Sisään kirjautuminen

Toiminto kirjaa käyttäjän sisään sovellukseen.

**URL** : `/kayttajat/`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : FALSE

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää käyttäjänimi, salasana (ja rooliId).

```json
{
  "kayttajanimi": STRING,
  "salasana": STRING,
  "rooliId": INTEGER
}
```
## Onnistunut response

**Tila** : Jos kaikki on OK

**Koodi** : `200 OK`

**Sisältö esimerkki**
```json
{
  "accessToken": HASH,
  "token": HASH
}
```
## Virhe Response

**Tila** : Jos käyttäjänimi tai salasana on virheellinen

**Koodi** : `403 FORBIDDEN`