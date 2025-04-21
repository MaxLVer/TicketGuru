import React, { useEffect, useState } from 'react';
import { securedFetch } from '../api/securedFetch';

const ProtectedPage = () => {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  //Pääosin testaamaan toimiiko Admin-oikeudet.

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await securedFetch('/tapahtumat');
        const result = await response.json();
        setData(result);
      } catch (err) {
        setError('An error occurred: ' + err.message);
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      {error && <p>{error}</p>}
      {data ? <pre>{JSON.stringify(data, null, 2)}</pre> : <p>Loading...</p>}
    </div>
  );
};

export default ProtectedPage;