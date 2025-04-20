import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@mui/material';

const HomePage = () => {
  const token = localStorage.getItem('jwtToken');

  return (
    <div style={{ textAlign: 'center', marginTop: 100 }}>
      <h1>Welcome to Home Page</h1>
      {token ? (
        <p>You're logged in</p>
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