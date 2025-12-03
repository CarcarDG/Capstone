import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authAPI } from '@/api/auth'

// Mock user storage (in-memory, for development without backend)
const mockUsers = JSON.parse(localStorage.getItem('mockUsers') || '[]')

// Initialize with default users if empty
if (mockUsers.length === 0) {
  mockUsers.push(
    { username: 'admin', password: 'admin123', nickname: 'Administrator', role: 'ADMIN', avatar: 'https://i.pravatar.cc/200?img=12', email: 'admin@example.com' },
    { username: 'john', password: 'user123', nickname: 'John Doe', role: 'USER', avatar: 'https://i.pravatar.cc/200?img=33', email: 'john@example.com' },
    { username: 'jane', password: 'user123', nickname: 'Jane Smith', role: 'USER', avatar: 'https://i.pravatar.cc/200?img=47', email: 'jane@example.com' }
  )
  localStorage.setItem('mockUsers', JSON.stringify(mockUsers))
}

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))

  const isAuthenticated = computed(() => !!token.value)

  async function login(credentials) {
    try {
      const response = await authAPI.login(credentials)

      // Store token and user data
      token.value = response.token
      user.value = {
        id: response.id, // Ensure ID is stored
        username: response.username,
        nickname: response.nickname,
        role: response.role,
        avatar: response.avatar
      }

      localStorage.setItem('token', response.token)
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

        // Create user object without password
        const mockUser = {
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
      user.value = userData
      localStorage.setItem('user', JSON.stringify(userData))
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
