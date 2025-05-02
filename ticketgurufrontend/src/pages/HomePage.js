import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@mui/material';

const HomePage = () => {
  const token = localStorage.getItem('jwtToken');

  return (
    <div style={{ textAlign: 'center', marginTop: 100 }}>
      <h1>Welcome to Home Page</h1>
      {token ? (
        <div>
        <p>You're logged in</p>
        <Link to="/lipunmyynti" style={{ textDecoration: 'none' }}><Button variant="contained" color="primary">Lipunmyynti</Button></Link>
        <Link to="/tapahtumat" style={{ textDecoration: 'none' }}><Button variant="contained" color="primary">Tapahtumat</Button></Link>
        </div>
      ) : (
        <p>
          <Link to="/login" style={{ textDecoration: 'none' }}>
            <Button variant="contained" color="primary">
              Login
            </Button>
          </Link>
        </p>
      )}
    </div>
  );
};

export default HomePage;