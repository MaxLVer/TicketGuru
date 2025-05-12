import React from "react";
import { useLocation } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { Alert, Button, Card, Col, Row, Spinner } from "react-bootstrap";
import { QRCodeSVG } from "qrcode.react";

const KuittiPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { kuitti } = location.state || {};

  if (!kuitti || kuitti.length === 0) {
    return (
      <div className="container mt-4">
        <h4>Kuitti puuttuu</h4>
        <p>Ei saatavilla olevia tietoja.</p>
        <Button onClick={() => navigate("/")}>Palaa etusivulle</Button>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <h3>Ostostapahtuma</h3>
      {kuitti.map((rivi, index) => (
        <Card key={index} className="mb-3">
          <Card.Body>
          <Card.Title>{rivi.tapahtumaNimi}</Card.Title>
<p><strong>Asiakas:</strong> {rivi.asiakasNimi}</p>
<p><strong>Lippuja:</strong> {rivi.lippujenMaara}</p>
<p><strong>Lipputyyppi-ID:</strong> {rivi.lipputyyppi}</p>
<p><strong>Lippustatus: Myyty</strong></p>
<div style={{ marginTop: "1rem" }}>
        <QRCodeSVG value={JSON.stringify(rivi)} size={128} />
      </div>

          </Card.Body>
        </Card>
      ))}
      <Button variant="success" onClick={() => navigate("/")}>Takaisin etusivulle</Button>
    </div>
  );
};

export default KuittiPage;