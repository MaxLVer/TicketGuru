export const login = async (username, password) => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/kayttajat/kirjaudu`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    });
  
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Login failed');
    }
  
    const data = await response.json();
    localStorage.setItem('jwtToken', data.token);
    return data;
  };