
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { Button, Typography, Table, TableHead, TableBody, TableRow, TableCell, Paper } from "@mui/material";

const API_BASE_URL = process.env.REACT_APP_API_URL;

function Myyntiraportti() {
  const { id } = useParams(); // tapahtumaId
  const [raportti, setRaportti] = useState([]);
  const [tapahtuma, setTapahtuma] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const haeData = async () => {
      try {
        const token = localStorage.getItem("jwtToken");

        const tapahtumaRes = await axios.get(`${API_BASE_URL}/tapahtumat/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setTapahtuma(tapahtumaRes.data);

        const raporttiRes = await axios.get(`${API_BASE_URL}/raportti/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setRaportti(raporttiRes.data); // olettaen että palauttaa [{lipputyyppi: "Aikuinen", kpl: 102, summa: 1530.00}, ...]
      } catch (error) {
        console.error("Virhe raportin haussa:", error);
      }
    };

    haeData();
  }, [id]);

  const yhteensaKpl = raportti.reduce((sum, r) => sum + r.kpl, 0);
  const yhteensaSumma = raportti.reduce((sum, r) => sum + r.summa, 0);

  return (
    <Paper style={{ padding: 24 }}>
      {tapahtuma && (
        <>
          <Typography variant="h5" gutterBottom>
            {tapahtuma.tapahtumaNimi}, {tapahtuma.paikka}
          </Typography>
          <Typography variant="subtitle1">
            {new Date(tapahtuma.tapahtumaAika).toLocaleString()}
          </Typography>
        </>
      )}

      <Typography variant="h6" style={{ marginTop: 24 }}>Myyntiraportti</Typography>

      <Table style={{ marginTop: 12 }}>
        <TableHead>
          <TableRow>
            <TableCell>Lipputyyppi</TableCell>
            <TableCell>Kpl</TableCell>
            <TableCell>Yhteensä</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {raportti.map((rivi, index) => (
            <TableRow key={index}>
              <TableCell>{rivi.lipputyyppi}</TableCell>
              <TableCell>{rivi.kpl}</TableCell>
              <TableCell>{rivi.summa.toFixed(2)}€</TableCell>
            </TableRow>
          ))}
          <TableRow>
            <TableCell><strong>Yhteensä</strong></TableCell>
            <TableCell><strong>{yhteensaKpl}</strong></TableCell>
            <TableCell><strong>{yhteensaSumma.toFixed(2)}€</strong></TableCell>
          </TableRow>
        </TableBody>
      </Table>

      <Button
        variant="contained"
        style={{ marginTop: 20 }}
        onClick={() => navigate(`/myyntitapahtumat/${id}`)}
      >
        Myyntitapahtumat
      </Button>
    </Paper>
  );
}

export default Myyntiraportti;
