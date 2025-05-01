import React from "react";
import { useLocation } from "react-router-dom";
import { Alert, Button } from "react-bootstrap";
import { QRCodeSVG } from "qrcode.react";

const KuittiPage = () => {
  const location = useLocation();
  const lipputiedot = location.state?.lipputiedot;

  if (!lipputiedot) {
    return <p>Ei lippuja näytettäväksi.</p>;
  }

  return (
    <div className="mt-4">
      <Alert variant="success">
        <h4>Osto onnistui!</h4>
        <p><strong>Etunimi:</strong> {lipputiedot.etunimi}</p>
        <p><strong>Sukunimi:</strong> {lipputiedot.sukunimi}</p>
        <p><strong>Tapahtuma:</strong> {lipputiedot.tapahtumaNimi}</p>
        <p><strong>Lippuja:</strong> {lipputiedot.kokonaisMaara}</p>
        {lipputiedot.qrUrl && (
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
  );
};

export default KuittiPage;
