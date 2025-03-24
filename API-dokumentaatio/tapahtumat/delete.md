# Tapahtuman Poistaminen

Poistaa tapahtuman.

**URL** : `/tapahtumat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtumat-taulun primary keytä.

**Method** : `DELETE`

**Auth required** : TBD

**Permissions required** : TBD

**Data** : `{}`

## Onnistunut response

**Tila** : Jos kaikki on ok ja tapahtuma on poistettu.

**Koodi** : `20O`


## Virhe Response
**Tila** : Tapahtumaa ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö** : `{}`





