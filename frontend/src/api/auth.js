import apiClient from './axios'

export const authAPI = {
  // Login
  login(credentials) {
    return apiClient.post('/auth/login', credentials)
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
