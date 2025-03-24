# Lipun Poistaminen

Poistaa lipun.

**URL** : `/liputt/{id}`

**URL Parameters** : `id` vastaa tietokannassa liput-taulun primary keytä.

**Method** : `DELETE`

**Auth required** : TBD

**Permissions required** : TBD

**Data** : `{}`

## Success Response

**Condition** : lipun oltava tietokannssa.

**Code** : `204 NO CONTENT`

**Content** : `{}`

## Error Responses

**Condition** : Lippua ei ole tietokannassa

**Code** : `404 NOT FOUND`

**Content** : `{}`

