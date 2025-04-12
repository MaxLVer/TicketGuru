# Ostostapahtuman poistaminen

Toiminto poistaa halutun ostostapahtuman, johon ei liity lippuja.

**URL** : `/ostostapahtumat/{id}`

**Metodi**: `DELETE`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Sisältö** : `{}`

## Onnistunut response

**Tila** : Jos kaikki on OK ja ostostapahtuma on poistettu.

**Koodi** : `204 NO CONTENT`


## Virhe Response
**Tila** : Ostostapahtumaa ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö** : `{}`

## Virhe Response

**Tila** : Jos ostostapahtumaan liittyy lippu.

**Koodi** : `409 CONFLICT`

**Sisältö** : `{}`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```