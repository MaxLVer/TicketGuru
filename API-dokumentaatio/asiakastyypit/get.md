# Asiakastyyppien haku

Toiminto listaa kaikki asiakastyypit tai näyttää yhden asiakastyypin

**Listaa kaikki asiakastyypit**

**URL** : `/asiakastyypit/` 

---


**Hakee asiakastyypit-taulun ID:n avulla tietyn asiakastyypin**

**URL** : `/asiakastyypit/{id}`

**URL Parameters** : `id` vastaa tietokannassa asiakastyypit-taulun primary keytä.

---


**Metodi**: `GET`

**Vaatii tunnistautumisen** : TRUE

**Vaatii hyväksyntää** : TBD

**Data ehdot**

-

## Onnistunut response

**Tila** : Yksi tai useampi asiakastyyppi on näkyvissä käyttäjälle

**Koodi** : `200 OK`

**Sisältö esimerkki** : 
```json
{
    {
    "asiakastyypiId": 4,
    "asiakastyyppi": "eläkeläislippu",
    "_links": {
        "self": {
            "href": "http://localhost:8080/asiakastyypit/4"
        }
    }
}
}

```

## TAI

**Tila** : asiakastyypille ei ole sisältöä

**Koodi** : `200 OK`

**Sisältö** : `{}`

## Virhe Response

**Tila** : asiakastyyppiä ei löydy.

**Koodi** : `404 NOT FOUND`