class ErrorInterceptor {
  static setup(axiosInstance) {
    axiosInstance.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response) {
          switch (error.response.status) {
            case 401:
              console.error('Unauthorized access');
              break;
            case 403:
              console.error('Forbidden access');
              break;
            case 500:
              console.error('Server error');
              break;
            default:
              console.error('Request error', error.message);
          }
        }
        return Promise.reject(error);
      }
    );
  }
}

export default ErrorInterceptor;