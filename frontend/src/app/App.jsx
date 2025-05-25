// C:\Users\user\Documents\4skuff\4skuff\frontend\src\app\App.jsx
import React, { useEffect, useState } from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { AppRouter } from '../routes/AppRouter';
import { store } from './store';
import theme from '../shared/styles/theme';
import '../shared/styles/base.css';
import { fetchUserDataThunk } from '../features/auth/store/authThunks';
import { useAuth } from './hooks/useAuth';

function AuthInitializer({ children }) {
  const [isAuthChecked, setIsAuthChecked] = useState(false);
  const { isAuthenticated } = useAuth();

  useEffect(() => {
    const initAuth = async () => {
      const token = localStorage.getItem('token');
      if (token) {
        try {
          await store.dispatch(fetchUserDataThunk()).unwrap();
        } catch (error) {
          localStorage.removeItem('token');
        }
      }
      setIsAuthChecked(true);
    };

    initAuth();
  }, []);

  if (!isAuthChecked) {
    return <div>Loading...</div>;
  }

  return children;
}

function App() {
  return (
    <Provider store={store}>
      <ThemeProvider theme={theme}>
        <BrowserRouter>
          <AuthInitializer>
            <AppRouter />
          </AuthInitializer>
        </BrowserRouter>
      </ThemeProvider>
    </Provider>
  );
}

export default App;