# Tapahtuma lipputyypin haku

Tämä toiminto listaa kaikki tapahtuma lipputyypit tai hakee yhden tietyn tapahtuma lipputyypin.

## Listaa kaikki tapahtumalipputyypit

**URL** : `/tapahtumalipputyypit/`

---

## Hakee tietyn tapahtuma lipputyypin ID:n avulla

**URL** : `/tapahtumalipputyypit/{id}`

**URL Parameters** :  
- `id` – Vastaa tietokannassa Tapahtuma lipputyypin-taulun primary keytä.

---

## HTTP-metodi

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TBD  

**Vaatii hyväksyntää** : TBD  

---

## Onnistunut Response

### Onnistunut pyyntö – kaikki tapahtuma lipputyypit

**Koodi** : `200 OK`

## TAI
### Onnistunut pyyntö – yksi

**Koodi** : `200 OK`

## Virhe Response

**Tila** : Jos tapahtuma lipputyyppiä ei löydy

**Koodi** : `404 Not Found`

## Virhe Response

**Tila** : Jos JSON on virheellinen tai se puuttuu.

**Koodi** : `400 BAD REQUEST`
