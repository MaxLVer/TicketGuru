# Asiakastyypin Poistaminen

Poistaa Asiakastyypin.

**URL** : `/Asiakastyypit/{id}`

**URL Parameters** : `id` vastaa tietokannassa Asiakastyypit-taulun primary keytä.

**Metodi** : `DELETE`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot** : `{}`

## Onnistunut response

**Tila** : Asiakastyypin oltava tietokannssa.

**Koodi** : `204 NO CONTENT`

**Sisältö esimerkki** : `{}`

## Virhe Response

**Tila** : Asiakastyyppiä ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö esimerkki** : `{}`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```