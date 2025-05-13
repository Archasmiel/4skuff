import axios from 'axios';

const API_URL = 'http://localhost:5000/api/auth/';

const API = axios.create({
    baseURL: API_URL,
});

export const login = (credentials) => API.post('login', credentials);
export const register = (credentials) => API.post('register', credentials);