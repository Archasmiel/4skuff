import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../app/hooks/useAuth';

export const PublicRoute = () => {
  const { isAuthenticated } = useAuth();
  return !isAuthenticated ? <Outlet /> : <Navigate to="/dashboard" replace />;
};