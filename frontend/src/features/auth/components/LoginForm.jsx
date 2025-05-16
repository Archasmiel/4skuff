import React, { useState } from 'react';

const LoginForm = ({ onSubmit }) => {
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');

  return (
    <form
      onSubmit={(e) => {
        e.preventDefault();
        onSubmit({ userName, password });
      }}
      className="form-group"
    >
      <div className="form-group">
        <input
          type="name"
          placeholder="Nickname"
          className="form-input"
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
        />
      </div>
      <div className="form-group">
        <input
          type="password"
          placeholder="Password"
          className="form-input"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <button type="submit" className="login-btn">Login</button>
    </form>
  );
};

export default LoginForm;
