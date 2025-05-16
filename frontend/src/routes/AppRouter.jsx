import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from '../features/auth/pages/AuthPage';
import DashboardPage from '../features/auth/pages/DashboardPage';
import PrivateRoute from './PrivateRoute';
import { Navigate } from 'react-router-dom';

const AppRouter = () => (
  <Router>
    <Routes>
      <Route path="/auth" element={<AuthPage />} />
      <Route
        path="/dashboard"
        element={
          <PrivateRoute>
            <DashboardPage />
          </PrivateRoute>
        }
      />
      <Route path="*" element={<Navigate to="/dashboard" replace />} />
    </Routes>
  </Router>
);

export default AppRouter;