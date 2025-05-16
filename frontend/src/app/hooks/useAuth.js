import { useSelector } from 'react-redux';
import TokenService from '../../features/auth/utils/TokenService';

const useAuth = () => {
  const { token, user } = useSelector((state) => state.auth);
  const isAuthenticated = !!token && TokenService.isTokenValid(token);
  
  return {
    isAuthenticated,
    user,
    token,
  };
};

export default useAuth;