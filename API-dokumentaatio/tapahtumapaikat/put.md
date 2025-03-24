# Tapahtumapaikan muokkaaminen

Tämä toiminto muokkaa valittua tapahtumapaikka-resurssia 

**URL** : `/tapahtumapaikat/{id}`

**Metodi**: `PUT`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**  
Päivitä olemassa oleva tapahtumapaikka käyttämällä sen ID:tä.

```json
{
  "lahiosoite": "Päivitettykatu 25",
  "postinumeroId": 123,
  "tapahtumapaikanNimi": "Uusi Areena",
  "kapasiteetti": 6000
}
```
---
### Onnistunut Response

**Tila**: Jos kaikki on OK

**Koodi**: `200 OK`

**Sisältö esimerkki**:
```json
{
  "lahiosoite": "Päivitettykatu 25",
  "postinumero": {
    "postinumeroId": 123,
    "postinumero": "STRING",
    "kaupunki": "STRING"
  },
  "tapahtumapaikanNimi": "Uusi Areena",
  "kapasiteetti": 6000,
  "id": INT
}
```
---
### Virhe Response

**Tila**: Jos postinumeroa ei löydy

**Koodi**: `400 BAD REQUEST`
