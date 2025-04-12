# API-dokumentaatio

### Julkiset endpointit
Julkiset endpointit, jotka eivät vaadi tunnistautumista

- [Sisään kirjautuminen](kayttajat/autentikointi/login.md): POST
- [Ulos kirjautuminen](kayttajat/autentikointi/)

### Endpointit jotka vaativat tunnistautumisen
Suljetut endpointit, jotka vaativat voimassaolevan Tokenin(sisäänkirjautuminen).
Määräaikaisesti julkiset
---
### Tapahtumat
- [Näytä tapahtumat / Hae yksi tapahtuma](tapahtumat/get.md): GET
- [Luo tapahtuma](tapahtumat/add.md): POST
- [Poista tapahtuma](tapahtumat/delete.md):DELETE 
- [Muokkaa tapahtumaa](tapahtumat/put.md): PUT
---
### Liput
- [Näytä liput / Hae yksi lippu](liput/get.md): GET
- [Lisää lippu](liput/add.md): POST
- [Muokkaa Lippua](liput/put.md): PUT
- [Poista lippu](liput/delete.md): DELETE
---
### Ostostapahtumat
- [Näytä ostostapahtumat / Näytä yksi ostostapahtuma](ostostapahtumat/get.md): GET
- [Lisää ostostapahtuma](ostostapahtumat/add.md): POST
- [Lisää ostostapahtumaan myyntiaika](ostostapahtumat/patch.md): PATCH
- [Muokkaa ostostapahtumaa](ostostapahtumat/put.md): PUT
- [Poista ostostapahtuan](ostostapahtumat/delete.md): DELETE
---
### Tapahtumapaikat
- [Näytä tapahtumapaikat / Hae yksi tapahtumapaikka](tapahtumapaikat/get.md): GET
- [Luo tapahtumapaikka](tapahtumapaikat/add.md): POST
- [Muokkaa tapahtumapaikkaa](tapahtumapaikat/put.md): PUT
- [Poista tapahtumapaikka](tapahtumapaikat/delete.md): DELETE
---
### Tapahtumalipputyypit
- [Näytä tapahtumalipputyypit](tapahtumalipputyypit/get.md): GET
- [Luo tapahtumalippu](tapahtumalipputyypit/add.md): POST
- [Muokkaa tapahtumalipputyyppiä](tapahtumalipputyypit/put.md): PUT
- [Poista tapahtumalipputyyppi](tapahtumalipputyypit/delete.md): DELETE
---
### Roolit
- [Näytä roolit](roolit/get.md): GET
---
### Asiakastyypit
- [Näytä asiakastyypit / hae yksi asiakastyyppi](asiakastyypit/get.md): GET
- [Luo asiakastyyppi](asiakastyypit/add.md): POST
- [Muokkaa asiakastyyppiä](asiakastyypit/put.md): PUT
- [Poista asiakastyyppi](asiakastyypit/delete.md): DELETE
