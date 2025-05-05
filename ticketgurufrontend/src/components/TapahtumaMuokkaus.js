import React, { useState, useEffect } from "react";
import axios from "axios";
import "../styles/properForm.css";

const API_BASE_URL = process.env.REACT_APP_API_URL;

const TapahtumaMuokkaus = ({ tapahtumaId, onSuccess }) => {
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

    // Hae muokattava tapahtuma
    axios
      .get(`${API_BASE_URL}/tapahtumat/${tapahtumaId}`, authHeaders)
      .then((res) => {
        setTapahtumaData(res.data); // Täytetään lomake haetuilla tiedoilla
      })
      .catch((err) => {
        console.error("Tapahtuman haku epäonnistui:", err);
      });
  }, [tapahtumaId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTapahtumaData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    try {
      // Lähetetään PUT-pyyntö tapahtuman päivittämiseksi
      const response = await axios.put(
        `${API_BASE_URL}/tapahtumat/${tapahtumaId}`,
        tapahtumaData,
        authHeaders
      );
      alert("Tapahtuma päivitetty!");
      if (typeof onSuccess === "function") {
        onSuccess(); // esim. sulje dialogi ja päivitä lista
      }
    } catch (err) {
      console.error("Tapahtuman päivitys epäonnistui:", err);
      if (err.response && err.response.status === 400) {
        setErrors(err.response.data);
      } else {
        alert("Tapahtuman päivitys epäonnistui.");
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="form-container">
      <h2 className="form-title">Muokkaa tapahtumaa</h2>

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
        Tallenna muutokset
      </button>
    </form>
  );
};

export default TapahtumaMuokkaus;
