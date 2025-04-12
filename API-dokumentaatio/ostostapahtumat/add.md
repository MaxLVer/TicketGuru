# Uuden ostostapahtuman lisääminen

Toiminto lisää uuden ostostapahtuman.

**URL** : `/ostostapahtumat`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää käyttäjän id
```json
{
  "kayttajaId": 1
}
```


## Onnistunut response

**Tila** : Jos kaikki on OK-

**Koodi** : `201 CREATED`

**Sisältö esimerkki:**
```json
{
  "ostostapahtumaId": 2,
  "myyntiaika": null,
  "kayttajaId": 1,
  "lippuIdt": [],
  "_links": {
    "self": {
      "href": "http://localhost:8080/ostostapahtumat/2"
    },
    "kayttaja": {
      "href": "http://localhost:8080/kayttajat/1"
    }
  }
}
```

## Virhe Response

**Tila** : Jos käyttäjää ei löydy

**Koodi** : `404 Not Found`

## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```