import React, { useState, useEffect } from "react";
import axios from "axios";
import { Button, Form, Card, Col, Row, Spinner, Alert } from "react-bootstrap";
import { QRCodeSVG } from 'qrcode.react';

const API_BASE_URL = process.env.REACT_APP_API_URL;

const TicketSaleApp = () => {
  const [tapahtumat, setTapahtumat] = useState([]);
  const [valittuTapahtuma, setValittuTapahtuma] = useState(null);
  const [lippujenMaara, setLippujenMaara] = useState(1);
  const [asiakas, setAsiakas] = useState({ nimi: "", sahkoposti: "" });
  const [isLoading, setIsLoading] = useState(false);
  const [ostoStatus, setOstoStatus] = useState(null);
  const [lipputiedot, setLipputiedot] = useState(null);

  useEffect(() => {
    const haeTapahtumat = async () => {
      try {
        const res = await axios.get(`${API_BASE_URL}/tapahtumat`);
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
    if (!asiakas.nimi || !asiakas.sahkoposti || !valittuTapahtuma) return;
    setIsLoading(true);
    setOstoStatus(null);

    try {
      const res = await axios.post(`${API_BASE_URL}/liput`, {
        tapahtumaId: valittuTapahtuma.id, // assuming the field is 'id'
        maara: Number(lippujenMaara),
        asiakas: {
          nimi: asiakas.nimi,
          sahkoposti: asiakas.sahkoposti,
        },
      });

      setLipputiedot(res.data);
      setOstoStatus("onnistui");
    } catch (error) {
      console.error("Osto epäonnistui:", error);
      setOstoStatus("virhe");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container mt-5">
      <h1>Lipunmyynti</h1>

      {/* {!valittuTapahtuma && (
        <Row>
          {tapahtumat.map((t) => (
            <Col sm={12} md={6} lg={4} key={t.id}>
              <Card className="mb-3">
                <Card.Body>
                  <Card.Title>{t.nimi}</Card.Title>
                  <Card.Text>{t.kuvaus}</Card.Text>
                  <Button onClick={() => setValittuTapahtuma(t)}>Valitse tapahtuma</Button>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      )}

      {valittuTapahtuma && ostoStatus !== "onnistui" && (
        <div>
          <h3>{valittuTapahtuma.nimi}</h3>
          <p>{valittuTapahtuma.kuvaus}</p>

          <Form>
            <Form.Group className="mb-2">
              <Form.Label>Nimi</Form.Label>
              <Form.Control
                type="text"
                name="nimi"
                value={asiakas.nimi}
                onChange={muutaAsiakasTieto}
                placeholder="Etunimi Sukunimi"
              />
            </Form.Group>

            <Form.Group className="mb-2">
              <Form.Label>Sähköposti</Form.Label>
              <Form.Control
                type="email"
                name="sahkoposti"
                value={asiakas.sahkoposti}
                onChange={muutaAsiakasTieto}
                placeholder="sahkoposti@example.com"
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
            <p><strong>Nimi:</strong> {lipputiedot?.asiakas?.nimi}</p>
            <p><strong>Sähköposti:</strong> {lipputiedot?.asiakas?.sahkoposti}</p>
            <p><strong>Tapahtuma:</strong> {lipputiedot?.tapahtuma?.nimi}</p>
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
      )} */}
    </div>
  );
};

export default TicketSaleApp;
