import { createSlice } from '@reduxjs/toolkit';
import TokenService from '../utils/TokenService';

const initialState = {
  token: TokenService.getToken(),
  user: null,
  status: 'idle',
  error: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    clearAuthState: (state) => {
      state.token = null;
      state.user = null;
      state.status = 'idle';
      state.error = null;
    },
  },
  extraReducers: (builders) => {
    builders
      .addMatcher(
        (action) => action.type.endsWith('/pending'),
        (state) => {
          state.status = 'loading';
          state.error = null;
        }
      )
      .addMatcher(
        (action) => action.type.endsWith('/fulfilled') && action.type.includes('auth'),
        (state, action) => {
          state.status = 'succeeded';
          state.token = action.payload?.token || null;
          state.user = action.payload?.user || null;
        }
      )
      .addMatcher(
        (action) => action.type.endsWith('/rejected') && action.type.includes('auth'),
        (state, action) => {
          state.status = 'failed';
          state.error = action.payload;
        }
      );
  },
});

export const { clearAuthState } = authSlice.actions;
export default authSlice.reducer;