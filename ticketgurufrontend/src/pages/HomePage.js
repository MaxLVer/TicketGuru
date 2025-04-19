import React, { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

const HomePage = () => {
  const history = useHistory();

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
      history.push('/login');
    }
  }, [history]);

  return (
    <div>
      <h1>Welcome to the Home Page</h1>
      {/* Your home page content */}
    </div>
  );
};

export default HomePage;
