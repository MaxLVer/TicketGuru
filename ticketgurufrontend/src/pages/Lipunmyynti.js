import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from 'jwt-decode';
import { Button, Form, Card, Col, Row, Spinner, Alert } from "react-bootstrap";
import { QRCodeSVG } from 'qrcode.react';

const API_BASE_URL = process.env.REACT_APP_API_URL;

const TicketSaleApp = () => {
  const [tapahtumat, setTapahtumat] = useState([]);
  const [valittuTapahtuma, setValittuTapahtuma] = useState(null);
  const [lippujenMaara, setLippujenMaara] = useState(1);
  const [asiakas, setAsiakas] = useState({ etunimi: "", sukunimi: "" });
  const [isLoading, setIsLoading] = useState(false);
  const [ostoStatus, setOstoStatus] = useState(null);
  const [lipputiedot, setLipputiedot] = useState(null);
  const [ostostapahtumaId, setOstostapahtumaId] = useState(null);
  const [uudetLiput, setUudetLiput] = useState(0); // Uudet liput määrän tallentaminen

  useEffect(() => {
    const haeTapahtumat = async () => {
      try {
        const token = localStorage.getItem("jwtToken");
        const res = await axios.get(`${API_BASE_URL}/tapahtumat`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setTapahtumat(res.data);
      } catch (error) {
        console.error("Tapahtumien haku epäonnistui:", error);
      }
    };
    haeTapahtumat();
  }, []);

  const muutaAsiakasTieto = (e) => {
    const { name, value } = e.target;
    setAsiakas((prev) => ({ ...prev, [name]: value }));
  };
  const ostaLiput = async () => {
    // Tarkistetaan, että tarvittavat kentät ovat täytettyjä
    if (!asiakas.etunimi || !asiakas.sukunimi || !valittuTapahtuma || !lippujenMaara) {
      alert("Tarkista, että kaikki kentät on täytetty oikein.");
      return;
    }
  
    setIsLoading(true);
    setOstoStatus(null);
  
    try {
      const token = localStorage.getItem("jwtToken");
  
      // Lähetetään pyyntö API:lle
      const res = await axios.post(`${API_BASE_URL}/liput`, {
        tapahtumaId: valittuTapahtuma.id,
        maara: Number(lippujenMaara),  // Varmistetaan, että määrä on numero
        ostostapahtumaId: ostostapahtumaId,
        asiakas: {
          etunimi: asiakas.etunimi,
          sukunimi: asiakas.sukunimi,
        },
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      // Jos pyyntö onnistui, käsitellään vastaus
      setOstoStatus("onnistui");
      // Tallennetaan lipputiedot vastausobjektista
      setLipputiedot(res.data);  // Tämä voi olla sinun vastausobjektisi rakenteen mukaan
  
    } catch (error) {
      // Jos pyyntö epäonnistuu, tarkistetaan virhe
      console.error("Osto epäonnistui:", error.response || error.message || error);
  
      // Näytetään virheilmoitus käyttäjälle
      setOstoStatus("virhe");
      alert("Lippujen osto epäonnistui. Tarkista tiedot ja yritä uudelleen.");
    } finally {
      // Poistetaan lataus-tilan päivitys
      setIsLoading(false);
    }
  };
  

  const valitseTapahtumaJaLuoOstostapahtuma = async (tapahtuma) => {
    setValittuTapahtuma(tapahtuma);
    setIsLoading(true);
  
    try {
      const token = localStorage.getItem("jwtToken");
  
      const parseJwt = (token) => {
        try {
          return JSON.parse(atob(token.split('.')[1]));
        } catch (e) {
          return null;
        }
      };
  
      const decoded = parseJwt(token);
      const kayttajaId = decoded?.kayttajaId || decoded?.sub;
  
      if (!kayttajaId) {
        throw new Error("Käyttäjä-ID puuttuu tokenista.");
      }
  
      const payload = {
        myyntiaika: new Date().toISOString(),
        kayttajaId: 22,
      };
  
      console.log("Lähetetään payload:", payload);
  
      const response = await axios.post(
        `${API_BASE_URL}/ostostapahtumat`,
        payload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
  
      console.log(" Vastaus ostostapahtumasta:", response.data);
  
      setOstostapahtumaId(response.data.ostostapahtumaId); 
    } catch (error) {
      console.error(" Ostostapahtuman luonti epäonnistui:", error);
      alert("Ostostapahtuman luonti epäonnistui!");
    } finally {
      setIsLoading(false);
    }
  };
  

  const lisaaLiputTapahtumaan = async () => {
    if (!uudetLiput || uudetLiput <= 0 || !valittuTapahtuma) return;
  
    setIsLoading(true);
    try {
      const token = localStorage.getItem("jwtToken");
  
      const payload = {
        tapahtumaId: valittuTapahtuma.id,
        tapahtumaLipputyyppiId: valittuTapahtuma.lipputyyppiId,
        maara: uudetLiput,
        ostostapahtumaId: "ostostapahtumaId_placeholder",
        status: "MYYTY",
        asiakas: {
          etunimi: asiakas.etunimi,
          sukunimi: asiakas.sukunimi,
        },
      };
  
      console.log("Ostetaan liput payloadilla:", payload);
  
      await axios.post(`${API_BASE_URL}/liput/lisaa`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
 
      // Päivitetään tapahtumat uudella lippumäärällä
      const res = await axios.get(`${API_BASE_URL}/tapahtumat`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setTapahtumat(res.data);
      alert("Lippuja lisätty tapahtumalle!");
    } catch (error) {
      console.error("Lippujen lisääminen epäonnistui:", error);
      alert("Lippujen lisääminen epäonnistui!");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container mt-5">
      <h1>Lipunmyynti</h1>

      {!valittuTapahtuma && (
        <Row>
          {tapahtumat.map((t) => (
            <Col sm={12} md={6} lg={4} key={t.tapahtumaId}>
              <Card className="mb-3">
                <Card.Body>
                  <Card.Title>{t.tapahtumaNimi}</Card.Title>
                  <Card.Text>
                    <strong>Kuvaus:</strong> {t.kuvaus}<br />
                    <strong>Aika:</strong> {new Date(t.tapahtumaAika).toLocaleString()}<br />
                    <strong>Lippuja jäljellä:</strong> {t.jaljellaOlevaLippumaara} / {t.kokonaislippumaara}
                  </Card.Text>
                  <Button onClick={() => valitseTapahtumaJaLuoOstostapahtuma(t)}>Valitse tapahtuma</Button>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      )}

      {valittuTapahtuma && (
        <div className="mt-4">
          <h3>{valittuTapahtuma.tapahtumaNimi}</h3>
          <p>{valittuTapahtuma.kuvaus}</p>
{/* 
           ADMININ LIPPUJEN LISÄÄMISOHJAUS 
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Lisää lippuja tapahtumalle</Form.Label>
              <Form.Control
                type="number"
                value={uudetLiput}
                onChange={(e) => setUudetLiput(e.target.value)}
                placeholder="Syötä lippujen määrä"
              />
            </Form.Group>

            <Button onClick={lisaaLiputTapahtumaan} disabled={isLoading}>
              {isLoading ? <Spinner size="sm" animation="border" /> : "Lisää liput"}
            </Button>
          </Form> */}

          <Form>
            <Form.Group className="mb-2">
              <Form.Label>Etunimi</Form.Label>
              <Form.Control
                type="text"
                name="etunimi"
                value={asiakas.etunimi}
                onChange={muutaAsiakasTieto}
                placeholder="Etunimi"
              />
            </Form.Group>

            <Form.Group className="mb-2">
              <Form.Label>Sukunimi</Form.Label>
              <Form.Control
                type="text"
                name="sukunimi"
                value={asiakas.sukunimi}
                onChange={muutaAsiakasTieto}
                placeholder="Sukunimi"
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Lippujen määrä</Form.Label>
              <Form.Control
                type="number"
                min="1"
                value={lippujenMaara}
                onChange={(e) => setLippujenMaara(e.target.value)}
              />
            </Form.Group>

            <Button onClick={ostaLiput} disabled={isLoading}>
              {isLoading ? <Spinner size="sm" animation="border" /> : "Osta liput"}
            </Button>
          </Form>
        </div>
      )}

      {ostoStatus === "onnistui" && lipputiedot && (
        <div className="mt-4">
          <Alert variant="success">
            <h4>Osto onnistui!</h4>
            <p><strong>Etunimi:</strong> {lipputiedot?.asiakas?.etunimi}</p>
            <p><strong>Sukunimi:</strong> {lipputiedot?.asiakas?.sukunimi}</p>
            <p><strong>Tapahtuma:</strong> {lipputiedot?.tapahtuma?.tapahtumaNimi}</p>
            <p><strong>Lippuja:</strong> {lipputiedot?.maara}</p>
            {lipputiedot?.qrUrl && (
              <>
                <h5>QR-koodi:</h5>
                <QRCodeSVG value={lipputiedot.qrUrl} size={200} />
              </>
            )}
          </Alert>
          <Button variant="secondary" onClick={() => window.location.reload()}>
            Osta lisää
          </Button>
        </div>
      )}

      {ostoStatus === "virhe" && (
        <Alert variant="danger" className="mt-4">
          Lippujen osto epäonnistui. Tarkista tiedot ja yritä uudelleen.
        </Alert>
      )}
    </div>
  );
};

export default TicketSaleApp;