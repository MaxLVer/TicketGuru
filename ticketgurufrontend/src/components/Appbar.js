import React from "react";
import { useState } from "react";
import { styled } from "@mui/material/styles";
import { AppBar, Toolbar, Button, IconButton, Box } from "@mui/material";
import Typography from "@mui/material/Typography";
import MenuIcon from "@mui/icons-material/Menu";
import AdbIcon from '@mui/icons-material/Adb';
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Tooltip from "@mui/material/Tooltip";
import Avatar from "@mui/material/Avatar";
import { useNavigate } from "react-router-dom"; // Käytetään useNavigate:ä
import { useLocation } from "react-router-dom";
import axios from "axios";
import { green } from "@mui/material/colors";

const API_BASE_URL = process.env.REACT_APP_API_URL;

const pages = ['Lipunmyynti', 'Tapahtumat', 'Liput'];
const settings = ['Profiili', 'Tili', 'Asetukset', 'Kirjaudu Ulos'];

export default function Appbar() {

  const navigate = useNavigate(); // Käytetään useNavigate-hookia
  const location = useLocation();
const [anchorElNav, setAnchorElNav] = useState(null);
const [anchorElUser, setAnchorElUser] = useState(null);


const handleOpenNavMenu = (event) => {
  setAnchorElNav(event.currentTarget);
};
const handleOpenUserMenu = (event) => {
  setAnchorElUser(event.currentTarget);
};

const handleCloseNavMenu = () => {
  setAnchorElNav(null);
};

const handleCloseUserMenu = () => {
  setAnchorElUser(null);
};
// Navigointi sivuille
const handlePageNavigation = (page) => {
  const route = "/" + page.toLowerCase();
  navigate(route);
  handleCloseNavMenu();
};

if (location.pathname === "/login") return null;



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
    <AppBar position="static" sx={{ backgroundColor: '#4caf50' }}>
    <Toolbar disableGutters>
      <AdbIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
      <Typography
        variant="h6"
        noWrap
        component="div"
        onClick={() => navigate("/")}
        sx={{
          mr: 2,
          display: { xs: 'none', md: 'flex' },
          fontFamily: 'monospace',
          fontWeight: 700,
          letterSpacing: '.3rem',
          color: 'inherit',
          textDecoration: 'none',
          cursor: 'pointer',
        }}
      >
        TICKETGURU
      </Typography>
  
      {/* Mobile Menu Icon */}
      <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
        <IconButton
          size="large"
          aria-label="account of current user"
          aria-controls="menu-appbar"
          aria-haspopup="true"
          onClick={handleOpenNavMenu}
          color="inherit"
        >
          <MenuIcon />
        </IconButton>
        {/* Mobile Menu */}
        <Menu
          id="menu-appbar"
          anchorEl={anchorElNav}
          anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
          }}
          keepMounted
          transformOrigin={{
            vertical: 'top',
            horizontal: 'left',
          }}
          open={Boolean(anchorElNav)}
          onClose={handleCloseNavMenu}
          sx={{ display: { xs: 'block', md: 'none' } }}
        >
          {pages.map((page) => (
            <MenuItem key={page} onClick={() => handlePageNavigation(page)}>
              <Typography textAlign="center">{page}</Typography>
            </MenuItem>
          ))}
        </Menu>
      </Box>
  
      {/* Desktop Menu */}
      <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
        {pages.map((page) => (
          <Button
            key={page}
            onClick={() => handlePageNavigation(page)}
            sx={{ my: 2, color: 'white', display: 'block' }}
          >
            {page}
          </Button>
        ))}
      </Box>
  
      {/* User Settings */}
      <Box sx={{ flexGrow: 0 }}>
        <Tooltip title="Open settings">
          <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
            <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
          </IconButton>
        </Tooltip>
        <Menu
          sx={{ mt: '45px' }}
          id="menu-appbar"
          anchorEl={anchorElUser}
          anchorOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
          keepMounted
          transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
          open={Boolean(anchorElUser)}
          onClose={handleCloseUserMenu}
        >
          {settings.map((setting) => (
            <MenuItem
            key={setting}
            onClick={() => {
              if (setting === "Kirjaudu Ulos") {
                logout();
              } else {
                navigate("/" + setting.toLowerCase());
                handleCloseUserMenu();
              }
            }}
          >
            <Typography textAlign="center">{setting}</Typography>
          </MenuItem>
          
          ))}
        </Menu>
      </Box>
    </Toolbar>
  </AppBar>
  
  );

}