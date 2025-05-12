import React, { useState, useEffect } from "react";
import { AgGridReact } from "ag-grid-react";
import { Button, Dialog, DialogTitle, DialogContent } from "@mui/material";
import axios from "axios";
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community';
import "ag-grid-community/styles/ag-theme-material.css";
import { useNavigate } from "react-router-dom";

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE_URL = process.env.REACT_APP_API_URL;

function LiputLista() {
    const [liput, setLiput] = useState([]);
    const [tapahtumat, setTapahtumat] = useState([]);
    const [columnDefs, setColumnDefs] = useState([]);
    

    const haeLiput = async () => {
        try {
            const token = localStorage.getItem("jwtToken");
            const [liputRes, tapahtumatRes] = await Promise.all([
                axios.get(`${API_BASE_URL}/liput`, { headers: { Authorization: `Bearer ${token}` } }),
                axios.get(`${API_BASE_URL}/tapahtumat`, { headers: { Authorization: `Bearer ${token}` } })
            ]);
            setLiput(liputRes.data);
            setTapahtumat(tapahtumatRes.data);
            console.log(liputRes.data)

        } catch (error) {
            console.error("Lippujen haku epäonnistui: ", error)
        }
    };

    useEffect(() => {
        haeLiput()
    }, []);

    
    useEffect(() => {
        if (tapahtumat.length > 0) {
            setColumnDefs([
                {
                    headerName: 'Tapahtuma',
                    field: 'tapahtumaId',
                    valueGetter: (params) => {
                        const tapahtuma = tapahtumat.find(t => t.tapahtumaId === params.data.tapahtumaId);
                        return tapahtuma ? tapahtuma.tapahtumaNimi : '-';
                    }
                },
                { field: "koodi", headerName: "Lipun Koodi" },
                { field: "status", headerName: "Lipun Status" },
                { field: 'lippuId', headerName: 'Lipun ID' },
                {
                    headerName: "Toiminnot",
                    cellRenderer: (params) => {
                      const onClick = async () => {
                        const lippuId = params.data.lippuId;
                        const koodi = params.data.koodi
                        const token = localStorage.getItem("jwtToken");
                        try {
                          await axios.patch(
                            `${API_BASE_URL}/liput/${lippuId}`,
                            { status: "KÄYTETTY" },
                            {
                              headers: {
                                Authorization: `Bearer ${token}`,
                                "Content-Type": "application/json"
                              }
                            }
                          );
                          alert(`Lippu ${koodi} merkitty käytetyksi.`);
                          haeLiput(); 
                        } catch (error) {
                          console.error("Lipun päivittäminen epäonnistui:", error);
                          alert("Virhe lipun päivittämisessä.");
                        }
                      };
                  
                      return <Button
                      onClick={onClick}
                      variant="contained"
                      color="success"
                      size="small"
                      disabled={params.data.status === "KÄYTETTY"}
                    >
                      Merkitse käytetyksi
                    </Button>
                    }
                  }
                
                
                
            ]);
        }
    }, [tapahtumat]);
    const defaultColDef = {
        sortable: true,
        filter: true
    };

    const autoSizeStrategy = {
        type: 'fitGridWidth',
        defaultMinWidth: 250,
    };

    return (
        <div className="LiputLista" style={{ position: "relative" }}>
            <div className="ag-theme-material" style={{ width: "100%", height: 800 }}>
                <AgGridReact
                    rowData={liput}
                    columnDefs={columnDefs}
                    defaultColDef={defaultColDef}
                    autoSizeStrategy={autoSizeStrategy}
                />
            </div>

        </div>
    )

}

export default LiputLista;