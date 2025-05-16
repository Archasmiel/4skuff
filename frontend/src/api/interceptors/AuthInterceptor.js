import TokenService from '../../features/auth/utils/TokenService';

class AuthInterceptor {
  static setup(axiosInstance) {
    axiosInstance.interceptors.request.use(
      (config) => {
        const token = TokenService.getToken();
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );
  }
}

export default AuthInterceptor;