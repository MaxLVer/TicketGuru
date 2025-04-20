import React from 'react';

const HomePage = () => {
  const token = localStorage.getItem('jwtToken');

  return (
    <div style={{ textAlign: 'center', marginTop: 100 }}>
      <h1>Welcome to Home Page</h1>
      {token ? <p>You're logged in</p> : <p>Please login to continue.</p>}
    </div>
  );
};

export default HomePage;