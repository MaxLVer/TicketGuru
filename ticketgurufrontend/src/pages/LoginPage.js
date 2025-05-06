import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '../api/auth';

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const nav = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();

    login(username, password)
      .then(result => {
        if (result.status === 'ok') {
          nav('/')
        } else {
          setError("Väärä käyttäjänimi tai salasana");
        }
      })
      .catch(error => {
        console.error('Unexpected error:', error);
      });
  };

  return (
    <div style={{ maxWidth: 300, margin: 'auto', paddingTop: 100 }}>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Username"
          required
        />
        <br />
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password"
          required
        />
        <br />
        <button type="submit">Login</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>
      <button onClick={() => nav('/')} style={{ marginTop: 10 }}>
        Home
      </button>
    </div>
  );
};

export default LoginPage;
