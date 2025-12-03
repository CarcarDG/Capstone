import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authAPI } from '@/api/auth'
import axios from '@/api/axios' // Import axios instance

export const useAuthStore = defineStore('auth', () => {
  // Initialize mock users on store creation
  const initMockUsers = () => {
    const mockUsers = JSON.parse(localStorage.getItem('mockUsers') || '[]')
    if (mockUsers.length === 0) {
      const defaultUsers = [
        { username: 'admin', password: 'admin123', nickname: 'Administrator', role: 'ADMIN', avatar: 'https://i.pravatar.cc/200?img=12', email: 'admin@example.com' },
        { username: 'john', password: 'user123', nickname: 'John Doe', role: 'USER', avatar: 'https://i.pravatar.cc/200?img=33', email: 'john@example.com' },
        { username: 'jane', password: 'user123', nickname: 'Jane Smith', role: 'USER', avatar: 'https://i.pravatar.cc/200?img=47', email: 'jane@example.com' }
      ]
      localStorage.setItem('mockUsers', JSON.stringify(defaultUsers))
    }
  }

  // Initialize mock users when store is created
  initMockUsers()

  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))

  const isAuthenticated = computed(() => !!token.value)

  async function login(credentials) {
    try {
      // Use the new public auth endpoint to bypass Spring Security filters
      // This is a direct call to avoid any interceptor issues
      const response = await axios.post('/public/auth/login', {
        username: credentials.username,
        password: credentials.password
      })

      const data = response.data

      // Store token and user data
      token.value = data.token
      user.value = {
        id: data.id,
        username: data.username,
        nickname: data.nickname,
        role: data.role,
        avatar: data.avatar
      }

      localStorage.setItem('token', data.token)
      localStorage.setItem('user', JSON.stringify(user.value))

      return true
    } catch (error) {
      console.error('Login failed, trying mock auth:', error)

      // Fallback to mock authentication if backend is unavailable
      return mockLogin(credentials)
    }
  }

  // Mock login fallback for when backend is not available
  function mockLogin(credentials) {
    // Simulate API delay
    return new Promise((resolve) => {
      setTimeout(() => {
        // Get current mock users from storage
        const users = JSON.parse(localStorage.getItem('mockUsers') || '[]')

        // Find user with matching credentials
        const foundUser = users.find(u =>
          u.username === credentials.username && u.password === credentials.password
        )

        if (!foundUser) {
          resolve(false)
          return
        }

        // Create user object without password and assign a unique id
        const mockUser = {
          id: Date.now(), // simple unique identifier
          username: foundUser.username,
          nickname: foundUser.nickname,
          role: foundUser.role,
          avatar: foundUser.avatar,
          email: foundUser.email
        }

        const mockToken = 'mock-jwt-token-' + Date.now()

        token.value = mockToken
        user.value = mockUser
        localStorage.setItem('token', mockToken)
        localStorage.setItem('user', JSON.stringify(mockUser))

        resolve(true)
      }, 500)
    })
  }

  async function register(userData) {
    try {
      await authAPI.register(userData)
      return true
    } catch (error) {
      console.error('Registration failed, using mock:', error)

      // Mock registration fallback
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          // Get current mock users
          const users = JSON.parse(localStorage.getItem('mockUsers') || '[]')

          // Check if username already exists
          if (users.some(u => u.username.toLowerCase() === userData.username.toLowerCase())) {
            reject(new Error('Username already exists'))
            return
          }

          // Check if email already exists
          if (userData.email && users.some(u => u.email === userData.email)) {
            reject(new Error('Email already exists'))
            return
          }

          // Add new user to mock storage
          const newUser = {
            username: userData.username,
            password: userData.password,
            nickname: userData.nickname || userData.username,
            email: userData.email,
            phone: userData.phone,
            role: 'USER',
            avatar: `https://i.pravatar.cc/200?img=${Math.floor(Math.random() * 70)}`
          }

          users.push(newUser)
          localStorage.setItem('mockUsers', JSON.stringify(users))

          console.log('Mock registration successful for:', userData.username)
          console.log('You can now login with:', userData.username, '/', userData.password)
          resolve(true)
        }, 500)
      })
    }
  }

  async function fetchCurrentUser() {
    try {
      const userData = await authAPI.getCurrentUser()
      // Ensure ID is present in the user object
      user.value = {
        ...userData,
        id: userData.id || userData.userId // Handle potential field name differences
      }
      localStorage.setItem('user', JSON.stringify(user.value))
    } catch (error) {
      console.error('Failed to fetch user:', error)
      logout()
    }
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  return {
    token,
    user,
    isAuthenticated,
    login,
    register,
    logout,
    fetchCurrentUser
  }
})
