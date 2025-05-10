import React, { useState, useEffect } from "react";
import { AgGridReact } from "ag-grid-react";
import { useParams } from "react-router-dom";
import dayjs from "dayjs";
import axios from "axios";
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community';

import "ag-grid-community/styles/ag-theme-material.css";

import NaytaOstostapahtuma from "../components/NaytaOstostapahtuma";

ModuleRegistry.registerModules([AllCommunityModule]);

const API_BASE_URL = process.env.REACT_APP_API_URL;

function Myyntitapahtumat() {
    const { tapahtumaId } = useParams();
    const [ostostapahtumat, setOstostapahtumat] = useState([]);
    useEffect(() => {
        const haeOstostapahtumat = async () => {
            try {
                const token = localStorage.getItem("jwtToken");
                const [liputRes, ostostapahtumatRes] = await Promise.all([
                    axios.get(`${API_BASE_URL}/liput`, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }), axios.get(`${API_BASE_URL}/ostostapahtumat`, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    })
                ]);
                const kaikkiLiput = liputRes.data;
                const kaikkiOstostapahtumat = ostostapahtumatRes.data;
                const liput = kaikkiLiput.filter(lippu => lippu.tapahtumaId === parseInt(tapahtumaId));

                const ostostapahtumaIdt = new Set(liput.map(lippu => lippu.ostostapahtumaId));

                const ostostapahtumat = kaikkiOstostapahtumat.filter(ostostapahtuma => ostostapahtumaIdt.has(ostostapahtuma.ostostapahtumaId)
                );

                setOstostapahtumat(ostostapahtumat);
            } catch (error) {
                console.error("Ostostapahtumien haku epäonnistui:", error);
            }
        };
        haeOstostapahtumat();
    }, []);
    const [columnDefs, setColumnDefs] = useState([
        {
            field: "myyntiaika",
            valueFormatter: (params) => {
                return dayjs(params.value).format('DD.MM.YYYY hh:mm');
            }
        },
        { field: "ostostapahtumaId", headerName: "#" },
        { field: "summa", headerName: "Kokonaishinta (€)" },
        {
            field: '_links.self.href',
            headerName: '',
            sortable: false,
            filter: false,
            cellRenderer: params => <NaytaOstostapahtuma valittuOstostapahtuma={params.data} />
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
        <div className="Myyntitapahtumat">
            <div className="ag-theme-material" style={{ width: "100%", height: 800 }}>
                <AgGridReact
                    rowData={ostostapahtumat}
                    columnDefs={columnDefs}
                    defaultColDef={defaulColDef}
                    autoSizeStrategy={autoSizeStrategy}

                />
            </div>
        </div>
    )
}

export default Myyntitapahtumat