# Tapahtuma lipputyypin muokkaaminen

Toiminto muokkaa tapahtuma lipputyyppiä.

**URL** : `/tapahtumalipputyypit/{id}`

**Metodi**: `PUT`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot**
- Joko tapahtumaid, asiakatyypin id tai hinnan muokkaaminen.

```json
{
  "kayttajaId": INT
}
```
tai
```json
{
  "asiakastyyppiId": INT
}
```
tai
```json
{
  "hinta": DOUBLE
}
```

## Onnistunut response

**Tila** : Jos kaikki on ok.

**Koodi** : `200 OK`

## Virhe Response

**Tila** : Jos tapahtuma lipputyyppiä ei löydy

**Koodi** : `404 Not Found`
## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`
