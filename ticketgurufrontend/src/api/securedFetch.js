import { refreshAccessToken } from './refreshToken';

export const securedFetch = async (url, options = {}) => {
    let token = localStorage.getItem('jwtToken');
    const headers = {
      ...options.headers,
      Authorization: `Bearer ${token}`,
    };
    const response = await fetch(`${import.meta.env.REACT_APP_API_URL}${url}`, {
      ...options,
      headers,
    });
  
    if (response.status === 401) {
      try {
        //kokeile refreshtokenia
        const newToken = await refreshAccessToken();
        const retryHeaders = {
          ...options.headers,
          Authorization: `Bearer ${newToken}`,
        };
        //toista alkuperäinen requesti
        return fetch(`${import.meta.env.REACT_APP_API_URL}${url}`, {
          ...options,
          headers: retryHeaders,
        });
      } catch (refreshError) {
        // kirjaa ulos tai redirect to login
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('refreshToken');
        throw new Error('Session expired. Please log in again.');
      }
    }
  
    return response;
  };