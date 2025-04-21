describe('Login test', () => {
  it('should log in with valid credentials', () => {
    cy.visit('http://localhost:3000/login'); // vaihda osoite tarvittaessa

    cy.get('input[placeholder="Username"]').type('kayttaja');
    cy.get('input[placeholder="Password"]').type('testaaja123');
    cy.get('button[type="submit"]').click();


    // Oleta, että onnistuneen kirjautumisen jälkeen siirrytään etusivulle
    cy.url('http://localhost:3000');
    cy.contains('Welcome').should('be.visible');
  });
});