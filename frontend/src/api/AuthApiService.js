import ApiClient from './ApiClient';
import { API_BASE_URL } from '../shared/constants/api';

class AuthApiService {
  constructor() {
    this.apiClient = new ApiClient(API_BASE_URL);
  }

  async login(credentials) {
    return this.apiClient.post('/auth/login', credentials);
  }

  async register(userData) {
    return this.apiClient.post('/auth/signup', userData);
  }

  async logout(token) {
    return this.apiClient.get('/auth/logout', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}

export default new AuthApiService();