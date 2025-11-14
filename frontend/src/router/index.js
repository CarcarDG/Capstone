import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/signup',
      name: 'Signup',
      component: () => import('@/views/Signup.vue')
    },
    {
      path: '/',
      component: () => import('@/views/user/Layout.vue'),
      meta: { requiresAuth: true },
      children: [
        {
          path: '',
          name: 'Home',
          component: () => import('@/views/user/Home.vue')
        },
        {
          path: 'recipes',
          name: 'Recipes',
          component: () => import('@/views/user/Recipes.vue')
        },
        {
          path: 'recipes/:id',
          name: 'RecipeDetail',
          component: () => import('@/views/user/RecipeDetail.vue')
        },
        {
          path: 'notes',
          name: 'Notes',
          component: () => import('@/views/user/Notes.vue')
        },
        {
          path: 'notes/:id',
          name: 'NoteDetail',
          component: () => import('@/views/user/NoteDetail.vue')
        },
        {
          path: 'collections',
          name: 'Collections',
          component: () => import('@/views/user/Collections.vue')
        },
        {
          path: 'meal-planner',
          name: 'MealPlanner',
          component: () => import('@/views/user/MealPlanner.vue')
        },
        {
          path: 'announcements',
          name: 'Announcements',
          component: () => import('@/views/user/Announcements.vue')
        },
        {
          path: 'profile',
          name: 'Profile',
          component: () => import('@/views/user/Profile.vue')
        },
        {
          path: 'recipes/create',
          name: 'CreateRecipe',
          component: () => import('@/views/user/CreateRecipe.vue')
        },
        {
          path: 'recipes/edit/:id',
          name: 'EditRecipe',
          component: () => import('@/views/user/CreateRecipe.vue')
        }
      ]
    },
    {
      path: '/admin',
      name: 'AdminLayout',
      component: () => import('@/views/admin/Layout.vue'),
      meta: { requiresAuth: true, requiresAdmin: true },
      children: [
        {
          path: '',
          name: 'AdminHome',
          component: () => import('@/views/admin/Home.vue')
        },
        {
          path: 'users',
          name: 'AdminUsers',
          component: () => import('@/views/admin/Users.vue')
        },
        {
          path: 'recipes',
          name: 'AdminRecipes',
          component: () => import('@/views/admin/Recipes.vue')
        },
        {
          path: 'notes',
          name: 'AdminNotes',
          component: () => import('@/views/admin/Notes.vue')
        },
        {
          path: 'announcements',
          name: 'AdminAnnouncements',
          component: () => import('@/views/admin/Announcements.vue')
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
  } else if (to.meta.requiresAdmin && authStore.user?.role !== 'ADMIN') {
    next('/')
  } else if (to.path === '/login' && authStore.isAuthenticated) {
    next(authStore.user?.role === 'ADMIN' ? '/admin' : '/')
  } else {
    next()
  }
})

export default router
