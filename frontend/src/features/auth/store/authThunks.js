import { createAsyncThunk } from '@reduxjs/toolkit';
import AuthService from '../services/AuthService';
import TokenService from '../utils/TokenService';

export const loginThunk = createAsyncThunk(
  'auth/login',
  async (credentials, { rejectWithValue }) => {
    try {
      const user = await AuthService.login(credentials);
      return { token: TokenService.getToken(), user };
    } catch (error) {
      return rejectWithValue(error.message);
    }
  }
);

export const registerThunk = createAsyncThunk(
  'auth/register',
  async (userData, { rejectWithValue }) => {
    try {
      const user = await AuthService.register(userData);
      return { token: TokenService.getToken(), user };
    } catch (error) {
      return rejectWithValue(error.message);
    }
  }
);

export const logoutThunk = createAsyncThunk(
  'auth/logout',
  async (_, { rejectWithValue }) => {
    try {
      await AuthService.logout();
    } catch (error) {
      return rejectWithValue(error.message);
    }
  }
);