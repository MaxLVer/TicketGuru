# Tapahtuman Poistaminen

Poistaa tapahtuman.

**URL** : `/tapahtumat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtumat-taulun primary keytä.

**Method** : `DELETE`

**Auth required** : TBD

**Permissions required** : TBD

**Data** : `{}`

## Success Response

**Condition** : Tapahtuman oltava tietokannssa.

**Code** : `204 NO CONTENT`

**Content** : `{}`

## Error Responses

**Condition** : Tapahtumaa ei ole tietokannassa

**Code** : `404 NOT FOUND`

**Content** : `{}`



