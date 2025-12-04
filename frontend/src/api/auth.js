import apiClient from './axios'

export const authAPI = {
  // Login - using public endpoint to bypass Spring Security issues
  login(credentials) {
    return apiClient.post('/public/auth/login', credentials)
  },
  
  // Test public auth endpoint
  testPublicAuth() {
    return apiClient.get('/public/auth/test')
  },

  // Register
  register(userData) {
    return apiClient.post('/auth/register', userData)
  },

  // Get current user info
  getCurrentUser() {
    return apiClient.get('/auth/me')
  },

  // Logout (if backend has logout endpoint)
  logout() {
    return apiClient.post('/auth/logout')
  }
}
