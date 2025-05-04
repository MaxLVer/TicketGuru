import React, { useState, useEffect } from "react";
import { AgGridReact } from "ag-grid-react";
import { Button } from "@mui/material";
import axios from "axios";
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community'; 

import "ag-grid-community/styles/ag-theme-material.css";

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE_URL = process.env.REACT_APP_API_URL;

function TapahtumaLista () {
    const [tapahtumat, setTapahtumat] = useState([]);
    useEffect(() => {
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
        haeTapahtumat();
      }, []);
      const [columnDefs, setColumnDefs] = useState([
        { field: "kuvaus" },
        { field: "tapahtumaAika", headerName: "Aika", },
        { field: "tapahtumaNimi", headerName: "Nimi" },
        {
            field: '_links.self.href',
            headerName: '',
            sortable: false,
            filter: false,
            cellRenderer: params => <Button>Muokkaa</Button>
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

    return (
        <div className="TapahtumaLista">
            <div className="ag-theme-material" style={{ width: "100%", height: 800 }}>
                <AgGridReact
                    rowData={tapahtumat}
                    columnDefs={columnDefs}
                    defaultColDef={defaulColDef}
                    autoSizeStrategy={autoSizeStrategy}
                    
                />
            </div>
        </div>
    )
}

export default TapahtumaLista