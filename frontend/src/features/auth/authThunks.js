import { createAsyncThunk } from '@reduxjs/toolkit';
import * as authApi from '../../api/authApi';
import { jwtDecode } from 'jwt-decode';

export const loginThunk = createAsyncThunk(
  'auth/login',
  async (credentials, { rejectWithValue }) => {
    try {
      const response = await authApi.login(credentials);
      const token = response.data.jwtToken;
      
      if (!token) {
        throw new Error('No token received from server');
      }
      
      const user = jwtDecode(token);
      return { token, user };
    } catch (error) {
      console.error('Login error:', error);
      return rejectWithValue(error.response?.data?.message || error.message || 'Login failed');
    }
  }
);

export const registerThunk = createAsyncThunk(
  'auth/signup',
  async (userData, { rejectWithValue }) => {
    try {
      const response = await authApi.register(userData);
      const token = response.data.jwtToken;

      if (!token) {
        throw new Error('No token received after registration');
      }

      const user = jwtDecode(token);
      return { token, user };
    } catch (error) {
      console.error('Registration error:', error);
      return rejectWithValue(
        error.response?.data?.message || 
        error.message || 
        'Registration failed'
      );
    }
  }
);