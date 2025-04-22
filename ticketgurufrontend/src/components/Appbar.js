import React from "react";
import { styled } from "@mui/material/styles";
import { AppBar, Toolbar, Button, IconButton } from "@mui/material";
import Typography from "@mui/material/Typography";
import MenuIcon from "@mui/icons-material/Menu";
import { useNavigate } from "react-router-dom"; // Käytetään useNavigate:ä
import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_URL;

const useStyles = styled((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

export default function Appbar() {
  const classes = useStyles();
  const navigate = useNavigate(); // Käytetään useNavigate-hookia

  // Logout-toiminto
  const logout = async () => {
    try {
      const token = localStorage.getItem("jwtToken");

      if (token) {
        await axios.post(
          `${API_BASE_URL}/kayttajat/uloskirjaudu`, // Backend-reitti logoutiin
          {},
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
      }

      // Poistetaan token localStoragesta
      localStorage.removeItem("jwtToken");

      // Ohjataan kirjautumissivulle
      navigate("/login");
    } catch (error) {
      console.error("Logout epäonnistui:", error);
    }
  };

  // Tarkistus, onko käyttäjä kirjautunut sisään
  const token = localStorage.getItem("jwtToken");

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" className={classes.title}>
            TicketGuru
          </Typography>
          {token && ( // Näytetään nappi vain, jos token on olemassa
            <Button color="inherit" variant="contained" onClick={logout} style={{ marginLeft: 'auto' }}>
              Kirjaudu ulos
            </Button>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
}
