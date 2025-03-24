# Olemassa olevan asiakastyypin muokkaaminen

Toiminto muokkaa asiakastyyppiä.

**URL** : `/asiakastyypit/{id}`

**Metodi**: `PUT`

**Vaatii tunnistautumisen** : TBD

**Vaatii hyväksyntää** : TBD

**Data ehdot**
Lisää asiakastyyppi
```json
{
  "asiakastyyppi": STRING,
}
```


## Onnistunut response

**Tila** : Jos kaikki on OK ja muokkaus onnistuu.

**Koodi** : `200 OK`

**Sisältö esimerkki**
```json
{
    {
    "asiakastyypiId": 3,
    "asiakastyyppi": "eläkeläislippu",
    "_links": {
        "self": {
            "href": "http://localhost:8080/asiakastyypit/3"
        }
    }
}
    
}
```

## Virhe Response

**Tila** : Jos kenttiä puuttuu tai niissä on virheitä.

**Koodi** : `400 BAD REQUEST`
