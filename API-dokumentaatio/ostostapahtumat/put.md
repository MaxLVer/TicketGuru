# Ostostapahtuman muokkaaminen

Toiminto muokkaa ostostapahtumaa.

**URL** : `/ostostapahtumat/{id}`

**Metodi**: `PUT`

**Vaatii tunnistautumisen** : TRUE
  
**Vaatii hyväksyntää** : TBD  

**Data ehdot**
Muokkaa esimerkiksi käyttäjän id toiseksi


```json
{
  "kayttajaId": 1
}
```


## Onnistunut response

**Tila** : Jos kaikki on OK.

**Koodi** : `200 OK`

**Sisältö esimerkki**
```json
{
  "ostostapahtumaId": 1,
  "myyntiaika": null,
  "kayttajaId": 1,
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

**Tila** : Jos ostostapahtumaa ei löydy

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