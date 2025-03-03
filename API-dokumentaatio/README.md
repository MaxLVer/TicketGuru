# API-dokumentaatio

### Julkiset endpointit
Julkiset endpointit, jotka eivät vaadi tunnistautumista

- Login(ei vielä valmis)

### Endpointit jotka vaativat tunnistautumisen
Suljetut endpointit, jotka vaativat voimassaolevan Tokenin(sisäänkirjautuminen).
Määräaikaisesti julkiset

- [Näytä tapahtumat](): GET
- [Luo tapahtuma](tapahtumat/add.md): POST
- [Poista tapahtuma](tapahtumat/delete.md):DELETE 
- [Hae tapahtumaa](): GET
- [Muokkaa tapahtumaa](): PUT
