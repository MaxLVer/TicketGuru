# Tapahtumapaikan muokkaaminen

Tämä toiminto muokkaa valittua tapahtumapaikka-resurssia 

**URL** : `/tapahtumapaikat/{id}`

**Metodi**: `PUT`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot**  
Päivitä olemassa oleva tapahtumapaikka käyttämällä sen ID:tä.

```json
{
  "lahiosoite": STRING,
  "postinumeroId": INTEGER,
  "tapahtumapaikanNimi": STRING,
  "kapasiteetti": INTEGER
}
```
---
### Onnistunut Response

**Tila**: Jos kaikki on OK

**Koodi**: `200 OK`

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

---
## Virhe Response

**Tila** : Jos tapahtumapaikkaa ei löydy

**Koodi** : `404 NOT FOUND`

### Virhe Response

**Tila**: Jos postinumeroa ei löydy

**Koodi**: `400 BAD REQUEST`
