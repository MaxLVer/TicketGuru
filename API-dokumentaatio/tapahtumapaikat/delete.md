# Tapahtumapaikan poistaminen
Tämä toiminto poistaa tapahtumapaikan

**URL** : `/tapahtumapaikat/{id}`

**Metodi**: `DELETE`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

---

### Onnistunut Response

**Tila**: Jos tapahtumapaikka poistetaan onnistuneesti

**Koodi** : `204 NO CONTENT`

**Sisältö** : `{}`

---

### Virhe Response

**Tila**: Jos tapahtumapaikkaa ei löydy

**Koodi**: `404 NOT FOUND`
