import axios from 'axios'
import { ElMessage } from 'element-plus'

// Create axios instance
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Request interceptor - add token to headers
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor - handle errors
apiClient.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          ElMessage.error('Unauthorized. Please login again.')
          localStorage.removeItem('token')
          localStorage.removeItem('user')
          window.location.href = '/login'
          break
        case 403:
          // ElMessage.error('Access denied') // Suppress 403 error message
          break
        case 404:
          ElMessage.error('Resource not found')
          break
        case 500:
          ElMessage.error('Server error. Please try again later.')
          break
        default:
          ElMessage.error(error.response.data?.message || 'An error occurred')
      }
    } else if (error.request) {
      // Network error - don't show message, let the store handle fallback
      console.warn('Backend not available, using mock authentication')
    } else {
      ElMessage.error('An error occurred')
    }
    return Promise.reject(error)
  }
)

export default apiClient
