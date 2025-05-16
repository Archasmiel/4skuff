import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import useAuth from '../app/hooks/useAuth';

const PublicRoute = () => {
  const { isAuthenticated } = useAuth();
  return !isAuthenticated ? <Outlet /> : <Navigate to="/dashboard" replace />;
};

export default PublicRoute;