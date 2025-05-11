/* import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_URL;

export const haeKayttajaId = async (username) => {
  const token = localStorage.getItem("jwtToken");

  try {
    const response = await axios.get(`${API_BASE_URL}/kayttajat`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    const kayttajat = response.data;
    const kayttaja = kayttajat.find(u => u.kayttajatunnus === username);

    return kayttaja ? kayttaja.kayttajaId : null;

  } catch (error) {
    console.error("Virhe käyttäjiä haettaessa:", error);
    return null;
  }
}; */