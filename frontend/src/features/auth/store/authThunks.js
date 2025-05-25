import { createAsyncThunk } from '@reduxjs/toolkit';
import { AuthService } from '../services/AuthService';

export const loginThunk = createAsyncThunk(
  'auth/login',
  async (credentials, { rejectWithValue }) => {
    try {
      const token = await AuthService.login(credentials);
      const user = await AuthService.getMe();
      return { token, user };
    } catch (error) {
      return rejectWithValue(error.message);
    }
  }
);

export const registerThunk = createAsyncThunk(
  'auth/register',
  async (userData, { rejectWithValue }) => {
    try {
      const token = await AuthService.register(userData);
      const user = await AuthService.getMe();
      return { token, user };
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

export const fetchUserDataThunk = createAsyncThunk(
  'auth/fetchUserData',
  async (_, { rejectWithValue }) => {
    try {
      const user = await AuthService.getMe();
      return { user };
    } catch (error) {
      return rejectWithValue(error.message);
    }
  }
);