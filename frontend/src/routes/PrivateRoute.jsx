import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import useAuth from '../app/hooks/useAuth';

const PrivateRoute = () => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <Outlet /> : <Navigate to="/auth" replace />;
};

export default PrivateRoute;