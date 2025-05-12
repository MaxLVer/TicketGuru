import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { Button, Table } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

const API_BASE_URL = process.env.REACT_APP_API_URL;

const CartSummaryPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [ostosKori, setOstosKori] = useState([]);

  useEffect(() => {
    const { ostosKori: kori } = location.state || {};
    if (Array.isArray(kori)) {
      setOstosKori(kori);
    }
  }, [location.state]);

  // Toimiva poisto-funktio
  const poistaKorista = (index) => {
    const uusiKori = [...ostosKori];
    uusiKori.splice(index, 1);
    setOstosKori(uusiKori);
  };





  // Luo uuden ostostapahtuman
  const luoOstostapahtuma = async () => {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
      alert("Kirjaudu sisään uudelleen. Token puuttuu.");
      return;
    }
    
    const kayttajaId = localStorage.getItem("kayttajaId");

    const payload = {
      myyntiaika: new Date().toISOString(),
      kayttajaId: kayttajaId,
    };

    const response = await axios.post(
      `${API_BASE_URL}/ostostapahtumat`,
      payload,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data?.ostostapahtumaId;
  };


  const handleOstaKaikki = async () => {
    if (!ostosKori || ostosKori.length === 0) {
      alert("Ostoskorisi on tyhjä tai sitä ei ladattu oikein.");
      return;
    }

    try {
      const token = localStorage.getItem("jwtToken");

      // Luo ostostapahtuma ja hae ID
      const ostostapahtumaId = await luoOstostapahtuma();
      if (!ostostapahtumaId) throw new Error("Ostostapahtuman luonti epäonnistui.");

      // Luodaan taulukko lippujen määrän mukaan
      const payload = ostosKori.flatMap(item =>
        Array.from({ length: item.maara }).map(() => ({
          tapahtumaLipputyyppiId: item.tapahtumaLipputyyppiId,
          ostostapahtumaId: ostostapahtumaId,
          tapahtumaId: item.tapahtumaId,
          asiakas: item.asiakas
        }))
      );

      // Lähetä payload
      const response = await axios.post(`${API_BASE_URL}/liput/kori`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      });

      const lipputiedot = response.data;

      if (!lipputiedot || !Array.isArray(lipputiedot)) {
        throw new Error("Palvelin ei palauttanut odotettua vastausta.");
      }
      // Lisätään tiedot kuittiin
      const combinedReceipt = ostosKori.map((item) => ({
        tapahtumaNimi: item.tapahtumaNimi,
        lipputyyppi: item.lipputyyppiNimi || item.tapahtumaLipputyyppiId,
        asiakasNimi: `${item.asiakas.etunimi} ${item.asiakas.sukunimi}`,
        lippujenMaara: item.maara,
      }));

      console.log("Siirretään kuitti:", combinedReceipt);
      navigate("/kuitti", {
        state: { kuitti: combinedReceipt }
      });

    } catch (error) {
      console.error("Osto epäonnistui:", error);
      alert("Osto epäonnistui. Tarkista virheilmoitus konsolista.");
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
        <Button variant="success" onClick={handleOstaKaikki}>
          Osta kaikki
        </Button>
      )}
      {ostosKori.length > 0 && (
        <div className="mt-3">
          <Button variant="success" onClick={() => navigate("/lipunmyynti", { state: { ostosKori } })}>
            Takaisin lipunmyyntiin
          </Button>
        </div>
      )}
    </div>
  );
};

export default CartSummaryPage;
