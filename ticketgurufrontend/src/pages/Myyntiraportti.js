import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

const API_BASE_URL = process.env.REACT_APP_API_URL;

function Myyntiraportti() {
  const { tapahtumaId } = useParams();
  const [raportti, setRaportti] = useState([]);
  const [tapahtumaNimi, setTapahtumaNimi] = useState("");

  console.log("TapahtumaId:", tapahtumaId);
  
  useEffect(() => {
    const haeRaportti = async () => {
      try {
        const token = localStorage.getItem("jwtToken");

        
const res = await axios.get(`${API_BASE_URL}/liput`, {
  headers: {
    Authorization: `Bearer ${token}`,
  },
});
localStorage.getItem("jwtToken")

        // Hae kaikki tiedot
        const [liputRes, lipputyypitRes, asiakastyypitRes, tapahtumatRes] = await Promise.all([
          axios.get(`${API_BASE_URL}/liput`, { headers: { Authorization: `Bearer ${token}` } }),
          axios.get(`${API_BASE_URL}/tapahtumalipputyypit`, { headers: { Authorization: `Bearer ${token}` } }),
          axios.get(`${API_BASE_URL}/asiakastyypit`, { headers: { Authorization: `Bearer ${token}` } }),
          axios.get(`${API_BASE_URL}/tapahtumat`, { headers: { Authorization: `Bearer ${token}` } })
        ]);

        const kaikkiLiput = liputRes.data;
        const lipputyypit = lipputyypitRes.data;
        const asiakastyypit = asiakastyypitRes.data;
        const kaikkiTapahtumat = tapahtumatRes.data;

        const tapahtuma = kaikkiTapahtumat.find(t => t.tapahtumaId === parseInt(tapahtumaId));
        if (tapahtuma) setTapahtumaNimi(tapahtuma.tapahtumaNimi);

        // Suodata liput tapahtumalle
        const liput = kaikkiLiput.filter(lippu => lippu.tapahtumaId === parseInt(tapahtumaId));
        const kooste = {};
        

        

        

        liput.forEach((lippu) => {
          const lipputyyppi = lipputyypit.find(lt => lt.tapahtumaLipputyyppiId === lippu.tapahtumaLipputyyppiId);
          if (!lipputyyppi) return;

          const asiakastyyppi = asiakastyypit.find(at => at.asiakastyyppiId === lipputyyppi.asiakastyyppiId);
          const tyyppiNimi = asiakastyyppi ? asiakastyyppi.asiakastyyppi : "Tuntematon";

                console.log("Lippu:", lippu);
      console.log("Lipputyyppi:", lipputyyppi);
    

          if (!kooste[tyyppiNimi]) {
            kooste[tyyppiNimi] = { kpl: 0, summa: 0, hinta: lipputyyppi.hinta };
          }

          kooste[tyyppiNimi].kpl += 1;
          kooste[tyyppiNimi].summa += lipputyyppi.hinta;
        });

        const raporttiData = Object.entries(kooste).map(([lipputyyppi, data]) => ({
          lipputyyppi,
          kpl: data.kpl,
          summa: data.summa
        }));
console.log("Koottu raportti:", raporttiData);
        setRaportti(raporttiData);
      } catch (err) {
        console.error("Myyntiraportin haku epäonnistui:", err);
      }
      
    };

    haeRaportti();
  }, [tapahtumaId]);

  
        const navigate = useNavigate();

      

  return (
    <div style={{ padding: 24 }}>
      <Typography variant="h4" gutterBottom>
        Myyntiraportti – {tapahtumaNimi}
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Lipputyyppi</TableCell>
              <TableCell>Myyty kpl</TableCell>
              <TableCell>Myynti yhteensä (€)</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {raportti.map((rivi, idx) => (
              <TableRow key={idx}>
                <TableCell>{rivi.lipputyyppi}</TableCell>
                <TableCell>{rivi.kpl}</TableCell>
                <TableCell>{rivi.summa}</TableCell>
              </TableRow>
            ))}
            {raportti.length === 0 && (
              <TableRow>
                <TableCell colSpan={3}>Ei myyntitietoja.</TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
        <Button onClick={() => navigate("/tapahtumat")}>Palaa</Button>
        <Button onClick={() => navigate(`/myyntitapahtumat/${tapahtumaId}`)}>Myyntitapahtumat</Button>
    </div>
  );
}

export default Myyntiraportti;
