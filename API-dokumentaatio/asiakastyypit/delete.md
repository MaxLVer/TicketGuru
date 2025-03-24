# Asiakastyypin Poistaminen

Poistaa Asiakastyypin.

**URL** : `/Asiakastyypit/{id}`

**URL Parameters** : `id` vastaa tietokannassa Asiakastyypit-taulun primary keytä.

**Method** : `DELETE`

**Auth required** : TBD

**Permissions required** : TBD

**Data** : `{}`

## Success Response

**Condition** : Asiakastyypin oltava tietokannssa.

**Code** : `204 NO CONTENT`

**Content** : `{}`

## Error Responses

**Condition** : Asiakastyyppiä ei ole tietokannassa

**Code** : `404 NOT FOUND`

**Content** : `{}`
