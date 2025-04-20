import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/loginpage';
import ProtectedPage from './pages/ProtectedPage';
import AppBar from './components/Appbar';

const App = () => {
  return (
    <>
    <AppBar />
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/protected" element={<ProtectedPage />} /> 
      <Route path="/lipunmyynti" element={<ProtectedPage />} />      
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
    </>
  );
};

export default App;