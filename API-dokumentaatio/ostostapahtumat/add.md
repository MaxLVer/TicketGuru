# Uuden lipun lisääminen

Toiminto lisää uuden lipun.

**URL** : `/ostostapahtumat`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää käyttäjän id
```json
{
  "kayttaja":{
    "kayttajaId":INTEGER
  }
}
```


## Onnistunut response

**Tila** : Jos kaikki on OK ja tapahtuma ei toistu.

**Koodi** : `201 CREATED`

**Sisältö esimerkki**
```json
{
  "ostostapahtumaId": 2,
  "myyntiaika": null,
  "kayttaja": {
    "kayttajaId": 1,
    "rooli": {
      "rooliId": 1,
      "nimike": "yllapito",
      "rooli_selite": "Ylläpitäjät hallitsevat järjestelmää."
    },
    "kayttajanimi": "test1",
    "etunimi": "Teppo",
    "sukunimi": "Testaaja"
  },
  "id": 2
}
```

## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`
