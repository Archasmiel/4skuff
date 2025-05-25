import { ApiClient } from './ApiClient';
import { API_BASE_URL } from '../shared/constants/api';

class AuthApiService {
  constructor() {
    this.apiClient = new ApiClient(API_BASE_URL);
  }

  login(credentials) {
    return this.apiClient.post('/auth/login', credentials);
  }

  register(userData) {
    return this.apiClient.post('/auth/signup', userData);
  }

  logout() {
    return this.apiClient.get('/auth/logout');
  }

  getMe() {
    return this.apiClient.get('/auth/me');
  }
}

export const authApiService = new AuthApiService();