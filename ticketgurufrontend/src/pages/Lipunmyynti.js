import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { Button, Form, Card, Col, Row, Alert, Spinner } from "react-bootstrap";
import { QRCodeSVG } from "qrcode.react";
import "../App.css";
import { useNavigate } from "react-router-dom";


const API_BASE_URL = process.env.REACT_APP_API_URL;

const TicketSaleApp = () => {
  const [tapahtumat, setTapahtumat] = useState([]);
  const [valittuTapahtuma, setValittuTapahtuma] = useState(null);
  const [lippujenMaara, setLippujenMaara] = useState(1);
  const [asiakas, setAsiakas] = useState({ etunimi: "", sukunimi: "" });
  const [isLoading, setIsLoading] = useState(false);
  const [ostoStatus, setOstoStatus] = useState(null);
  const [lipputiedot, setLipputiedot] = useState({
    etunimi: "",
    sukunimi: "",
    tapahtumaNimi: "",
    kokonaisMaara: 0,
    qrUrl: "",
  });
  const [ostostapahtumaId, setOstostapahtumaId] = useState(null);
  const [tapahtumaLipputyypit, setTapahtumaLipputyypit] = useState([]);
  const [valittuLipputyyppiId, setValittuLipputyyppiId] = useState(null);
  const [asiakastyypit, setAsiakastyypit] = useState([]);
  const [ostosKori, setOstosKori] = useState([]);
  const [kuitti, setKuitti] = useState(null); 

  const navigate = useNavigate();

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
  // Osta liput
  const ostaLiput = async () => {
    // Tarkistetaan, että tarvittavat kentät ovat täytettyjä
    if (
      !asiakas.etunimi ||
      !asiakas.sukunimi ||
      !valittuTapahtuma ||
      !lippujenMaara
    ) {
      alert("Tarkista, että kaikki kentät on täytetty oikein.");
      return;
    }

    /* if (!valittuTapahtuma.tapahtumaLipputyyppiId) {
      alert("Tälle tapahtumalle ei ole määritelty lipputyyppiä. Lisää lipputyyppi ensin.");
      return;
    } */

    setIsLoading(true);
    setOstoStatus(null);

    try {
      const token = localStorage.getItem("jwtToken");

      //Luodaan lähetettävä lippu objekti
      const lippu = {
        tapahtumaId: valittuTapahtuma.tapahtumaId,
        tapahtumaLipputyyppiId: valittuLipputyyppiId,
        maara: Number(lippujenMaara),
        ostostapahtumaId: ostostapahtumaId,
        asiakas: {
          etunimi: asiakas.etunimi,
          sukunimi: asiakas.sukunimi,
        },
      };

      console.log("Lähetettävä lippu:", lippu);

      //Lähetetään pyyntö API:lle
      const res = await axios.post(`${API_BASE_URL}/liput`, lippu, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      // Jos pyyntö onnistui, käsitellään vastaus
      setOstoStatus("onnistui");
      console.log(res.data);
      // Tallennetaan lipputiedot vastausobjektista
      // Vastausobjektista ei löydy tietoja, jotka on asetettu näytettäväksi(katso API-dokumentaatio)
      // Nyt lipputiedot otetaan suoraan syötteestä
      //setLipputiedot(res.data);

      setLipputiedot({
        etunimi: asiakas.etunimi,
        sukunimi: asiakas.sukunimi,
        tapahtumaNimi: valittuTapahtuma.tapahtumaNimi,
        kokonaisMaara: Number(lippu.maara),
        qrUrl: res.data.qrUrl,
      });

      navigate("/kuitti", { state: { lipputiedot } });

      setTimeout(() => {
        tyhjennäKentät();
      }, 0);
      console.log(lipputiedot);
    } catch (error) {
      // Jos pyyntö epäonnistuu, tarkistetaan virhe
      console.error(
        "Osto epäonnistui:",
        error.response || error.message || error
      );
      // Näytetään virheilmoitus käyttäjälle
      setOstoStatus("virhe");
      alert("Lippujen osto epäonnistui. Tarkista tiedot ja yritä uudelleen.");
    } finally {
      // Poistetaan lataus-tilan päivitys
      setIsLoading(false);
    }
  };

  const valitseTapahtumaJaLuoOstostapahtuma = async (tapahtuma) => {
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

      const decoded = jwtDecode(token);
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
      maara: Number(lippujenMaara),
      asiakas: { ...asiakas },
    };

    setOstosKori((prev) => [...prev, uusiRivi]);

    tyhjennäKentät();
  };

  const poistaKorista = (index) => {
    setOstosKori((prev) => prev.filter((_, i) => i !== index));
  };

  const ostaKokoKori = async () => {
    if (ostosKori.length === 0) {
      alert("Ostoskorissa ei ole lippuja.");
      return;
    }

    setIsLoading(true);
    setOstoStatus(null);

    try {
      const token = localStorage.getItem("jwtToken");

      // Luodaan lähetettävä ostoskori-data
      const data = ostosKori.map((rivi) => ({
        tapahtumaId: rivi.tapahtumaId,
        tapahtumaLipputyyppiId: rivi.tapahtumaLipputyyppiId,
        maara: rivi.maara,
        ostostapahtumaId: ostostapahtumaId,
        koodi: rivi.koodi || "", // Jos koodi puuttuu, varmista sen arvo!
        status: "MYYTY", // tai määrittele oletusarvo, jos status on pakollinen
      }));

      console.log("Lahetettava ostoskori:", data);

      console.log("Token:", token); // tarkistusta varten

      const res = await axios.post(`${API_BASE_URL}/liput/kori`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        withCredentials: true,
      });

      setOstoStatus("onnistui");
      console.log(res.data);

       // Create a combined receipt for all items in the cart
    const combinedReceipt = ostosKori.map((item) => ({
      tapahtumaNimi: item.tapahtumaNimi,
      lipputyyppi: item.tapahtumaLipputyyppiId,
      asiakasNimi: `${item.asiakas.etunimi} ${item.asiakas.sukunimi}`,
      lippujenMaara: item.maara,
    }));

    setKuitti(combinedReceipt);

    // Clear the cart after purchase
    setTimeout(() => {
      setOstosKori([]);
      setValittuTapahtuma(null);
      setValittuLipputyyppiId(null);
      setLippujenMaara(1);
    }, 0);

    navigate("/kuitti", { state: { kuitti: combinedReceipt } });

    } catch (err) {
      console.error("Osto epaonnistui:", err.response || err.message || err);
      setOstoStatus("virhe");
      alert(
        "Ostoskorin osto ei onnistunut. Tarkista tiedot ja yrita uudelleen."
      );
    } finally {
      setIsLoading(false);
    }
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
              {rivi.tapahtumaNimi} ({rivi.maara} kpl) – {rivi.asiakas.etunimi} {rivi.asiakas.sukunimi}
            </div>
          </li>
        ))}
      </ul>

      <div className="cart-actions">
        <Button
          variant="primary"
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
                      <strong>Lippuja jäljellä:</strong>{" "}
                      {t.jaljellaOlevaLippumaara} / {t.kokonaislippumaara}
                    </Card.Text>
                    <Button
                      onClick={() => valitseTapahtumaJaLuoOstostapahtuma(t)}
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
                    //Debuggaus logi
                    //console.log("Valittu id on " + valittuLipputyyppiId)
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
                          (a) => a.asiakastyypiId === lt.asiakastyyppiId
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
              className="me-2"
              onClick={ostaLiput} disabled={isLoading}>
                {isLoading ? <Spinner size="sm" animation="border" /> : "Osta"}
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

        {ostoStatus === "onnistui" && lipputiedot && (
          <div className="mt-4">
            <Alert variant="success">
              <h4>Osto onnistui!</h4>
              <p>
                <strong>Etunimi:</strong> {lipputiedot?.etunimi}
              </p>
              <p>
                <strong>Sukunimi:</strong> {lipputiedot?.sukunimi}
              </p>
              <p>
                <strong>Tapahtuma:</strong> {valittuTapahtuma?.tapahtumaNimi}
              </p>
              <p>
                <strong>Lippuja:</strong> {lipputiedot?.kokonaisMaara}
              </p>
              {lipputiedot?.qrUrl && (
                <>
                  <h5>QR-koodi:</h5>
                  <QRCodeSVG value={lipputiedot.qrUrl} size={200} />
                </>
              )}
            </Alert>
            <Button
              variant="secondary"
              onClick={() => window.location.reload()}
            >
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
    </>
  );
};

export default TicketSaleApp;
