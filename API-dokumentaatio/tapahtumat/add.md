### Uuden tapahtuman lisääminen

Toiminto lisää uuden tapahtuman.

**URL** : `tapahtumat/lisaa`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**

-

## Onnistunut response

**Tila** : Jos kaikki on OK ja tapahtuma ei toistu.

**Koodi** : `201 CREATED`

## Virhe Response

**Tila** : Jos sama tapahtuma on jo olemassa.

**Koodi** : `303 SEE OTHER`

### Tai

**Tila** : Jos kenttiä puuttuu.

**Koodi** : `400 BAD REQUEST`
