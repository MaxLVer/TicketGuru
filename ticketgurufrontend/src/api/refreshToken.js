export const refreshAccessToken = async () => {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) throw new Error('No refresh token available');
  
    const response = await fetch(`${process.env.REACT_APP_API_URL}/auth/refresh`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ refreshToken }),
    });
  
    if (!response.ok) {
      throw new Error('Refresh token expired or invalid');
    }
  
    const data = await response.json();
    localStorage.setItem('jwtToken', data.accessToken);
    return data.accessToken;
  };
  