import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import useAuth from '../app/hooks/useAuth';

<<<<<<< HEAD
const PrivateRoute = () => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <Outlet /> : <Navigate to="/auth" replace />;
=======
const PrivateRoute = ({ children }) => {
    const token = useSelector((state) => state.auth.token);
    return token ? children : <Navigate to="/auth" />;
>>>>>>> 7bec585c5c94913dca0b778a99a52f978c8afa8e
};

export default PrivateRoute;