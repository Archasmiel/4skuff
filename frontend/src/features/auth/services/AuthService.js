import { authApiService } from '../../../api/AuthApiService';
import TokenService from '../utils/TokenService';

export class AuthService {
  static async login(credentials) {
    const { data } = await authApiService.login(credentials);
    TokenService.setToken(data.jwtToken);
    return data.jwtToken;
  }

  static async register(userData) {
    const { data } = await authApiService.register(userData);
    TokenService.setToken(data.jwtToken);
    return data.jwtToken;
  }

  static async logout() {
    try {
      await authApiService.logout();
    } finally {
      TokenService.clearToken();
    }
  }

  static async getMe() {
    const { data } = await authApiService.getMe();
    return data;
  }
}