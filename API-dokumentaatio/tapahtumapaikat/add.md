# Uuden tapahtumapaikan lisääminen

Toiminto lisää uuden tapahtumapaikan.

## Tapahtumapaikan lisääminen

**URL** : `/tapahtumapaikat`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**  
Lisää uusi tapahtumapaikka, johon sisältyy muun muassa osoite, postinumero, kapasiteetti ja tapahtumapaikan nimi.

```json
{
  "lahiosoite": "Uusikatu 20",
  "postinumeroId": 123,
  "tapahtumapaikanNimi": "Suuri Areena",
  "kapasiteetti": 5000
}
```

### Onnistunut Response

**Tila**: Jos kaikki on OK

**Koodi**: `201 CREATED`

**Sisältö esimerkki**:
```json
{
  "lahiosoite": "STRING",
  "postinumero": {
    "postinumeroId": INT,
    "postinumero": "STRING",
    "kaupunki": "STRING"
  },
  "tapahtumapaikanNimi": "STRING",
  "kapasiteetti": INT,
  "id": INT
}
```

### Virhe Response

**Tila**: Jos vaaditut parametrit ovat tyhjiä/virheellisiä

**Koodi**: `400 BAD REQUEST`

---

**Tila**: Jos haettua postinumeroa ei ole löydy

**Koodi**: `404 NOT FOUND`
