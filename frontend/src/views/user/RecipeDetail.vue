<template>
  <div class="recipe-detail-page">
    <div class="back-button">
      <el-button @click="goBack" icon="ArrowLeft">Back to Recipes</el-button>
    </div>

    <div class="recipe-header">
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

    <div class="recipe-content">
      <el-card class="ingredients-card">
        <template #header>
          <h2>Ingredients</h2>
        </template>
        <ul class="ingredients-list">
          <li v-for="(ingredient, index) in recipe.ingredients" :key="index">
            <el-checkbox v-model="ingredient.checked" />
            <span>{{ ingredient.name }}</span>
            <span class="amount">{{ ingredient.amount }}</span>
          </li>
        </ul>
      </el-card>

      <el-card class="steps-card">
        <template #header>
          <h2>Cooking Steps</h2>
        </template>
        <div class="steps-list">
          <div v-for="step in recipe.steps" :key="step.step" class="step-item">
            <div class="step-number">{{ step.step }}</div>
            <div class="step-content">
              <p>{{ step.description }}</p>
            </div>
          </div>
        </div>
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

const route = useRoute()
const router = useRouter()

const recipes = {
  1: {
    id: 1,
    title: 'Healthy Shrimp Mushroom Tofu Soup',
    description: 'Low-calorie, high-protein nutritious healthy soup',
    coverImage: 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=800&h=600&fit=crop',
    difficulty: 'EASY',
    cookingTime: 30,
    servings: 2,
    viewCount: 1234,
    likeCount: 89,
    collectCount: 56,
    ingredients: [
      { name: 'Shrimp', amount: '200g', checked: false },
      { name: 'Mushrooms', amount: '150g', checked: false },
      { name: 'Tofu', amount: '1 block', checked: false },
      { name: 'Ginger and scallions', amount: 'to taste', checked: false }
    ],
    steps: [
      { step: 1, description: 'Clean shrimp and devein, slice mushrooms, cube tofu' },
      { step: 2, description: 'Boil water in pot, add ginger slices' },
      { step: 3, description: 'After water boils, add tofu and mushrooms, cook for 5 minutes' },
      { step: 4, description: 'Add shrimp and cook until they change color' },
      { step: 5, description: 'Season with salt and sprinkle scallions' }
    ],
    tips: 'Do not overcook shrimp, just until color changes to keep tender texture'
  },
  2: {
    id: 2,
    title: 'Low-Calorie Sweet Pumpkin Pancakes',
    description: 'Healthy and delicious pumpkin treats',
    coverImage: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800&h=600&fit=crop',
    difficulty: 'EASY',
    cookingTime: 45,
    servings: 4,
    viewCount: 2341,
    likeCount: 156,
    collectCount: 89,
    ingredients: [
      { name: 'Pumpkin', amount: '300g', checked: false },
      { name: 'Glutinous rice flour', amount: '200g', checked: false },
      { name: 'Sugar', amount: '30g', checked: false }
    ],
    steps: [
      { step: 1, description: 'Peel pumpkin, cut into pieces and steam until soft' },
      { step: 2, description: 'While hot, add sugar and mash into puree' },
      { step: 3, description: 'Gradually add glutinous rice flour and knead into dough' },
      { step: 4, description: 'Divide into small portions and flatten' },
      { step: 5, description: 'Pan fry on low heat until golden on both sides' }
    ],
    tips: 'Dough should not be too soft, or it will be hard to shape'
  },
  3: {
    id: 3,
    title: 'Potato Shred Egg Pancake',
    description: 'Simple and quick breakfast option',
    coverImage: 'https://images.unsplash.com/photo-1525351484163-7529414344d8?w=800&h=600&fit=crop',
    difficulty: 'EASY',
    cookingTime: 20,
    servings: 2,
    viewCount: 3456,
    likeCount: 234,
    collectCount: 123,
    ingredients: [
      { name: 'Potatoes', amount: '2', checked: false },
      { name: 'Eggs', amount: '2', checked: false },
      { name: 'Flour', amount: '50g', checked: false },
      { name: 'Chopped scallions', amount: 'to taste', checked: false }
    ],
    steps: [
      { step: 1, description: 'Shred potatoes and rinse with water to remove starch' },
      { step: 2, description: 'Drain water, add eggs, flour, scallions, and salt' },
      { step: 3, description: 'Mix evenly into batter' },
      { step: 4, description: 'Brush oil in pan, pour batter and spread evenly' },
      { step: 5, description: 'Cook on low heat until golden on both sides' }
    ],
    tips: 'Shred potatoes as fine as possible for better texture'
  },
  4: {
    id: 4,
    title: 'Comfort Food That Heals the Soul',
    description: 'Exquisite and delicious creative cuisine',
    coverImage: 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800&h=600&fit=crop',
    difficulty: 'MEDIUM',
    cookingTime: 60,
    servings: 3,
    viewCount: 4567,
    likeCount: 345,
    collectCount: 234,
    ingredients: [
      { name: 'Main ingredients', amount: 'according to personal preference', checked: false },
      { name: 'Seasonings', amount: 'to taste', checked: false }
    ],
    steps: [
      { step: 1, description: 'Prepare required ingredients' },
      { step: 2, description: 'Cook according to steps' },
      { step: 3, description: 'Plate and garnish' }
    ],
    tips: 'Pay attention to heat control'
  }
}

const recipe = ref(recipes[1])

onMounted(() => {
  const recipeId = parseInt(route.params.id)
  if (recipes[recipeId]) {
    recipe.value = recipes[recipeId]
  }
})

const goBack = () => {
  router.push('/recipes')
}

const likeRecipe = () => {
  recipe.value.likeCount++
  ElMessage.success('Liked!')
}

const collectRecipe = () => {
  recipe.value.collectCount++
  ElMessage.success('Saved to collections!')
}

const addToMealPlan = () => {
  ElMessage.success('Added to meal plan!')
  router.push('/meal-planner')
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
