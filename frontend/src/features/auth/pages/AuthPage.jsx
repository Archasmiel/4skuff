import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { loginThunk, registerThunk } from '../authThunks';
import LoginForm from '../components/LoginForm';
import RegisterForm from '../components/RegisterForm';
import '../../../styles/auth.css';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { status, error, token } = useSelector((state) => state.auth);

  const handleLogin = (credentials) => {
    const payload = {
      username: credentials.userName,
      password: credentials.password,
    };
    dispatch(loginThunk(payload));
  };

  const handleRegister = (credentials) => {
    dispatch(registerThunk(credentials));
  };

  useEffect(() => {
    if (token) {
      navigate('/dashboard');
    }
  }, [token, navigate]);

  return (
    <div className="auth-container">
      <h2 className="auth-title">{isLogin ? 'Login' : 'Register'}</h2>
      {isLogin ? (
        <LoginForm onSubmit={handleLogin} />
      ) : (
        <RegisterForm onSubmit={handleRegister} />
      )}
      {status === 'loading' && <p>Loading...</p>}
      {error && <p className="auth-error">Error: {error}</p>}
      <p className="switch-form" onClick={() => setIsLogin((prev) => !prev)}>
        {isLogin ? 'Dont have an account? Register here' : 'Already have an account? Login here'}
      </p>
    </div>
  );
};

export default AuthPage;