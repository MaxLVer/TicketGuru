# Uuden lipun lisääminen

Toiminto lisää uuden lipun.

**URL** : `/liput/`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TRUE

**Data ehdot**
Lisää lipun tunniste, voimassaoloaika, ostostapahtuman id, tapahtuman id ja tapahtumalipputyypin id
```json
{
  "tunniste": STRING,
  "voimassaoloaika": DATETIME,
  "ostostapahtumaId": INTEGER,
  "tapahtumaId": INTEGER,
  "tapahtumaLipputyyppiId": INTEGER

}
```

---


## Onnistunut response

**Tila** : Jos kaikki on OK.

**Koodi** : `201 CREATED`

**Sisältö esimerkki**
```json
{
    "lippuId": 2,
    "tunniste": "LIPPU123",
    "voimassaoloaika": "2025-10-11T12:00:00",
    "tapahtumaId": 1,
    "ostostapahtumaId": 1,
    "tapahtumaLipputyyppiId": 1
}
```

## Virhe Response

**Tila** : Jos sama tapahtuma on jo olemassa.

**Koodi** : `303 SEE OTHER`

### Tai

**Tila** : Jos kenttiä puuttuu.

**Koodi** : `400 BAD REQUEST`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```