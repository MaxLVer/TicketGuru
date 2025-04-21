export const login = async (username, password) => {
  try {
    console.log('🔐 Attempting login...');

    const response = await fetch(`${process.env.REACT_APP_API_URL}/kayttajat/kirjaudu`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-Custom-Header': 'trigger-cors',
        Accept: '*/*',
      },
      body: JSON.stringify({ username, password }),
    });

    console.log('📡 Response received:', response);

    const rawText = await response.text();
    let data = {}
    console.log('Raw response text:', rawText);

    try {
      data = JSON.parse(rawText);
    } catch (e) {
      console.warn('No JSON body in response');
    }


    if (!response.ok) {
      console.error('❌ Login failed:', data.message);
      throw new Error(data.message || 'Login failed');
    }

    console.log('✅ Login successful:', data);

    // Save tokens (adjust keys based on your backend's response format)
    localStorage.setItem('jwtToken', data.token || data.accessToken);
    if (data.refreshToken) {
      localStorage.setItem('refreshToken', data.refreshToken);
    }

    return data;
  } catch (error) {
    console.error('💥 Error during login:', error.message);
    throw error;
  }
};
