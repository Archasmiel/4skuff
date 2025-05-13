import { createAsyncThunk } from '@reduxjs/toolkit';
import * as authApi from '../../api/authApi';

export const loginThunk = createAsyncThunk(
  'auth/login',
  async (credentials, { rejectWithValue }) => {
    try {
      const response = await authApi.login(credentials);
      const token = response.data.token;

      const jwt_decode = (await import('jwt-decode')).default;
      const user = jwt_decode(token);

      return { token, user };
    } catch (error) {
      console.error('Login error:', error);
      return rejectWithValue(error.response?.data?.message || 'Login failed');
    }
  }
);

export const registerThunk = createAsyncThunk(
  'auth/register',
  async (userData, { rejectWithValue }) => {
    try {
      const response = await authApi.register(userData);
      const token = response.data.token;

      const jwt_decode = (await import('jwt-decode')).default;
      const user = jwt_decode(token);

      return { token, user };
    } catch (error) {
      console.error('Registration error:', error);
      return rejectWithValue(error.response?.data?.message || 'Registration failed');
    }
  }
);
