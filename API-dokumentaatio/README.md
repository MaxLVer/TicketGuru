# API-dokumentaatio

### Julkiset endpointit
Julkiset endpointit, jotka eivät vaadi tunnistautumista

- Login(ei vielä valmis)

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
- [Näytä liput](liput/get.md): GET
- [Lisää lippu](liput/add.md): POST
---
### Ostostapahtumat
- [Näytä ostostapahtumat / Näytä yksi ostostapahtuma](ostostapahtumat/get.md): GET
- [Lisää ostostapahtuma](ostostapahtumat/add.md): POST
- [Muokkaa ostostapahtumaa](ostostapahtumat/put.md): PATCH
---
