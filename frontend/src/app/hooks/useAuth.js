import { useSelector } from 'react-redux';
import TokenService from '../../features/auth/utils/TokenService';

export const useAuth = () => {
  const { token, user } = useSelector((state) => state.auth);
  const currentToken = token || TokenService.getToken();

  return {
    isAuthenticated: !!currentToken && TokenService.isTokenValid(currentToken),
    user,
    token: currentToken
  };
};