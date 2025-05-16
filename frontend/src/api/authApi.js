import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

const API = axios.create({
    baseURL: API_URL,
});

// for future production
API.interceptors.request.use((config) => {
  config.headers['x-api-key'] = 'my-secret-api-key';
  return config;
});

export const login = (credentials) => API.post('login', {
  username: credentials.username,
  password: credentials.password
});

export const register = (credentials) => API.post('signup', {
  username: credentials.username,
  email: credentials.email,
  password: credentials.password
});