import React from 'react';
<<<<<<< HEAD
import { Routes, Route } from 'react-router-dom';
import PublicRoute from './PublicRoute';
import PrivateRoute from './PrivateRoute';
import AuthPage from '../features/auth/pages/AuthPage/AuthPage';
import DashboardPage from '../features/auth/pages/DashboardPage/DashboardPage';
import { Navigate } from 'react-router-dom';

const AppRouter = () => (
  <Routes>
    <Route element={<PublicRoute />}>
      <Route path="/auth" element={<AuthPage />} />
    </Route>
    
    <Route element={<PrivateRoute />}>
      <Route path="/dashboard" element={<DashboardPage />} />
    </Route>
    
    <Route path="*" element={<Navigate to="/dashboard" replace />} />
  </Routes>
=======
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
>>>>>>> 7bec585c5c94913dca0b778a99a52f978c8afa8e
);

export default AppRouter;