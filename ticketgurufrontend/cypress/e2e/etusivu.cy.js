describe('Etusivu toimii', () => {
  it('avaa sivun ja tarkistaa tekstin', () => {
    cy.visit('http://localhost:3000');
    cy.contains('Welcome to Home Page'); 
  });
});