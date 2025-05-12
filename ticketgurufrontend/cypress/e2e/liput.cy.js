describe('Lippujenn hakeminen ja käytetyksi merkitseminen', () => {
  it("tekee ostoksen onnistuneesti", () => {
    cy.visit('http://localhost:3000/login'); // vaihda osoite tarvittaessa

    cy.get('input[placeholder="Username"]').type('kayttaja');
    cy.get('input[placeholder="Password"]').type('testaaja123');
    cy.get('button[type="submit"]').click();

   // Avaa myydyt liput 
    cy.contains("Liput").click();
    cy.contains("Merkitse käytetyksi").click();

  
  });
  
 });