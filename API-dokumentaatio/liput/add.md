# Uuden lipun lisääminen

Toiminto lisää uuden lipun.

**URL** : `/liput/`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää lipun tunniste, voimassaoloaika, status ostostapahtuman id, tapahtuman id ja tapahtumalipputyypin id
```json
{
  "tunniste": STRING,
  "voimassaoloaika": DATETIME,
  "status": STRING,
  "ostostapahtuma": {
    "ostostapahtumaId": INTEGER
  },
  "tapahtuma": {
    "id": INTEGER
  },
  "tapahtumaLipputyyppi": {
    "tapahtumaLipputyyppiId": INTEGER
  }
}
```


## Onnistunut response

**Tila** : Jos kaikki on OK ja tapahtuma ei toistu.

**Koodi** : `201 CREATED`

**Sisältö esimerkki**
```json
{
    "lippuId": 2,
    "tunniste": "LIPPU123",
    "voimassaoloaika": "2023-10-11T12:00:00",
    "status": "MYYTY",
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
