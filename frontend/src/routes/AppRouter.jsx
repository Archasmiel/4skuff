import { Routes, Route, Navigate } from 'react-router-dom';
import { PublicRoute } from './PublicRoute';
import { PrivateRoute } from './PrivateRoute';
import { AuthPage } from '../features/auth/pages/AuthPage/AuthPage';
import { DashboardPage } from '../features/auth/pages/DashboardPage/DashboardPage';

export const AppRouter = () => (
  <Routes>
    <Route element={<PublicRoute />}>
      <Route path="/auth" element={<AuthPage />} />
    </Route>

    <Route element={<PrivateRoute />}>
      <Route path="/dashboard" element={<DashboardPage />} />
    </Route>

    <Route path="*" element={<Navigate to="/auth" replace />} />
  </Routes>
);