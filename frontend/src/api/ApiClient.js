import axios from 'axios';
import { AuthInterceptor } from './interceptors/AuthInterceptor';
import { ErrorInterceptor } from './interceptors/ErrorInterceptor';

export class ApiClient {
  constructor(baseURL) {
    this.instance = axios.create({ baseURL });
    AuthInterceptor.setup(this.instance);
    ErrorInterceptor.setup(this.instance);
  }

  get(url, config) {
    return this.instance.get(url, config);
  }

  post(url, data, config) {
    return this.instance.post(url, data, config);
  }
}
