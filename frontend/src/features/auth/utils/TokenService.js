import { jwtDecode } from 'jwt-decode';

class TokenService {
  static getToken() {
    return localStorage.getItem('token') || null;
  }

  static setToken(token) {
    localStorage.setItem('token', token);
  }

  static clearToken() {
    localStorage.removeItem('token');
  }

  static decodeToken(token) {
    try {
      return jwtDecode(token);
    } catch (error) {
      console.error('Token decode error:', error);
      return null;
    }
  }

  static isTokenValid(token) {
    if (!token) return false;
    const decoded = this.decodeToken(token);
    return decoded?.exp > Date.now() / 1000;
  }
}

export default TokenService;