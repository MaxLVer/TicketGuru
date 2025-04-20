import React, { useEffect, useState } from 'react';

const ProtectedPage = () => {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  //Pääosin testaamaan toimiiko Admin-oikeudet.

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    
    const fetchData = async () => {
      try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/tapahtumat`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setData(data);
        } else {
          setError('Failed to fetch data');
        }
      } catch (err) {
        setError('An error occurred');
      }
    };

    if (token) {
      fetchData();
    } else {
      setError('No token found');
    }
  }, []);

  return (
    <div>
      {error && <p>{error}</p>}
      {data ? <pre>{JSON.stringify(data, null, 2)}</pre> : <p>Loading...</p>}
    </div>
  );
};

export default ProtectedPage;