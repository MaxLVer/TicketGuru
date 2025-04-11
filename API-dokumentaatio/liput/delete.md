# Lipun Poistaminen

Poistaa lipun.

**URL** : `/liputt/{id}`

**URL Parameters** : `id` vastaa tietokannassa liput-taulun primary keytä.

**Metodi** : `DELETE`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot** : `{}`

## Success Response

**Tila** : lipun oltava tietokannssa.

**Koodi** : `204 NO CONTENT`

**Sisältö esimerkki** : `{}`

## Error Responses

**Tila** : Lippua ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö esimerkki** : `{}`

