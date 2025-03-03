# Tapahtumien/Tapahtuman haku

Toiminto listaa kaikki tapahtumat tai näyttää yhden tapahtuman

**Listaa kaikki tapahtumat**
**URL** : `/tapahtumat/` 

**Hakee Tapahtumat-taulun ID:n avulla tietyn tapahtuman**
**URL** : `/tapahtumat/{id}`

**URL Parameters** : `id` vastaa tietokannassa Tapahtumat-taulun primary keytä.

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**

-

## Onnistunut response

**Tila** : Yksi tai useampi tapahtuma on näkyvissä käyttäjälle

**Koodi** : `200 OK`

**Sisältö** : `{}`

## TAI

**Tila** : Tapahtumalle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`
