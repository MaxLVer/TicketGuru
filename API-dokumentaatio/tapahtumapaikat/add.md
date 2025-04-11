# Uuden tapahtumapaikan lisääminen

Toiminto lisää uuden tapahtumapaikan.

## Tapahtumapaikan lisääminen

**URL** : `/tapahtumapaikat`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot**  
Lisää uusi tapahtumapaikka, johon sisältyy muun muassa osoite, postinumero, kapasiteetti ja tapahtumapaikan nimi.

```json
{
  "lahiosoite": STRING,
  "postinumeroId": INTEGER,
  "tapahtumapaikanNimi": STRING,
  "kapasiteetti": INTEGER
}
```

### Onnistunut Response

**Tila**: Jos kaikki on OK

**Koodi**: `201 CREATED`

**Sisältö esimerkki**:
```json
  {
    "tapahtumapaikkaId": INTEGER,
    "lahiosoite": STRING,
    "tapahtumapaikanNimi": STRING,
    "kapasiteetti": INTEGER,
    "postinumeroId": INTEGER,
    "links": [
      {
        "rel": "self",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/tapahtumapaikat/1"
      },
      {
        "rel": "postinumero",
        "href": "https://ticket-guru-git-ohjelmistoprojekti-1.2.rahtiapp.fi/postinumerot/1"
      }
    ]
  }
```

### Virhe Response

**Tila**: Jos vaaditut parametrit ovat tyhjiä/virheellisiä

**Koodi**: `400 BAD REQUEST`

---

**Tila**: Jos haettua postinumeroa ei ole löydy

**Koodi**: `404 NOT FOUND`
