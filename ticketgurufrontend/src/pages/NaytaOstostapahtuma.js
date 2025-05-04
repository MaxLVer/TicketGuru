import { useState, useEffect } from "react";
import axios from "axios";
import { Dialog, DialogTitle, DialogActions, DialogContent, TextField } from "@mui/material";
import { AgGridReact } from "ag-grid-react";
export default function NaytaOstotapahtuma({API_BASE_URL, valittuOstostapahtuma }) {
    const [open, setOpen] = useState(false);
    const [ostostapahtuma, setOstostapahtuma] = useState(valittuOstostapahtuma);
    const [liput, setLiput] = useState([]);
    useEffect(() => {
        const haeLiput = async () => {
            try {
                const token = localStorage.getItem("jwtToken");
                const res = await axios.get(`${API_BASE_URL}/liput`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setLiput(res.data);
            } catch (error) {
                console.error("Lippujen haku epäonnistui:", error);
            }
        };
        haeLiput();
    }, []);

    const [columnDefs, setCoumnDefs] = useState([
        { field: 'lippuId' },
        { field: 'tapahtumaId' }
    ]);
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