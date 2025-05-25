import TokenService from '../../features/auth/utils/TokenService';

export class AuthInterceptor {
  static setup(axiosInstance) {
    axiosInstance.interceptors.request.use(config => {
      const token = TokenService.getToken();
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    });
  }
}