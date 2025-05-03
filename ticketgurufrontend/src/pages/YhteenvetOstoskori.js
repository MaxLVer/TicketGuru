import React from "react";
import { useLocation } from "react-router-dom";
import { Button, Table } from "react-bootstrap";
import { useNavigate } from "react-router-dom";


const CartSummaryPage = () => {
  const location = useLocation();
  const { ostosKori = [] } = location.state || {};

  const poistaKorista = (index) => {
    alert(`Poistetaan rivi ${index}`);
  };

  const ostaKokoKori = () => {
    return {
        onnistui: true,
        liput: ostosKori,
        aikaleima: new Date().toISOString()
      };
    };

  const navigate = useNavigate();

  const handleOstaKaikki = async () => {
    try {
      const lipputiedot = await ostaKokoKori(); // Palauttaa tiedot onnistuneesta ostosta

      const combinedReceipt = lipputiedot.liput.map((item) => ({
        tapahtumaNimi: item.tapahtumaNimi,
        lipputyyppi: item.tapahtumaLipputyyppiId,
        asiakasNimi: `${item.asiakas.etunimi} ${item.asiakas.sukunimi}`,
        lippujenMaara: item.maara,
      }));

      console.log("Siirretään kuitti:", combinedReceipt);
      navigate("/kuitti", {
        state: { kuitti: combinedReceipt }
      });
    } catch (error) {
      console.error("Osto epäonnistui:", error);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Yhteenveto ostoskorista</h2>
      {ostosKori.length === 0 ? (
        <p>Ostoskorisi on tyhjä.</p>
      ) : (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Tapahtuma</th>
              <th>Asiakas</th>
              <th>Määrä</th>
              <th>Toiminnot</th>
            </tr>
          </thead>
          <tbody>
            {ostosKori.map((rivi, index) => (
              <tr key={index}>
                <td>{rivi.tapahtumaNimi}</td>
                <td>{rivi.asiakas.etunimi} {rivi.asiakas.sukunimi}</td>
                <td>{rivi.maara}</td>
                <td>
                  <Button variant="danger" size="sm" onClick={() => poistaKorista(index)}>Poista</Button>{' '}
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
      {ostosKori.length > 0 && (
        <Button variant="primary" onClick={handleOstaKaikki}>
          Osta kaikki
        </Button>
      )}
    </div>
  );
};

export default CartSummaryPage;
