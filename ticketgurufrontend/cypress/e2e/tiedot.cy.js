describe('Tapahtumien hakeminen ja tietojen katselu', () => {
  it("tekee ostoksen onnistuneesti", () => {
    cy.visit('http://localhost:3000/login'); // vaihda osoite tarvittaessa

    cy.get('input[placeholder="Username"]').type('kayttaja');
    cy.get('input[placeholder="Password"]').type('testaaja123');
    cy.get('button[type="submit"]').click();

    // Avaa myyntisivu
    cy.contains("Tapahtumat").click();
    cy.contains("Näytä tiedot").click();

    cy.contains('Tapahtuman tiedot'); 

  
  });
  
 });