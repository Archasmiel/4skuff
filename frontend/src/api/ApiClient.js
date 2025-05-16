import axios from 'axios';
import AuthInterceptor from './interceptors/AuthInterceptor';
import ErrorInterceptor from './interceptors/ErrorInterceptor';

class ApiClient {
  constructor(baseURL) {
    this.instance = axios.create({
      baseURL,
      headers: {
        'Content-Type': 'application/json',
      },
    });

    this.initializeInterceptors();
  }

  initializeInterceptors() {
    AuthInterceptor.setup(this.instance);
    ErrorInterceptor.setup(this.instance);
  }

  async get(url, config = {}) {
    return this.instance.get(url, config);
  }

  async post(url, data, config = {}) {
    return this.instance.post(url, data, config);
  }

  async put(url, data, config = {}) {
    return this.instance.put(url, data, config);
  }

  async delete(url, config = {}) {
    return this.instance.delete(url, config);
  }
}

export default ApiClient;