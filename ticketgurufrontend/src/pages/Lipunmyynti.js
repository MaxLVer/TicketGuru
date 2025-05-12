import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { Button, Form, Card, Col, Row, Alert, Spinner } from "react-bootstrap";
import { QRCodeSVG } from "qrcode.react";
import "../App.css";
import { useLocation, useNavigate } from "react-router-dom";

const API_BASE_URL = process.env.REACT_APP_API_URL;

const TicketSaleApp = () => {
  const [tapahtumat, setTapahtumat] = useState([]);
  const [valittuTapahtuma, setValittuTapahtuma] = useState(null);
  const [lippujenMaara, setLippujenMaara] = useState(1);
  const [asiakas, setAsiakas] = useState({ etunimi: "", sukunimi: "" });
  const [isLoading, setIsLoading] = useState(false);
  const [ostostapahtumaId, setOstostapahtumaId] = useState(null);
  const [tapahtumaLipputyypit, setTapahtumaLipputyypit] = useState([]);
  const [valittuLipputyyppiId, setValittuLipputyyppiId] = useState(null);
  const [asiakastyypit, setAsiakastyypit] = useState([]);
  const [ostosKori, setOstosKori] = useState([]);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const tallennettuKori = location.state?.ostosKori;
    if (Array.isArray(tallennettuKori)) {
      setOstosKori(tallennettuKori);
    }
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
  }, [location.state]);

  // Lataa asiakastyypit API:sta
  const haeAsiakastyypit = async () => {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.get(`${API_BASE_URL}/asiakastyypit`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setAsiakastyypit(response.data);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        alert("Et ole valtuutettu katsomaan asiakastyyppitietoja.");
      } else {
        console.error("Asiakastyyppejä ei saatu haettua", error);
      }
    }
  };

  const muutaAsiakasTieto = (e) => {
    const { name, value } = e.target;
    setAsiakas((prev) => ({ ...prev, [name]: value }));
  };

  const tyhjennäKentät = () => {
    setAsiakas({ etunimi: "", sukunimi: "" });
    setValittuLipputyyppiId(null);
    setLippujenMaara(1);
  };


  const valitseTapahtuma = async (tapahtuma) => {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
      alert("Kirjaudu sisään uudelleen. Token puuttuu.");
      return;
    }

    try {
      setValittuTapahtuma({
        ...tapahtuma,
        lipputyyppiId:
          tapahtuma.lipputyypit?.[0]?.tapahtumaLipputyyppiId ||
          tapahtuma.lipputyyppiId,
      });

      setIsLoading(true);


      const lipputyyppiRes = await axios.get(
        `${API_BASE_URL}/tapahtumat/${tapahtuma.tapahtumaId}/lipputyypit`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          withCredentials: true,
        }
      );
      setTapahtumaLipputyypit(lipputyyppiRes.data);
    } catch (error) {
      console.error(" Ostostapahtuman luonti epäonnistui:", error);
      alert("Ostostapahtuman luonti epäonnistui!");
    } finally {
      setIsLoading(false);
    }
  };

  //OSTOSKORI
  const lisaaOstoskoriin = () => {
    if (
      !asiakas.etunimi ||
      !asiakas.sukunimi ||
      !valittuTapahtuma ||
      !valittuLipputyyppiId ||
      !lippujenMaara
    ) {
      alert("Tayta kaikki tiedot ennen lisaamista.");
      return;
    }

    const uusiRivi = {
      tapahtumaId: valittuTapahtuma.tapahtumaId,
      tapahtumaNimi: valittuTapahtuma.tapahtumaNimi,
      tapahtumaLipputyyppiId: valittuLipputyyppiId,
      ostostapahtumaId: ostostapahtumaId,
      maara: Number(lippujenMaara),
      asiakas: { ...asiakas },
    };

    setOstosKori((prev) => [...prev, uusiRivi]);

    tyhjennäKentät();
  };


  useEffect(() => {
    const haeTapahtumaLipputyypit = async () => {
      const token = localStorage.getItem("jwtToken");
      if (!valittuTapahtuma?.tapahtumaId) return;

      try {
        const response = await axios.get(
          `${API_BASE_URL}/tapahtumat/${valittuTapahtuma.tapahtumaId}/lipputyypit`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setTapahtumaLipputyypit(response.data);
      } catch (error) {
        console.error("Lipputyyppejä ei saatu haettua", error);
      }
    };

    haeAsiakastyypit();
    if (valittuTapahtuma) {
      haeTapahtumaLipputyypit();
    }
  }, [valittuTapahtuma]);

  const siirryYhteenvetoon = () => {
    navigate("/yhteenveto", { state: { ostosKori } });
  };

  return (
    <>
      <div className="cart-box">
        <h6>Ostoskorissa:</h6>

        {ostosKori.length === 0 ? (
          <p>Ostoskorisi on tyhjä.</p>
        ) : (
          <>
            <ul className="cart-list">
              {ostosKori.map((rivi, i) => (
                <li key={i} className="cart-item">
                  <div className="cart-info">
                    {rivi.tapahtumaNimi} ({rivi.maara} kpl) –{" "}
                    {rivi.asiakas.etunimi} {rivi.asiakas.sukunimi}
                  </div>
                </li>
              ))}
            </ul>

            <div className="cart-actions">
              <Button
                variant="success"
                size="sm"
                onClick={siirryYhteenvetoon}
                disabled={isLoading}
              >
                Osta
              </Button>
            </div>
          </>
        )}
      </div>
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
                      <strong>Kuvaus:</strong> {t.kuvaus}
                      <br />
                      <strong>Aika:</strong>{" "}
                      {new Date(t.tapahtumaAika).toLocaleString()}
                      <br />
                    </Card.Text>
                    <Button
                    variant="success"
                      onClick={() => valitseTapahtuma(t)}
                    >
                      Valitse tapahtuma
                    </Button>
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

              <Form.Group className="mb-2">
                <Form.Label>Lipputyyppi</Form.Label>
                <Form.Control
                  as="select"
                  value={valittuLipputyyppiId || ""}
                  onChange={(e) => {
                    const value = e.target.value;
                    setValittuLipputyyppiId(value ? parseInt(value) : null);
                  }}
                >
                  {!tapahtumaLipputyypit.length ? (
                    <option value="">Ladataan lipputyyppejä...</option>
                  ) : (
                    <>
                      <option value="" disabled>
                        Valitse lipputyyppi
                      </option>
                      {tapahtumaLipputyypit.map((lt) => {
                        // Etsitään asiakastyyppi tästä lipputyyppistä
                        const asiakasTyyppi = asiakastyypit.find(
                          (a) => a.asiakastyyppiId === lt.asiakastyyppiId
                        );

                        return (
                          <option
                            key={lt.tapahtumaLipputyyppiId}
                            value={lt.tapahtumaLipputyyppiId}
                          >
                            {asiakasTyyppi
                              ? `${asiakasTyyppi.asiakastyyppi} -  (${lt.hinta}€)`
                              : `${lt.nimi} (${lt.hinta}€)`}
                          </option>
                        );
                      })}
                    </>
                  )}
                </Form.Control>
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

              <Button
              variant="success"
                className="me-2"
                onClick={() => {
                  lisaaOstoskoriin();
                  setValittuTapahtuma(null); // Tämä palauttaa käyttäjän lipunmyyntinäkymään
                }}
                disabled={isLoading}
              >
                Lisaa koriin
              </Button>

              <Button
                variant="outline-danger"
                onClick={() => setValittuTapahtuma(null)}
              >
                Palaa
              </Button>
            </Form>
          </div>
        )}


      </div>
    </>
  );
};

export default TicketSaleApp;
