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
        <el-radio-button label="all">All</el-radio-button>
        <el-radio-button label="main">Main Dishes</el-radio-button>
        <el-radio-button label="quick">Quick Meals</el-radio-button>
        <el-radio-button label="soups">Soups</el-radio-button>
        <el-radio-button label="baking">Baking</el-radio-button>
        <el-radio-button label="desserts">Desserts</el-radio-button>
        <el-radio-button label="vegetarian">Vegetarian</el-radio-button>
        <el-radio-button label="meat">Meat Dishes</el-radio-button>
      </el-radio-group>
    </div>

    <div class="recipes-grid">
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
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { Clock, User, View, Star, Collection, Plus } from '@element-plus/icons-vue'

const router = useRouter()
const searchQuery = ref('')
const selectedCategory = ref('all')

const createRecipe = () => {
  router.push('/recipes/create')
}

const recipes = ref([
  {
    id: 1,
    title: 'Healthy Shrimp Mushroom Tofu Soup',
    description: 'Low-calorie, high-protein nutritious healthy soup',
    coverImage: 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=800&h=600&fit=crop',
    category: 'soups',
    difficulty: 'EASY',
    cookingTime: 30,
    servings: 2,
    viewCount: 1234,
    likeCount: 89,
    collectCount: 56
  },
  {
    id: 2,
    title: 'Low-Calorie Sweet Pumpkin Pancakes',
    description: 'Healthy and delicious pumpkin treats',
    coverImage: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800&h=600&fit=crop',
    category: 'desserts',
    difficulty: 'EASY',
    cookingTime: 45,
    servings: 4,
    viewCount: 2341,
    likeCount: 156,
    collectCount: 89
  },
  {
    id: 3,
    title: 'Potato Shred Egg Pancake',
    description: 'Simple and quick breakfast option',
    coverImage: 'https://images.unsplash.com/photo-1525351484163-7529414344d8?w=800&h=600&fit=crop',
    category: 'quick',
    difficulty: 'EASY',
    cookingTime: 20,
    servings: 2,
    viewCount: 3456,
    likeCount: 234,
    collectCount: 123
  },
  {
    id: 4,
    title: 'Comfort Food That Heals the Soul',
    description: 'Exquisite and delicious creative cuisine',
    coverImage: 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800&h=600&fit=crop',
    category: 'main',
    difficulty: 'MEDIUM',
    cookingTime: 60,
    servings: 3,
    viewCount: 4567,
    likeCount: 345,
    collectCount: 234
  }
])

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
