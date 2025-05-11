import { useState, useEffect } from "react";
import axios from "axios";
import { Dialog, DialogTitle, DialogActions, DialogContent, TextField } from "@mui/material";
import { QRCodeSVG } from 'qrcode.react';
import { Card, CardContent, Typography } from "@mui/material";




const API_BASE_URL = process.env.REACT_APP_API_URL;

export default function NaytaOstotapahtuma({ valittuOstostapahtuma }) {
    const [open, setOpen] = useState(false);
    const [asiakastyypit, setAsiakastyypit] = useState([]);
    const [lipputyypit, setLipputyypit] = useState([]);
    const [tapahtumat, setTapahtumat] = useState([]);
    const [ostostapahtuma, setOstostapahtuma] = useState(valittuOstostapahtuma);
    const [liput, setLiput] = useState([]);
    useEffect(() => {
        const haeLiput = async () => {
            try {
                const token = localStorage.getItem("jwtToken");
                const [liputRes, lipputyypitRes, asiakastyypitRes, tapahtumatRes] = await Promise.all([
                    axios.get(`${API_BASE_URL}/liput`, { headers: { Authorization: `Bearer ${token}` } }),
                    axios.get(`${API_BASE_URL}/tapahtumalipputyypit`, { headers: { Authorization: `Bearer ${token}` } }),
                    axios.get(`${API_BASE_URL}/asiakastyypit`, { headers: { Authorization: `Bearer ${token}` } }),
                    axios.get(`${API_BASE_URL}/tapahtumat`, { headers: { Authorization: `Bearer ${token}` } })
                ]);
                const kaikkiLiput = liputRes.data;
                const ostostapahtuma = valittuOstostapahtuma.ostostapahtumaId
                const liput = kaikkiLiput.filter(lippu => lippu.ostostapahtumaId === parseInt(ostostapahtuma));
                setLiput(liput);
                setAsiakastyypit(asiakastyypitRes.data);
                setLipputyypit(lipputyypitRes.data);
                setTapahtumat(tapahtumatRes.data);
            } catch (error) {
                console.error("Lippujen haku epäonnistui:", error);
            }
        };
        haeLiput();
    }, [valittuOstostapahtuma]);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const haeTapahtumaNimi = (tapahtumaId) => {
        const tapahtuma = tapahtumat.find(t => t.tapahtumaId === tapahtumaId);
        return tapahtuma ? tapahtuma.tapahtumaNimi : "-";
    };

    const haeHinta = (tapahtumaLipputyyppiId) => {
        const tyyppi = lipputyypit.find(t => t.tapahtumaLipputyyppiId === tapahtumaLipputyyppiId);
        return tyyppi ? tyyppi.hinta : "-";
    };

    const haeAsiakastyyppi = (tapahtumaLipputyyppiId) => {
        const lipputyyppi = lipputyypit.find(t => t.tapahtumaLipputyyppiId === tapahtumaLipputyyppiId);
        const asiakastyyppi = asiakastyypit.find(a => a.asiakastyypiId === lipputyyppi?.asiakastyyppiId);
        return asiakastyyppi ? asiakastyyppi.asiakastyyppi : "-";
    };
    return (
        <>
            <button onClick={handleClickOpen}>Näytä</button>
            <Dialog
                open={open}
                onClose={handleClose}
            >
                <DialogTitle>Myyntitapahtuma</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Myyntitapahtuman numero"
                        type="Long"
                        fullWidth
                        variant="standard"
                        value={ostostapahtuma.ostostapahtumaId}
                    />
                    <TextField
                        label="Myyntiaika"
                        type="LocalDateTime"
                        fullWidth
                        variant="standard"
                        value={ostostapahtuma.myyntiaika}
                        margin="dense"
                    />
                    <TextField
                        label="Summa"
                        type="BicDecimal"
                        fullWidth
                        variant="standard"
                        value={ostostapahtuma.summa}
                        margin="dense"
                    />
                   <div style={{ marginTop: "1rem" }}>
                        {liput.map((lippu, index) => (
                            <Card key={index} variant="outlined" style={{ marginBottom: "1rem" }}>
                                <CardContent>
                                    <Typography variant="h6">Lippu ID: {lippu.lippuId}</Typography>
                                    <Typography>Tapahtuma: {haeTapahtumaNimi(lippu.tapahtumaId)}</Typography>
                                    <Typography>Hinta: {haeHinta(lippu.tapahtumaLipputyyppiId)} €</Typography>
                                    <Typography>Asiakastyyppi: {haeAsiakastyyppi(lippu.tapahtumaLipputyyppiId)}</Typography>
                                    <div style={{ marginTop: "1rem" }}>
                                        <QRCodeSVG
                                            value={`https://esimerkki.fi/lippu/${lippu.lippuId}`}
                                            size={128}
                                        />
                                    </div>
                                </CardContent>
                            </Card>
                        ))}
                    </div>
                </DialogContent>
                <DialogActions>
                    <button onClick={handleClose}>Sulje</button>
                </DialogActions>
            </Dialog>
        </>
    )
}