import React from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logoutThunk } from '../../store/authThunks';
import { DashboardContainer, UserInfoCard, LogoutButton } from './DashboardPage.styles';

const DashboardPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await dispatch(logoutThunk()).unwrap();
      navigate('/auth');
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  return (
    <DashboardContainer>
      <h1>Welcome to Dashboard</h1>
      <UserInfoCard>
        <h2>User Information</h2>
        <p>Here you can display user-specific data</p>
      </UserInfoCard>
      <LogoutButton onClick={handleLogout}>Logout</LogoutButton>
    </DashboardContainer>
  );
};

export default DashboardPage;