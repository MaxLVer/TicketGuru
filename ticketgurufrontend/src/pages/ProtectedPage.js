import React, { useEffect, useState } from 'react';
import { getProtectedData } from '../api/auth';

const ProtectedPage = () => {
  const [data, setData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      const result = await getProtectedData();
      setData(result);
    };

    fetchData();
  }, []);

  return (
    <div>
      <h1>Protected Page</h1>
      {data ? (
        <div>
          <h2>Protected Data:</h2>
          <pre>{JSON.stringify(data, null, 2)}</pre>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default ProtectedPage;
