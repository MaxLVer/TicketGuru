import { useState, useEffect } from "react"; 
import axios from "axios";
import "../styles/properForm.css";

const API_BASE_URL = process.env.REACT_APP_API_URL;

const TapahtumaLipputyypit = ({ tapahtumaId, onSuccess }) => {
    const [asiakastyyppiId, setAsiakastyyppiId] = useState("");
    const [asiakastyypit, setAsiakastyypit] = useState([]);
    const [tapahtumaNimi, setTapahtumaNimi] = useState("");
    const [hinta, setHinta] = useState("");
    const [error, setError] = useState(null);
    const [olemassaOlevatLipputyypit, setOlemassaOlevatLipputyypit] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const token = localStorage.getItem("jwtToken");
            const authHeaders = {
                headers: { Authorization: `Bearer ${token}` },
            };

            try {
                const tapahtumaRes = await axios.get(`${API_BASE_URL}/tapahtumat/${tapahtumaId}`, authHeaders);
                setTapahtumaNimi(tapahtumaRes.data.tapahtumaNimi);

                const asiakasRes = await axios.get(`${API_BASE_URL}/asiakastyypit`, authHeaders);
                setAsiakastyypit(asiakasRes.data);

                const lipputyyppiRes = await axios.get(`${API_BASE_URL}/tapahtumalipputyypit`, authHeaders);
                const normalized = lipputyyppiRes.data
                    .filter(lt => lt.tapahtumaId === tapahtumaId)
                    .map(lt => ({
                        ...lt,
                        asiakastyyppiId: lt.asiakastyyppiId // muokataan kenttä frontendin logiikkaa varten
                    }));
                setOlemassaOlevatLipputyypit(normalized);
            } catch (err) {
                setError("Tietojen haku epäonnistui.");
            }
        };

        if (tapahtumaId) {
            fetchData();
        }
    }, [tapahtumaId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        if (!asiakastyyppiId || isNaN(parseFloat(hinta))) {
            setError("Täytä kaikki kentät oikein.");
            return;
        }

        const token = localStorage.getItem("jwtToken");
        const authHeaders = {
            headers: { Authorization: `Bearer ${token}` },
        };

        try {
            await axios.post(`${API_BASE_URL}/tapahtumalipputyypit`, {
                tapahtumaId,
                asiakastyyppiId: parseInt(asiakastyyppiId), // backendin väärä nimi
                hinta: parseFloat(hinta),
            }, authHeaders);

            setAsiakastyyppiId("");
            setHinta("");
            onSuccess();
            window.location.reload();
        } catch (err) {
            setError("Lipputyypin lisääminen epäonnistui.");
        }
    };

    const handleUpdate = async (id, updatedData) => {
        const token = localStorage.getItem("jwtToken");
        const authHeaders = {
            headers: { Authorization: `Bearer ${token}` },
        };

        try {
            await axios.put(`${API_BASE_URL}/tapahtumalipputyypit/${id}`, updatedData, authHeaders);
            onSuccess();
            window.location.reload();
        } catch (err) {
            setError("Lipputyypin päivitys epäonnistui.");
        }
    };

    const handleDelete = async (id) => {
        const token = localStorage.getItem("jwtToken");
        const authHeaders = {
            headers: { Authorization: `Bearer ${token}` },
        };

        console.log("Poistetaan lipputyyppi id:llä", id);

        try {
            const response = await axios.delete(`${API_BASE_URL}/tapahtumalipputyypit/${id}`, authHeaders);
            console.log("Poisto onnistui:", response);
            onSuccess();
            window.location.reload();
        } catch (err) {
            console.error("Poistovirhe:", err.response?.data || err.message);
            setError("Poisto epäonnistui.");
        }
    };

    return (
        <form className="proper-form" onSubmit={handleSubmit}>
            <h3>Lisätään lipputyyppi tapahtumaan: <em>{tapahtumaNimi} (ID: {tapahtumaId})</em></h3>

            <label>
                Asiakastyyppi:
                <select
                    value={asiakastyyppiId}
                    onChange={(e) => setAsiakastyyppiId(e.target.value)}
                    required
                >
                    <option value="">Valitse asiakastyyppi</option>
                    {asiakastyypit.map(tyyppi => (
                        <option key={tyyppi.asiakastyyppiId} value={tyyppi.asiakastyyppiId}>
                            {tyyppi.asiakastyyppi}
                        </option>
                    ))}
                </select>
            </label>

            <label>
                Hinta:
                <input
                    type="number"
                    value={hinta}
                    onChange={(e) => setHinta(e.target.value)}
                    required
                />
            </label>

            {error && <p className="error-text">{error}</p>}

            <button type="submit">Tallenna uusi lipputyyppi</button>

            <h4>Olemassa olevat lipputyypit:</h4>
            <ul>
                {olemassaOlevatLipputyypit.map((lt, idx) => (
                    <li key={lt.tapahtumaLipputyyppiId || idx}>
                        <span style={{ fontWeight: "bold", marginRight: "10px" }}>
                            ID: {lt.tapahtumaLipputyyppiId}
                        </span>

                        <select
                            value={parseInt(lt.asiakastyyppiId)}
                            onChange={(e) => {
                                const newList = [...olemassaOlevatLipputyypit];
                                newList[idx] = {
                                    ...newList[idx],
                                    asiakastyyppiId: parseInt(e.target.value),
                                };
                                setOlemassaOlevatLipputyypit(newList);
                            }}
                        >
                            {asiakastyypit.map((tyyppi) => (
                                <option key={tyyppi.asiakastyyppiId} value={tyyppi.asiakastyyppiId}>
                                    {tyyppi.asiakastyyppi}
                                </option>
                            ))}
                        </select>

                        <input
                            type="number"
                            value={lt.hinta}
                            onChange={(e) => {
                                const newList = [...olemassaOlevatLipputyypit];
                                newList[idx].hinta = parseFloat(e.target.value);
                                setOlemassaOlevatLipputyypit(newList);
                            }}
                            style={{ width: "80px", marginLeft: "10px" }}
                        /> €

                        <button
                            type="button"
                            onClick={() =>
                                handleUpdate(lt.tapahtumaLipputyyppiId, {
                                    asiakastyyppiId: lt.asiakastyyppiId,
                                    hinta: lt.hinta,
                                    tapahtumaId: lt.tapahtumaId,
                                })
                            }
                            style={{ marginLeft: "10px" }}
                        >
                            Tallenna muutokset
                        </button>

                        <button
                            type="button"
                            onClick={() => {
                                console.log("Poistetaan lipputyyppi:", lt);
                                handleDelete(lt.tapahtumaLipputyyppiId);
                            }}
                            style={{
                                marginLeft: "10px",
                                color: "red",
                                background: "none",
                                border: "none",
                                cursor: "pointer",
                            }}
                        >
                            Poista
                        </button>
                    </li>
                ))}
            </ul>
        </form>
    );
};

export default TapahtumaLipputyypit;
