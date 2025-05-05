import { useEffect, useState } from "react";
import axios from "axios";
import { Typography, Divider, Box } from "@mui/material";


const TapahtumaTiedot = ({ tapahtuma }) => {
    const [tapahtumapaikka, setTapahtumapaikka] = useState(null);



    useEffect(() => {
        const tapahtumapaikkaLink = tapahtuma.links?.find(link => link.rel === "tapahtumapaikka")?.href;
        const token = localStorage.getItem("jwtToken");

        if (tapahtumapaikkaLink) {
            axios.get(tapahtumapaikkaLink, {
                headers: { Authorization: `Bearer ${token}` }
            })
                .then(res => setTapahtumapaikka(res.data))
                .catch(err => console.error("Tapahtumapaikan haku epäonnistui:", err));
        }
    }, [tapahtuma]);


    return (
        <Box>
            <Typography variant="h6">{tapahtuma.tapahtumaNimi}</Typography>
            <Typography variant="body2">{new Date(tapahtuma.tapahtumaAika).toLocaleString()}</Typography>

            <Typography>{tapahtuma.kuvaus}</Typography>
            <Typography><strong>Lippuja jäljellä: </strong>{tapahtuma.jaljellaOlevaLippumaara}</Typography>
            <Typography><strong>Lippujen Kokonaismäärä: </strong>{tapahtuma.kokonaislippumaara}</Typography>
            <Divider sx={{ my: 2 }} />
            {tapahtumapaikka && (
                <Box mt={2}>
                    <Typography variant="h6">Tapahtumapaikka</Typography>
                    <Typography><strong>Tapahtumapaikan nimi: </strong>{tapahtumapaikka.tapahtumapaikanNimi}</Typography>
                    <Typography><strong>Osoite: </strong>{tapahtumapaikka.lahiosoite}</Typography>
                    <Typography><strong>Kapasiteetti: </strong>{tapahtumapaikka.kapasiteetti}</Typography>
                </Box>
            )}
        </Box>
    );
};


export default TapahtumaTiedot