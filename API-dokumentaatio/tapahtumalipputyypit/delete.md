# Tapahtuma lipputyypin poistaminen

Toiminto poistaa tapahtuma lipputyypin id:llä.

**URL** : `/tapahtumalipputyypit/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtuma lipputyypin-taulun primary keytä.

**Metodi**: `DELETE`

**Vaatii tunnistautumisen** : TRUE

**Sisältö** : `{}`

## Onnistunut response

**Tila** : Jos kaikki on ok ja tapahtuma lipputyyppi on poistettu.

**Koodi** : `20O`


## Virhe Response
**Tila** : Tapahtuma lipputyyppiä ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö** : `{}`

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```