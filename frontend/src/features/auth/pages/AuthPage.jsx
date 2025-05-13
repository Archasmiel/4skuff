import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loginThunk } from '../authThunks';
import LoginForm from '../components/LoginForm';
import RegisterForm from '../components/RegisterForm';
import '../../../styles/auth.css';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const dispatch = useDispatch();
  const { status, error } = useSelector((state) => state.auth);

  const handleLogin = (credentials) => {
    dispatch(loginThunk(credentials));
  };

  const handleRegister = (credentials) => {
    dispatch(registerThunk(credentials));
  };

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
      <p
        className="switch-form"
        onClick={() => setIsLogin((prev) => !prev)}
      >
        {isLogin ? 'Don’t have an account? Register here' : 'Already have an account? Login here'}
      </p>
      <div className="google-btn-container">
        <button
          className="google-btn"
          onClick={() => setIsLogin((prev) => !prev)}
        >
          Or use Google
        </button>
      </div>

    </div>
  );
};

export default AuthPage;
