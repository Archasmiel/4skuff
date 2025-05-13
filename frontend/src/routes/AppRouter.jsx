import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from '../features/auth/pages/AuthPage';

const Dashboard = () => <div>Welcome</div>;

const AppRouter = () => (
  <Router>
    <Routes>
      <Route path="/auth/" element={<AuthPage />} />
      <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
  </Router>
);

export default AppRouter;
