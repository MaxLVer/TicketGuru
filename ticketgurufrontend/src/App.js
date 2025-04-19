import React from 'react';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/loginpage';
import ProtectedPage from './pages/ProtectedPage';

const App = () => {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={HomePage} />
        <Route path="/login" component={LoginPage} />
        <Route path="/protected" component={ProtectedPage} />
        <Redirect to="/" />
      </Switch>
    </Router>
  );
};

export default App;
