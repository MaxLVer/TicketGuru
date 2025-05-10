import { useState, useEffect } from "react";
import axios from "axios";
import { Dialog, DialogTitle, DialogActions, DialogContent, TextField } from "@mui/material";
import { AgGridReact } from "ag-grid-react";

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
    }, []);
    console.log("Tapahtumat:", tapahtumat);
    const [columnDefs, setColumnDefs] = useState([]);
    useEffect(() => {
        if (tapahtumat.length > 0) {
            setColumnDefs([
                { field: 'lippuId' },
                {
                    headerName: 'Tapahtuma',
                    field: 'tapahtumaId',
                    valueGetter: (params) => {
                        console.log("Row data:", params.data);
                        const tapahtuma = tapahtumat.find(t => t.tapahtumaId === params.data.tapahtumaId);
                        return tapahtuma ? tapahtuma.tapahtumaNimi : '-:-';
                    }
                },
                {
                    headerName: 'Hinta',
                    field: 'tapahtumaLipputyyppiId',
                    valueGetter: (params) => {
                        console.log("Row data:", params.data);
                        const lipputyyppi = lipputyypit.find(t => t.tapahtumaLipputyyppiId === params.data.tapahtumaLipputyyppiId);
                        return lipputyyppi ? lipputyyppi.hinta : '-:-';
                    }
                },
                {
                    headerName: 'Asiakastyyppi',
                    field: 'tapahtumaLipputyyppiId',
                    valueGetter: (params) => {
                        console.log("Row data:", params.data);
                        const asiakastyyppiId = lipputyypit.find(t => t.tapahtumaLipputyyppiId === params.data.tapahtumaLipputyyppiId);
                        const asiakastyyppi = asiakastyypit.find(t => t.asiakastyypiId === params.data.tapahtumaLipputyyppiId);
                        return asiakastyyppi ? asiakastyyppi.asiakastyyppi : '-:-';
                    }
                }
            ]);
        }
    }, [tapahtumat]);
    const defaultColumnDefs = {
        sortable: true,
        filter: true
    };

    const autoSizeStrategy = {
        type: 'fitCellContents',
    }
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
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
                    />
                    <TextField
                        label="Summa"
                        type="BicDecimal"
                        fullWidth
                        variant="standard"
                        value={ostostapahtuma.summa}
                    />
                    <div className="ag-theme-material" style={{ width: "100%", height: 800 }}>
                        <AgGridReact
                            rowData={liput}
                            columnDefs={columnDefs}
                            defaultColDef={defaultColumnDefs}
                            autoSizeStrategy={autoSizeStrategy}
                        />
                    </div>
                </DialogContent>
                <DialogActions>
                    <button onClick={handleClose}>Sulje</button>
                </DialogActions>
            </Dialog>
        </>
    )
}