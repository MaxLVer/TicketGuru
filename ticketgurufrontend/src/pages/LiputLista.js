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
            console.error("Lippujen haku epäonnistui: ", error)
        }
    };

    useEffect(() => {
        haeLiput()
    }, []);

    const merkitseKaytetyksi = () => {

    }

    const [columnDefs, setColumnDefs] = useState([
        { field: "koodi", headername: "Lipun Koodi" },
        { field: "status", headername: "Lipun Status" },
        { field: "ostostapahtumaId", headername: "Ostostapahtuman tunnus" },
        {
            field: '_links.self.href',
            headerName: '',
            sortable: false,
            filterable: false,
            cellRenderer: (params) => {
                <Button onClick={() => merkitseKaytetyksi(params.data)}>
                    Merkitse Käytetyksi
                </Button>
            }
        }
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
        <div className="LiputLista" style={{ position: "relative" }}>
            <div className="ag-theme-material" style={{ width: "100%", height: 800 }}>
                <AgGridReact
                    rowData={liput}
                    columnDefs={columnDefs}
                    defaultColDef={defaulColDef}
                    autoSizeStrategy={autoSizeStrategy}
                />
            </div>

        </div>
    )

}

export default LiputLista;