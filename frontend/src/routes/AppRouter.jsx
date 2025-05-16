import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from '../features/auth/pages/AuthPage';
import PrivateRoute from './PrivateRoute';

const Dashboard = () => <div>Welcome</div>;

const AppRouter = () => (
  <Router>
    <Routes>
      <Route path="/auth" element={<AuthPage />} />
      <Route
        path="/dashboard"
        element={
          <PrivateRoute>
            <Dashboard />
          </PrivateRoute>
        }
      />
    </Routes>
  </Router>
);

export default AppRouter;
