import TokenService from '../../features/auth/utils/TokenService';

export class ErrorInterceptor {
  static setup(axiosInstance) {
    axiosInstance.interceptors.response.use(
      response => response,
      error => {
        if (error.response?.status === 401) {
          TokenService.clearToken();
          window.location.href = '/auth';
        }
        return Promise.reject(error);
      }
    );
  }
}