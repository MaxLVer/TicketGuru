# Muokkaa ostostapahtuman myyntiaikaa

Toiminnon avulla voidaan päivittää ostostapahtuman myyntiaika tietokannassa.

**URL** : `/ostostapahtumat/{id}/myyntiaika`

**URL Parameters** : `id` vastaa tietokannassa Ostostapahtumat-taulun primary keytä.

---

**Metodi**: `PATCH`

**Vaatii tunnistautumisen** : TRUE
  
**Vaatii hyväksyntää** : TBD  

**Data ehdot**

- `myyntiaika` on pakollinen kenttä ja sen tulee olla validi `LocalDateTime`-muotoinen arvo (ISO 8601).

## Onnistunut response

**Tila** : Tietue löytyy ja myyntiaika on päivitetty onnistuneesti

**Koodi** : `200 OK`

**Esimerkki sisältö** :
```json
{
  "ostostapahtumaId": 1,
  "myyntiaika": "2025-03-10T10:00:00",
  "kayttajaId": 1,
  "_links": {
    "self": {
      "href": "http://localhost:8080/ostostapahtumat/1"
    },
    "kayttaja": {
      "href": "http://localhost:8080/kayttajat/1"
    }
  }
}
```

## TAI

**Tila** : Ostostapahtumaa ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö** : `{}`

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