import React from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import AppRouter from '../routes/AppRouter';
import { store } from './store';
import theme from '../shared/styles/theme';
import '../shared/styles/base.css';

function App() {
  return (
    <Provider store={store}>
      <ThemeProvider theme={theme}>
        <BrowserRouter>
          <AppRouter />
        </BrowserRouter>
      </ThemeProvider>
    </Provider>
  );
}

export default App;