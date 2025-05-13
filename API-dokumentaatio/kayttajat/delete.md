# Käyttäjän Poistaminen

Poistaa käyttäjän.

**URL** : `/kayttajat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Käyttäjät-taulun primary keytä.

**Metodi** : `DELETE`

**Vaatii tunnistautumisen** : TRUE

**Data ehdot** : `{}`

# Success Response

**Tila** : Käyttäjän oltava tietokannssa.

**Koodi** : `204 NO CONTENT`

**Sisältö esimerkki** : `{}`

## Error Responses

**Tila** : Käyttäjää ei ole tietokannassa

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