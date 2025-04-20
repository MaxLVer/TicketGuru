export const securedFetch = async (url, options = {}) => {
    const token = localStorage.getItem('jwtToken');
    const headers = {
      ...options.headers,
      Authorization: `Bearer ${token}`,
    };
  
    return fetch(`${import.meta.env.REACT_APP_API_URL}${url}`, {
      ...options,
      headers,
    });
  };