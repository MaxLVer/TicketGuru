# Uuden asiakastyypinn lisääminen

Toiminto lisää uuden asiakastyypinn.

**URL** : `/asiakastyypit/`

**Metodi**: `POST`

**Vaatii tunnistautumisen** : TRUE

**Data ehdot**
Lisää asiakastyyppi
```json
{
  "asiakastyyppi": STRING,
  
}
```


## Onnistunut response

**Tila** : Jos kaikki on OK.

**Koodi** : `201 CREATED`

**Sisältö esimerkki**
```json
{
    "asiakastyypiId": 3,
    "asiakastyyppi": "eläkeläislippu",
    "_links": {
        "self": {
            "href": "http://localhost:8080/asiakastyypit/3"
        }
    }
}
```

## Virhe Response


**Tila** : Jos kenttiä puuttuu tai niissä on virheitä.

**Koodi** : `400 BAD REQUEST`

## Virhe respone

**Tila** : Jos token on virheellinen

**Koodi** : `401 UNAUTHORIZED`

```json
{
  "error": "Virheellinen token"
}
```
