<template>
  <div class="user-layout">
    <header class="header">
      <div class="header-content">
        <div class="logo" @click="router.push('/')">
          <h1>🍎 Recipe Platform</h1>
        </div>
        <nav class="nav">
          <router-link to="/" :class="{ active: route.path === '/' }">Home</router-link>
          <router-link to="/recipes" :class="{ active: route.path.startsWith('/recipes') }">Recipes</router-link>
          <router-link to="/notes" :class="{ active: route.path.startsWith('/notes') }">Notes</router-link>
          <router-link to="/collections" :class="{ active: route.path === '/collections' }">Collections</router-link>
          <router-link to="/meal-planner" :class="{ active: route.path === '/meal-planner' }">Meal Planner</router-link>
          <router-link to="/announcements" :class="{ active: route.path === '/announcements' }">Announcements</router-link>
        </nav>
        <div class="search-bar">
          <el-input placeholder="Search recipes, ingredients" prefix-icon="Search">
            <template #append>
              <el-button icon="Search">Search</el-button>
            </template>
          </el-input>
        </div>
        <div class="user-info">
          <el-dropdown @command="handleCommand">
            <span class="user-dropdown">
              <el-avatar :src="authStore.user?.avatar || 'https://i.pravatar.cc/200?img=33'" />
              <span>{{ authStore.user?.nickname }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">Profile</el-dropdown-item>
                <el-dropdown-item command="collections">My Collections</el-dropdown-item>
                <el-dropdown-item command="meal-planner">Meal Planner</el-dropdown-item>
                <el-dropdown-item divided command="logout">Logout</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>

    <main class="main-content">
      <router-view />
    </main>

    <footer class="footer">
      <p>About Us · App Center · Help · Privacy Policy · User Agreement</p>
      <p>Recipe Platform Inc. ICP1131232888 License X201407774-174</p>
    </footer>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const handleCommand = (command) => {
  if (command === 'logout') {
    authStore.logout()
    router.push('/login')
  } else if (command === 'collections') {
    router.push('/collections')
  } else if (command === 'meal-planner') {
    router.push('/meal-planner')
  } else if (command === 'profile') {
    router.push('/profile')
  }
}
</script>

<style scoped>
.user-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f5f5f5;
}

.header {
  background: linear-gradient(135deg, #ff6b6b 0%, #ff8e53 100%);
  color: white;
  padding: 0 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1400px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  gap: 30px;
  padding: 15px 0;
}

.logo {
  cursor: pointer;
  transition: opacity 0.3s;
}

.logo:hover {
  opacity: 0.9;
}

.logo h1 {
  font-size: 24px;
  margin: 0;
  white-space: nowrap;
}

.nav {
  display: flex;
  gap: 25px;
}

.nav a {
  color: white;
  text-decoration: none;
  font-size: 16px;
  transition: opacity 0.3s;
  padding: 5px 0;
  border-bottom: 2px solid transparent;
}

.nav a:hover,
.nav a.active {
  opacity: 0.8;
  border-bottom-color: white;
}

.search-bar {
  flex: 1;
  max-width: 400px;
}

.user-dropdown {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.main-content {
  flex: 1;
  max-width: 1400px;
  width: 100%;
  margin: 0 auto;
  padding: 20px;
}

.footer {
  background: #333;
  color: #999;
  text-align: center;
  padding: 30px 20px;
  margin-top: 40px;
}

.footer p {
  margin: 10px 0;
  font-size: 14px;
}

@media (max-width: 1200px) {
  .nav {
    gap: 15px;
  }
  
  .nav a {
    font-size: 14px;
  }
}

@media (max-width: 992px) {
  .header-content {
    flex-wrap: wrap;
  }
  
  .search-bar {
    order: 3;
    flex-basis: 100%;
    max-width: 100%;
  }
}
</style>
