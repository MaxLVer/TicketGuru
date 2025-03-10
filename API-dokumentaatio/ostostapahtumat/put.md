# Muokkaa ostostapahtuman myyntiaikaa

Toiminnon avulla voidaan päivittää ostostapahtuman myyntiaika tietokannassa.

**URL** : `/ostostapahtumat/{id}/myyntiaika`

**URL Parameters** : `id` vastaa tietokannassa Ostostapahtumat-taulun primary keytä.

---

**Metodi**: `PUT`

**Data ehdot**

- `myyntiaika` on pakollinen kenttä ja sen tulee olla validi `LocalDateTime`-muotoinen arvo (ISO 8601).

## Onnistunut response

**Tila** : Tietue löytyy ja myyntiaika on päivitetty onnistuneesti

**Koodi** : `200 OK`

**Sisältö** :
```json
{
  "myyntiaika": "2025-03-10T10:00:00"
}
```

## TAI

**Tila** : Ostostapahtumaa ei ole tietokannassa

**Koodi** : `404 NOT FOUND`

**Sisältö** : `{}`
