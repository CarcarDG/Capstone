import { computed } from 'vue'
import { useAuthStore } from '@/stores/auth'

/**
 * Composable for authentication
 * Provides easy access to auth state and methods
 */
export function useAuth() {
  const authStore = useAuthStore()

  const isAuthenticated = computed(() => authStore.isAuthenticated)
  const user = computed(() => authStore.user)
  const isAdmin = computed(() => authStore.user?.role === 'ADMIN')
  const isUser = computed(() => authStore.user?.role === 'USER')

  return {
    isAuthenticated,
    user,
    isAdmin,
    isUser,
    login: authStore.login,
    register: authStore.register,
    logout: authStore.logout,
    fetchCurrentUser: authStore.fetchCurrentUser
  }
}
