import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logoutThunk, fetchUserDataThunk } from '../../store/authThunks';
import { DashboardContainer, UserInfoCard, LogoutButton } from './DashboardPage.styles';
import { AuthService } from '../../services/AuthService';

export const DashboardPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user, status } = useSelector((state) => state.auth);

  useEffect(() => {
    if (status === 'idle') {
      dispatch(fetchUserDataThunk());
    }
  }, [dispatch, status]);

  if (status === 'loading') {
    return <div>Loading...</div>;
  }

  if (!user) {
    return <Navigate to="/auth" replace />;
  }

  const handleLogout = async () => {
    try {
      await AuthService.logout();
      navigate('/auth');
    } catch (error) {
      console.error('Logout error:', error);
    }
  };

  return (
    <DashboardContainer>
      <h1>Welcome to Dashboard</h1>
      
      <UserInfoCard>
        <h2>User Information</h2>
        {user ? (
          <>
            <p>Username: {user.username}</p>
            {user.email && <p>Email: {user.email}</p>}
          </>
        ) : (
          <p>Loading user data...</p>
        )}
      </UserInfoCard>

      <LogoutButton onClick={handleLogout}>
        Logout
      </LogoutButton>
    </DashboardContainer>
  );
};