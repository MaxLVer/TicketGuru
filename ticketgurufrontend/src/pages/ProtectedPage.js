import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedPage = () => {
  const token = localStorage.getItem('jwtToken');

  if (!token) {
    return <Navigate to="/login" />;
  }

  return (
    <div style={{ textAlign: 'center', marginTop: 100 }}>
      <h2>This is a protected page</h2>
      <p>You can only see this if you're logged in.</p>
    </div>
  );
};

export default ProtectedPage;