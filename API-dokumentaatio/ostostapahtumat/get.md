# Ostostapahtumien/Ostostapahtuman haku

Tämä toiminto listaa kaikki ostostapahtumat tai hakee yhden tietyn tapahtuman.

## Listaa kaikki ostostapahtumat

**URL** : `/ostostapahtumat/`

---

## Hakee tietyn ostostapahtuman ID:n avulla

**URL** : `/ostostapahtumat/{id}`

**URL Parameters** :  
- `id` – Vastaa tietokannassa Ostostapahtumat-taulun primary keytä.

---

## HTTP-metodi

**Metodi**: `GET`

**Vaatii tunnistautumisen** : TBD  
**Vaatii hyväksyntää** : TBD  

---

## Response

### Onnistunut pyyntö – kaikki ostostapahtumat

**Koodi** : `200 OK`

**Sisältö** :  
```json
[
  {
    "id": INTEGER,
    "kayttajaId": INTEGER,
    "myyntiaika": DATE,
  }
]
```

## TAI

**Tila** : Tapahtumalle ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`
