import React, { useState, useEffect } from "react";
import axios from "axios";

import "../styles/properForm.css";

const API_BASE_URL = process.env.REACT_APP_API_URL;

const TapahtumaLisays = ({ onSuccess }) => {
  const [tapahtumaData, setTapahtumaData] = useState({
    tapahtumapaikkaId: "",
    tapahtumaAika: "",
    tapahtumaNimi: "",
    kuvaus: "",
    kokonaislippumaara: "",
    jaljellaOlevaLippumaara: "",
  });

  const [errors, setErrors] = useState({});
  const [tapahtumapaikat, setTapahtumapaikat] = useState([]);

  const token = localStorage.getItem("jwtToken");
  const authHeaders = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  useEffect(() => {
    axios
      .get(`${API_BASE_URL}/tapahtumapaikat`, authHeaders)
      .then((res) => {
        setTapahtumapaikat(res.data);
      })
      .catch((err) => {
        console.error("Tapahtumapaikkojen haku epäonnistui:", err);
      });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTapahtumaData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    try {
      const response = await axios.post(
        `${API_BASE_URL}/tapahtumat`,
        tapahtumaData,
        authHeaders
      );
      alert("Tapahtuma lisätty!");
      setTapahtumaData({
        tapahtumapaikkaId: "",
        tapahtumaAika: "",
        tapahtumaNimi: "",
        kuvaus: "",
        kokonaislippumaara: "",
        jaljellaOlevaLippumaara: "",
      });

      if (typeof onSuccess === "function") {
        onSuccess(); // esim. sulje dialogi ja päivitä lista
      }
    } catch (err) {
      console.error("Tapahtuman lisäys epäonnistui:", err);
      if (err.response && err.response.status === 400) {
        setErrors(err.response.data);
      } else {
        alert("Tapahtuman lisäys epäonnistui.");
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="form-container">
      <h2 className="form-title">Lisää uusi tapahtuma</h2>

      <div className="form-group">
        <label>Tapahtumapaikka:</label>
        <select
          name="tapahtumapaikkaId"
          value={tapahtumaData.tapahtumapaikkaId}
          onChange={handleChange}
          required
        >
          <option key="valitse" value="">Valitse</option>
          {tapahtumapaikat.map((paikka) => (
            <option key={paikka.tapahtumapaikkaId} value={paikka.tapahtumapaikkaId}>
              {paikka.tapahtumapaikanNimi}
            </option>
          ))}
        </select>
        {errors.tapahtumapaikkaId && (
          <div className="error-text">{errors.tapahtumapaikkaId}</div>
        )}
      </div>

      <div className="form-group">
        <label>Aika:</label>
        <input
          type="datetime-local"
          name="tapahtumaAika"
          value={tapahtumaData.tapahtumaAika}
          onChange={handleChange}
          required
        />
        {errors.tapahtumaAika && (
          <div className="error-text">{errors.tapahtumaAika}</div>
        )}
      </div>

      <div className="form-group">
        <label>Nimi:</label>
        <input
          type="text"
          name="tapahtumaNimi"
          value={tapahtumaData.tapahtumaNimi}
          onChange={handleChange}
          required
        />
        {errors.tapahtumaNimi && (
          <div className="error-text">{errors.tapahtumaNimi}</div>
        )}
      </div>

      <div className="form-group">
        <label>Kuvaus:</label>
        <textarea
          name="kuvaus"
          value={tapahtumaData.kuvaus}
          onChange={handleChange}
        />
        {errors.kuvaus && <div className="error-text">{errors.kuvaus}</div>}
      </div>

      <div className="form-group">
        <label>Kokonaislippumäärä:</label>
        <input
          type="number"
          name="kokonaislippumaara"
          value={tapahtumaData.kokonaislippumaara}
          onChange={handleChange}
          required
        />
        {errors.kokonaislippumaara && (
          <div className="error-text">{errors.kokonaislippumaara}</div>
        )}
      </div>

      <div className="form-group">
        <label>Jäljellä olevat liput:</label>
        <input
          type="number"
          name="jaljellaOlevaLippumaara"
          value={tapahtumaData.jaljellaOlevaLippumaara}
          onChange={handleChange}
        />
        {errors.jaljellaOlevaLippumaara && (
          <div className="error-text">{errors.jaljellaOlevaLippumaara}</div>
        )}
      </div>

      <button type="submit" className="submit-button">
        Lisää tapahtuma
      </button>
    </form>
  );
};

export default TapahtumaLisays;
