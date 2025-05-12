 import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_URL;

// Hae käyttäjän id käyttäjänimen perusteella
export const haeKayttajaId = async (username) => {
  const token = localStorage.getItem("jwtToken");
  const kayttajanimi = username
  try {
    const response = await axios.get(`${API_BASE_URL}/kayttajat`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    const kayttajat = response.data;
    const kayttaja = kayttajat.find(u => u.kayttajanimi === kayttajanimi);
    console.log(kayttaja.kayttajaId)
    return kayttaja ? kayttaja.kayttajaId : null;
    

  } catch (error) {
    console.error("Virhe käyttäjiä haettaessa:", error);
    return null;
  }
}; 