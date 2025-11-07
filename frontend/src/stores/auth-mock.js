import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

// Mock authentication store (no backend required)
export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))

  const isAuthenticated = computed(() => !!token.value)

  async function login(credentials) {
    // Mock login - simulate API call
    await new Promise(resolve => setTimeout(resolve, 500))
    
    // Mock user data based on username
    let mockUser
    if (credentials.username === 'admin') {
      mockUser = {
        username: 'admin',
        nickname: 'Administrator',
        role: 'ADMIN',
        avatar: 'https://i.pravatar.cc/200?img=12'
      }
    } else {
      mockUser = {
        username: credentials.username,
        nickname: credentials.username === 'john' ? 'John Doe' : 'Jane Smith',
        role: 'USER',
        avatar: credentials.username === 'john' ? 'https://i.pravatar.cc/200?img=33' : 'https://i.pravatar.cc/200?img=47'
      }
    }
    
    const mockToken = 'mock-jwt-token-' + Date.now()
    
    token.value = mockToken
    user.value = mockUser
    localStorage.setItem('token', mockToken)
    localStorage.setItem('user', JSON.stringify(mockUser))
    
    return true
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
    logout
  }
})
