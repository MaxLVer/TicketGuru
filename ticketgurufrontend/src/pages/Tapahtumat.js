import React, { useState, useEffect } from "react";
import { AgGridReact } from "ag-grid-react";
import { Button, Dialog, DialogTitle, DialogContent } from "@mui/material";
import axios from "axios";
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community';
import TapahtumaLisays from "../components/TapahtumaLisays";
import TapahtumaMuokkaus from "../components/TapahtumaMuokkaus";
import { useNavigate } from "react-router-dom";
import TapahtumanLipputyypit from "../components/TapahtumanLipputyypit";

import "ag-grid-community/styles/ag-theme-material.css";
import TapahtumaTiedot from "../components/TapahtumaTiedot";

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE_URL = process.env.REACT_APP_API_URL;

function TapahtumaLista() {
  const [tapahtumat, setTapahtumat] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedTapahtumaId, setSelectedTapahtumaId] = useState(null);
  const [tiedotDialogAuki, setTiedotDialogAuki] = useState(false);
  const [tiedotTapahtuma, setTiedotTapahtuma] = useState(null);
  const [lipputyyppiDialogAuki, setLipputyyppiDialogAuki] = useState(false);
  const [lipputyyppiTapahtumaId, setLipputyyppiTapahtumaId] = useState(null);

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

  useEffect(() => {
    haeTapahtumat();
  }, []);

  const navigate = useNavigate();

  const [columnDefs, setColumnDefs] = useState([
    { field: "tapahtumaNimi", headerName: "Nimi" },
    { field: "kuvaus" },
    { field: "tapahtumaAika", headerName: "Aika", },
    {
      field: '_links.self.href',
      headerName: '',
      sortable: false,
      filter: false,
      cellRenderer: (params) => (
        <Button color="success" onClick={() => handleShowDetails(params.data)}>
          Näytä tiedot
        </Button>
      )
    },
    {
      field: '_links.self.href',
      headerName: '',
      sortable: false,
      filter: false,
      cellRenderer: params => (
        <Button color="success" onClick={() => handleOpenDialog(params.data)}>
          Muokkaa
        </Button>
      )
    },
    {
      field: '_links.self.href',
      headerName: '',
      sortable: false,
      filter: false,
      cellRenderer: params => (
        <Button color="success" onClick={() => handleOpenLipputyyppiDialog(params.data.tapahtumaId)}>
          Muokkaa Lipputyyppejä
        </Button>
      )
    },
    {
      field: '_links.self.href',
      headerName: '',
      sortable: false,
      filter: false,
      cellRenderer: params => (
        <Button color="success" onClick={() => navigate(`/raportti/${params.data.tapahtumaId}`)}>Raportti</Button>
      )
    },


  ]);

  const defaulColDef = {
    sortable: true,
    filter: true
  };

  const autoSizeStrategy = {
    type: 'fitGridWidth',
    defaultMinWidth: 200,
  };

  const handleShowDetails = (tapahtuma) => {
    setTiedotTapahtuma(tapahtuma);
    setTiedotDialogAuki(true);
  };

  const handleOpenLipputyyppiDialog = (tapahtumaId) => {
    setLipputyyppiTapahtumaId(tapahtumaId);
    setLipputyyppiDialogAuki(true);
  };

  const handleOpenDialog = (tapahtuma) => {
    if (tapahtuma && tapahtuma.tapahtumaId != null) {
      setSelectedTapahtumaId(tapahtuma.tapahtumaId);
    } else {
      setSelectedTapahtumaId(null); // Uuden tapahtuman ID on null ennen luontia, joten tämä ei herjaa virhettä
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    haeTapahtumat();
  };


  return (
    <div className="TapahtumaLista" style={{ position: "relative" }}>
      <div className="ag-theme-material" style={{ width: "100%", height: 800 }}>
        <AgGridReact
          rowData={tapahtumat}
          columnDefs={columnDefs}
          defaultColDef={defaulColDef}
          autoSizeStrategy={autoSizeStrategy}
        />
      </div>

      <Button
        variant="contained"
        color="success"
        onClick={handleOpenDialog}
        style={{
          position: "fixed",
          bottom: 20,
          right: 20,
          borderRadius: "50%",
          width: 60,
          height: 60,
          fontSize: 28,
          minWidth: 0
        }}
      >
        +
      </Button>
      <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
        <DialogTitle>
          {selectedTapahtumaId ? "Muokkaa tapahtumaa" : "Lisää tapahtuma"}
        </DialogTitle>
        <DialogContent>
          {selectedTapahtumaId != null ? (
            <TapahtumaMuokkaus tapahtumaId={selectedTapahtumaId} onSuccess={handleCloseDialog} />
          ) : (
            <TapahtumaLisays onSuccess={handleCloseDialog} />
          )}
        </DialogContent>
      </Dialog>
      <Dialog
        open={tiedotDialogAuki}
        onClose={() => setTiedotDialogAuki(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>Tapahtuman tiedot</DialogTitle>
        <DialogContent>
          {tiedotTapahtuma && <TapahtumaTiedot tapahtuma={tiedotTapahtuma} />}
        </DialogContent>
      </Dialog>

      <Dialog
        open={lipputyyppiDialogAuki}
        onClose={() => setLipputyyppiDialogAuki(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>Muokkaa lipputyyppejä</DialogTitle>
        <DialogContent>
          {lipputyyppiTapahtumaId && (
            <TapahtumanLipputyypit
              tapahtumaId={lipputyyppiTapahtumaId}
              onSuccess={() => {
                setLipputyyppiDialogAuki(false);
                haeTapahtumat(); // päivitetään lista jos tarpeen
              }}
            />
          )}
        </DialogContent>
      </Dialog>
    </div>


  );
}

export default TapahtumaLista;