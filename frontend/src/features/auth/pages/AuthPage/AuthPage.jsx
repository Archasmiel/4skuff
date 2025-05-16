import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { loginThunk, registerThunk } from '../../store/authThunks';
import LoginForm from '../../components/LoginForm/LoginForm';
import RegisterForm from '../../components/RegisterForm/RegisterForm';
import { AuthContainer, AuthToggle } from './AuthPage.styles';
import useAuth from '../../../../app/hooks/useAuth';
import { Typography } from '@mui/material';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { status, error } = useSelector((state) => state.auth);
  const { isAuthenticated } = useAuth();

  const handleLogin = (credentials) => {
    dispatch(loginThunk(credentials));
  };

  const handleRegister = (credentials) => {
    dispatch(registerThunk(credentials));
  };

  if (isAuthenticated) {
    navigate('/dashboard');
    return null;
  }

  return (
    <AuthContainer>
      <Typography variant="h4" gutterBottom>
        {isLogin ? 'Login' : 'Register'}
      </Typography>
      {error && (
        <Typography color="error" gutterBottom>
          {error}
        </Typography>
      )}
      {isLogin ? (
        <LoginForm onSubmit={handleLogin} isLoading={status === 'loading'} />
      ) : (
        <RegisterForm onSubmit={handleRegister} isLoading={status === 'loading'} />
      )}
      <AuthToggle onClick={() => setIsLogin(!isLogin)}>
        {isLogin ? 'Need an account? Register' : 'Have an account? Login'}
      </AuthToggle>
    </AuthContainer>
  );
};

export default AuthPage;