import axios from 'axios';

const login = async (username, password) => {
  try {
    const response = await axios.post(`${process.env.VITE_API_URL}/kayttajat/kirjaudu/`, {
      username,
      password,
    });
    const token = response.data.token;
    localStorage.setItem('jwtToken', token);
    console.log('Login successful!');
  } catch (error) {
    console.error('Login failed:', error);
  }
};

const getProtectedData = async () => {
  try {
    const token = localStorage.getItem('jwtToken');
    const response = await axios.get(`${process.env.VITE_API_URL}/protected-endpoint`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log('Protected data:', response.data);
  } catch (error) {
    console.error('Failed to fetch protected data:', error);
  }
};

export { login, getProtectedData };
