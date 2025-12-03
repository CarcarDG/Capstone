<template>
  <div class="recipe-detail-page" v-loading="loading">
    <div class="back-button">
      <el-button @click="goBack" icon="ArrowLeft">Back to Recipes</el-button>
    </div>

    <div class="recipe-header" v-if="recipe.id">
      <img :src="recipe.coverImage" :alt="recipe.title" class="recipe-cover" />
      <div class="recipe-header-info">
        <h1>{{ recipe.title }}</h1>
        <p class="description">{{ recipe.description }}</p>
        
        <div class="recipe-meta">
          <el-tag type="success">{{ recipe.difficulty }}</el-tag>
          <span><el-icon><Clock /></el-icon> {{ recipe.cookingTime }} min</span>
          <span><el-icon><User /></el-icon> {{ recipe.servings }} servings</span>
        </div>

        <div class="recipe-stats">
          <span><el-icon><View /></el-icon> {{ recipe.viewCount }} views</span>
          <span><el-icon><Star /></el-icon> {{ recipe.likeCount }} likes</span>
          <span><el-icon><Collection /></el-icon> {{ recipe.collectCount }} saved</span>
        </div>

        <div class="action-buttons">
          <el-button type="primary" @click="likeRecipe">
            <el-icon><Star /></el-icon> Like
          </el-button>
          <el-button @click="collectRecipe">
            <el-icon><Collection /></el-icon> Save
          </el-button>
          <el-button @click="addToMealPlan">
            <el-icon><Calendar /></el-icon> Add to Meal Plan
          </el-button>
        </div>
      </div>
    </div>

    <div class="recipe-content" v-if="recipe.id">
      <el-card class="ingredients-card">
        <template #header>
          <h2>Ingredients</h2>
        </template>
        <ul class="ingredients-list" v-if="recipe.ingredients && recipe.ingredients.length > 0">
          <li v-for="(ingredient, index) in recipe.ingredients" :key="index">
            <el-checkbox v-model="ingredient.checked" />
            <span>{{ ingredient.name }}</span>
            <span class="amount">{{ ingredient.amount }}</span>
          </li>
        </ul>
        <p v-else>No ingredients listed</p>
      </el-card>

      <el-card class="steps-card">
        <template #header>
          <h2>Cooking Steps</h2>
        </template>
        <div class="steps-list" v-if="recipe.steps && recipe.steps.length > 0">
          <div v-for="step in recipe.steps" :key="step.step" class="step-item">
            <div class="step-number">{{ step.step }}</div>
            <div class="step-content">
              <p>{{ step.description }}</p>
            </div>
          </div>
        </div>
        <p v-else>No cooking steps listed</p>
      </el-card>

      <el-card v-if="recipe.tips" class="tips-card">
        <template #header>
          <h2>💡 Tips</h2>
        </template>
        <p>{{ recipe.tips }}</p>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Clock, User, View, Star, Collection, Calendar, ArrowLeft } from '@element-plus/icons-vue'
import { recipeAPI } from '@/api/recipe'
import { collectionAPI } from '@/api/collection'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const currentUser = ref(JSON.parse(localStorage.getItem('user') || '{}'))

const recipe = ref({
  id: 0,
  title: '',
  description: '',
  coverImage: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c', // Default image
  difficulty: 'MEDIUM',
  cookingTime: 0,
  servings: 0,
  viewCount: 0,
  likeCount: 0,
  collectCount: 0,
  ingredients: [],
  steps: [],
  tips: ''
})

const isCollected = ref(false)

// Function to refresh user from localStorage
const refreshUser = () => {
  const userData = localStorage.getItem('user')
  if (userData) {
    try {
      currentUser.value = JSON.parse(userData)
    } catch (e) {
      console.error('Failed to parse user data', e)
      currentUser.value = {}
    }
  } else {
    currentUser.value = {}
  }
}

const fetchRecipe = async (id) => {
  loading.value = true
  try {
    const data = await recipeAPI.getRecipeById(id)
    
    // Parse ingredients and steps from JSON strings
    let ingredients = []
    let steps = []
    
    try {
      ingredients = typeof data.ingredients === 'string' 
        ? JSON.parse(data.ingredients).map(ing => ({ ...ing, checked: false }))
        : (data.ingredients || [])
    } catch (e) {
      console.error('Failed to parse ingredients:', e)
      ingredients = []
    }
    
    try {
      steps = typeof data.steps === 'string'
        ? JSON.parse(data.steps)
        : (data.steps || [])
    } catch (e) {
      console.error('Failed to parse steps:', e)
      steps = []
    }
    
    recipe.value = {
      ...data,
      ingredients,
      steps,
      coverImage: data.coverImage || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c', // Fallback image
      viewCount: data.viewCount || 0,
      likeCount: data.likeCount || 0,
      collectCount: data.collectCount || 0
    }

    // Refresh user data and check collection status
    refreshUser()
    if (currentUser.value.id) {
      checkCollectionStatus(id)
    }
  } catch (error) {
    console.error('Failed to fetch recipe:', error)
    ElMessage.error('Failed to load recipe')
  } finally {
    loading.value = false
  }
}

const checkCollectionStatus = async (recipeId) => {
  try {
    const status = await collectionAPI.checkCollection(currentUser.value.id, recipeId)
    isCollected.value = status
  } catch (error) {
    console.error('Failed to check collection status:', error)
  }
}

onMounted(() => {
  const recipeId = route.params.id
  if (recipeId) {
    fetchRecipe(recipeId)
  }
})

const goBack = () => {
  router.push('/recipes')
}

const likeRecipe = async () => {
  // Implement like functionality if backend supports it
  recipe.value.likeCount++
  ElMessage.success('Liked!')
}

const collectRecipe = async () => {
  refreshUser() // Ensure we have the latest user data
  
  if (!currentUser.value.id) {
    ElMessage.warning('Please login to save recipes')
    return
  }

  try {
    if (isCollected.value) {
      await collectionAPI.removeFromCollection(currentUser.value.id, recipe.value.id)
      recipe.value.collectCount--
      isCollected.value = false
      ElMessage.success('Removed from collections')
    } else {
      await collectionAPI.addToCollection(currentUser.value.id, recipe.value.id)
      recipe.value.collectCount++
      isCollected.value = true
      ElMessage.success('Saved to collections!')
    }
  } catch (error) {
    console.error('Failed to update collection:', error)
    ElMessage.error('Failed to update collection')
  }
}

const addToMealPlan = () => {
  router.push({
    path: '/meal-planner',
    query: { addRecipe: recipe.value.id }
  })
}
</script>

<style scoped>
.recipe-detail-page {
  max-width: 1000px;
  margin: 0 auto;
}

.back-button {
  margin-bottom: 20px;
}

.recipe-header {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
  margin-bottom: 30px;
}

.recipe-cover {
  width: 100%;
  height: 400px;
  object-fit: cover;
  border-radius: 10px;
}

.recipe-header-info h1 {
  margin: 0 0 15px 0;
  font-size: 32px;
}

.description {
  color: #666;
  font-size: 16px;
  margin-bottom: 20px;
}

.recipe-meta {
  display: flex;
  gap: 20px;
  align-items: center;
  margin-bottom: 15px;
  font-size: 16px;
}

.recipe-meta span {
  display: flex;
  align-items: center;
  gap: 5px;
}

.recipe-stats {
  display: flex;
  gap: 25px;
  margin-bottom: 25px;
  font-size: 14px;
  color: #999;
}

.recipe-stats span {
  display: flex;
  align-items: center;
  gap: 5px;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.recipe-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.ingredients-card h2,
.steps-card h2,
.tips-card h2 {
  margin: 0;
  font-size: 22px;
}

.ingredients-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.ingredients-list li {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.ingredients-list li:last-child {
  border-bottom: none;
}

.ingredients-list .amount {
  margin-left: auto;
  color: #666;
  font-weight: 500;
}

.steps-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.step-item {
  display: flex;
  gap: 20px;
}

.step-number {
  flex-shrink: 0;
  width: 40px;
  height: 40px;
  background: #409eff;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  font-size: 18px;
}

.step-content p {
  margin: 0;
  line-height: 1.6;
  font-size: 16px;
}

.tips-card p {
  margin: 0;
  line-height: 1.6;
  font-size: 16px;
  color: #666;
}

@media (max-width: 768px) {
  .recipe-header {
    grid-template-columns: 1fr;
  }
}
</style>
