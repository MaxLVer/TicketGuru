import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import ProtectedPage from './pages/ProtectedPage';
import AppBar from './components/Appbar';
import Lipunmyynti from './pages/Lipunmyynti';
import YhteenvetOstoskori from './pages/YhteenvetOstoskori';
import Kuittisivu from './pages/Kuittisivu';
import 'bootstrap/dist/css/bootstrap.min.css';


const App = () => {
  return (
    <>
    <AppBar />
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/protected" element={<ProtectedPage />} /> 
      <Route path="/lipunmyynti" element={<Lipunmyynti />} />      
      <Route path="*" element={<Navigate to="/" replace />} />
      <Route path="/yhteenveto" element={<YhteenvetOstoskori />} /> 
      <Route path="/kuitti" element={<Kuittisivu />} /> 
    </Routes>
    </>
  );
};

export default App;