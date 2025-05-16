import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logout } from '../authSlice';
import axios from 'axios';
import '../../../styles/dashboard.css';

const DashboardPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.auth);

  const handleLogout = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.get('http://localhost:8080/api/auth/logout', {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      dispatch(logout());
      navigate('/auth');
    } catch (error) {
      console.error('Logout error:', error);
      
      dispatch(logout());
      navigate('/auth');
    }
  };

  return (
    <div className="dashboard-container">
      <h1>Welcome to Dashboard</h1>
      {user && (
        <div className="user-info">
          <p>Hello, {user.username}!</p>
          <p>Email: {user.email}</p>
        </div>
      )}
      <button onClick={handleLogout} className="logout-button">
        Logout
      </button>
    </div>
  );
};

export default DashboardPage;