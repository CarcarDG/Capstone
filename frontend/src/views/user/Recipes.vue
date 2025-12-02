<template>
  <div class="recipes-page">
    <div class="page-header">
      <h1>Recipes</h1>
      <div class="header-actions">
        <el-input
          v-model="searchQuery"
          placeholder="Search recipes..."
          prefix-icon="Search"
          style="width: 300px"
          clearable
        />
        <el-button type="primary" @click="createRecipe">
          <el-icon><Plus /></el-icon> Create Recipe
        </el-button>
      </div>
    </div>

    <div class="filters">
      <el-radio-group v-model="selectedCategory" @change="handleCategoryChange">
        <el-radio-button value="all">All</el-radio-button>
        <el-radio-button value="main">Main Dishes</el-radio-button>
        <el-radio-button value="quick">Quick Meals</el-radio-button>
        <el-radio-button value="soups">Soups</el-radio-button>
        <el-radio-button value="baking">Baking</el-radio-button>
        <el-radio-button value="desserts">Desserts</el-radio-button>
        <el-radio-button value="vegetarian">Vegetarian</el-radio-button>
        <el-radio-button value="meat">Meat Dishes</el-radio-button>
      </el-radio-group>
    </div>

    <div class="recipes-grid" v-loading="loading">
      <el-card 
        v-for="recipe in filteredRecipes" 
        :key="recipe.id" 
        class="recipe-card" 
        shadow="hover"
        @click="viewRecipe(recipe.id)"
      >
        <img :src="recipe.coverImage" :alt="recipe.title" class="recipe-image" />
        <div class="recipe-content">
          <h3>{{ recipe.title }}</h3>
          <p class="description">{{ recipe.description }}</p>
          <div class="recipe-meta">
            <el-tag size="small">{{ recipe.difficulty }}</el-tag>
            <span><el-icon><Clock /></el-icon> {{ recipe.cookingTime }} min</span>
            <span><el-icon><User /></el-icon> {{ recipe.servings }} servings</span>
          </div>
          <div class="recipe-stats">
            <span><el-icon><View /></el-icon> {{ recipe.viewCount }}</span>
            <span><el-icon><Star /></el-icon> {{ recipe.likeCount }}</span>
            <span><el-icon><Collection /></el-icon> {{ recipe.collectCount }}</span>
          </div>
          <el-button type="primary" @click.stop="viewRecipe(recipe.id)">View Recipe</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Clock, User, View, Star, Collection, Plus } from '@element-plus/icons-vue'
import { recipeAPI } from '@/api/recipe'
import { ElMessage } from 'element-plus'

const router = useRouter()
const searchQuery = ref('')
const selectedCategory = ref('all')
const loading = ref(false)
const recipes = ref([])

const createRecipe = () => {
  router.push('/recipes/create')
}

const fetchRecipes = async () => {
  loading.value = true
  try {
    const data = await recipeAPI.getAllRecipes()
    console.log('API Response:', data)
    console.log('Response type:', typeof data)
    console.log('Is array:', Array.isArray(data))
    
    // Handle both array and object responses
    const recipeList = Array.isArray(data) ? data : (data.data || [])
    
    recipes.value = recipeList.map(recipe => ({
      ...recipe,
      category: getCategoryName(recipe.categoryId),
      viewCount: recipe.viewCount || 0,
      likeCount: recipe.likeCount || 0,
      collectCount: recipe.collectCount || 0,
      coverImage: recipe.coverImage || 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=300&h=200&fit=crop',
      description: recipe.description || '',
      difficulty: recipe.difficulty || 'Medium',
      cookingTime: recipe.cookingTime || 30,
      servings: recipe.servings || 2
    }))
    
    console.log('Processed recipes:', recipes.value)
  } catch (error) {
    console.error('Failed to fetch recipes:', error)
    console.error('Error details:', error.response || error.message)
    ElMessage.error('Failed to load recipes: ' + (error.message || 'Unknown error'))
  } finally {
    loading.value = false
  }
}

const getCategoryName = (categoryId) => {
  const categoryMap = {
    1: 'main',
    2: 'quick',
    3: 'soups',
    4: 'baking',
    5: 'desserts',
    6: 'vegetarian',
    7: 'meat'
  }
  return categoryMap[categoryId] || 'main'
}

const filteredRecipes = computed(() => {
  let result = recipes.value

  if (selectedCategory.value !== 'all') {
    result = result.filter(r => r.category === selectedCategory.value)
  }

  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    result = result.filter(r => 
      r.title.toLowerCase().includes(query) || 
      r.description.toLowerCase().includes(query)
    )
  }

  return result
})

const handleCategoryChange = () => {
  // Category filter is handled by computed property
}

const viewRecipe = (id) => {
  router.push(`/recipes/${id}`)
}

onMounted(() => {
  fetchRecipes()
})
</script>

<style scoped>
.recipes-page {
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h1 {
  margin: 0;
  font-size: 28px;
}

.header-actions {
  display: flex;
  gap: 10px;
  align-items: center;
}

.filters {
  margin-bottom: 30px;
}

.recipes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.recipe-card {
  cursor: pointer;
  transition: transform 0.2s;
}

.recipe-card:hover {
  transform: translateY(-5px);
}

.recipe-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 4px;
}

.recipe-content {
  padding: 15px 0;
}

.recipe-content h3 {
  margin: 0 0 10px 0;
  font-size: 18px;
}

.description {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
}

.recipe-meta {
  display: flex;
  gap: 15px;
  align-items: center;
  margin-bottom: 10px;
  font-size: 14px;
  color: #666;
}

.recipe-meta span {
  display: flex;
  align-items: center;
  gap: 5px;
}

.recipe-stats {
  display: flex;
  gap: 20px;
  margin-bottom: 15px;
  font-size: 14px;
  color: #999;
}

.recipe-stats span {
  display: flex;
  align-items: center;
  gap: 5px;
}
</style>
