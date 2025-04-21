export const login = (username, password) => {
  console.log('🔐 Attempting login...');

  // Make the POST request to the login endpoint
  return fetch(`${process.env.REACT_APP_API_URL}/kayttajat/kirjaudu`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Accept: '*/*',
    },
    credentials: 'include',
    body: JSON.stringify({ kayttajanimi: username, salasana: password }),
  })
    .then(response => {
      // Check if the response status is OK (status code 200)
      if (!response.ok) {
        return Promise.reject('Invalid username or password');
      }

      // Parse the JSON response
      return response.json();
    })
    .then(data => {
      // Save the tokens to localStorage if the login is successful
      localStorage.setItem('jwtToken', data.accessToken);
      localStorage.setItem('refreshToken', data.refreshToken);

      // Return the data (you can return status 'ok' or any other info)
      console.log('✅ Login successful for', username);
      return { status: 'ok', data };  // Return status 'ok' and data
    })
    .catch(error => {
      // Handle errors (login failed or other errors)
      console.error('❌ Login failed:', error);
      return { status: 'error', message: error };  // Return status 'error' with the error message
    });
};
