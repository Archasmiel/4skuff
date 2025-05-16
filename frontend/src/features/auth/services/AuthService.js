import AuthApiService from '../../../api/AuthApiService';
import TokenService from '../utils/TokenService';

class AuthService {
  static async login(credentials) {
    try {
      const response = await AuthApiService.login(credentials);
      const { jwtToken } = response.data;
      
      if (!jwtToken) {
        throw new Error('No token received from server');
      }
      
      TokenService.setToken(jwtToken);
      return TokenService.decodeToken(jwtToken);
    } catch (error) {
      throw error;
    }
  }

  static async register(userData) {
    try {
      const response = await AuthApiService.register(userData);
      const { jwtToken } = response.data;
      
      if (!jwtToken) {
        throw new Error('No token received after registration');
      }
      
      TokenService.setToken(jwtToken);
      return TokenService.decodeToken(jwtToken);
    } catch (error) {
      throw error;
    }
  }

  static async logout() {
    try {
      const token = TokenService.getToken();
      if (token) {
        await AuthApiService.logout(token);
      }
      TokenService.clearToken();
    } catch (error) {
      TokenService.clearToken();
      throw error;
    }
  }
}

export default AuthService;