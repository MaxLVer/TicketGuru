# Käyttäjän Poistaminen

Poistaa käyttäjän.

**URL** : `/kayttajat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Käyttäjät-taulun primary keytä.

**Method** : `DELETE`

**Auth required** : TBD

**Permissions required** : TBD

**Data** : `{}`

# Success Response

**Condition** : Käyttäjän oltava tietokannssa.

**Code** : `204 NO CONTENT`

**Content** : `{}`

## Error Responses

**Condition** : Käyttäjää ei ole tietokannassa

**Code** : `404 NOT FOUND`

**Content** : `{}`