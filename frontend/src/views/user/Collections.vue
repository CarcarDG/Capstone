<template>
  <div class="collections-page">
    <div class="page-header">
      <h1>My Collections</h1>
      <el-radio-group v-model="activeTab">
        <el-radio-button label="recipes">Recipes</el-radio-button>
        <el-radio-button label="notes">Notes</el-radio-button>
      </el-radio-group>
    </div>

    <div v-if="activeTab === 'recipes'" class="collections-grid">
      <el-card 
        v-for="item in collectedRecipes" 
        :key="item.id" 
        class="collection-card" 
        shadow="hover"
        @click="viewItem(item.id)"
      >
        <img :src="item.coverImage" :alt="item.title" class="collection-image" />
        <div class="collection-content">
          <h3>{{ item.title }}</h3>
          <p class="description">{{ item.description }}</p>
          <div class="collection-meta">
            <el-tag size="small">{{ item.difficulty }}</el-tag>
            <span><el-icon><Clock /></el-icon> {{ item.cookingTime }} min</span>
          </div>
          <div class="collection-actions">
            <el-button type="primary" size="small" @click.stop="viewItem(item.id)">View</el-button>
            <el-button type="danger" size="small" plain @click.stop="removeCollection(item.id)">Remove</el-button>
          </div>
        </div>
      </el-card>
    </div>

    <div v-else class="collections-grid">
      <el-card 
        v-for="item in collectedNotes" 
        :key="item.id" 
        class="collection-card" 
        shadow="hover"
        @click="viewNote(item.id)"
      >
        <img :src="item.images[0]" :alt="item.title" class="collection-image" />
        <div class="collection-content">
          <h3>{{ item.title }}</h3>
          <p class="description">{{ item.content }}</p>
          <div class="collection-meta">
            <el-tag v-for="tag in item.tags.split(',')" :key="tag" size="small">{{ tag }}</el-tag>
          </div>
          <div class="collection-actions">
            <el-button type="primary" size="small" @click.stop="viewNote(item.id)">View</el-button>
            <el-button type="danger" size="small" plain @click.stop="removeCollection(item.id)">Remove</el-button>
          </div>
        </div>
      </el-card>
    </div>

    <el-empty v-if="(activeTab === 'recipes' && collectedRecipes.length === 0) || 
                     (activeTab === 'notes' && collectedNotes.length === 0)" 
              description="No collections yet" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Clock } from '@element-plus/icons-vue'

const router = useRouter()
const activeTab = ref('recipes')

const collectedRecipes = ref([
  {
    id: 1,
    title: 'Healthy Shrimp Mushroom Tofu Soup',
    description: 'Low-calorie, high-protein nutritious healthy soup',
    coverImage: 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=800&h=600&fit=crop',
    difficulty: 'EASY',
    cookingTime: 30
  },
  {
    id: 2,
    title: 'Low-Calorie Sweet Pumpkin Pancakes',
    description: 'Healthy and delicious pumpkin treats',
    coverImage: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800&h=600&fit=crop',
    difficulty: 'EASY',
    cookingTime: 45
  }
])

const collectedNotes = ref([
  {
    id: 1,
    title: 'Braised Pork Belly (Super Detailed)',
    content: 'Made super delicious braised pork today, fatty but not greasy...',
    images: ['https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=800&h=600&fit=crop'],
    tags: 'braised pork,home cooking'
  }
])

const viewItem = (id) => {
  router.push(`/recipes/${id}`)
}

const viewNote = (id) => {
  router.push(`/notes/${id}`)
}

const removeCollection = (id) => {
  ElMessageBox.confirm('Remove this item from collections?', 'Confirm', {
    confirmButtonText: 'Remove',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(() => {
    if (activeTab.value === 'recipes') {
      const index = collectedRecipes.value.findIndex(item => item.id === id)
      if (index > -1) collectedRecipes.value.splice(index, 1)
    } else {
      const index = collectedNotes.value.findIndex(item => item.id === id)
      if (index > -1) collectedNotes.value.splice(index, 1)
    }
    ElMessage.success('Removed from collections')
  })
}
</script>

<style scoped>
.collections-page {
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.page-header h1 {
  margin: 0;
  font-size: 28px;
}

.collections-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.collection-card {
  transition: transform 0.2s;
}

.collection-card:hover {
  transform: translateY(-5px);
}

.collection-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 4px;
}

.collection-content {
  padding: 15px 0;
}

.collection-content h3 {
  margin: 0 0 10px 0;
  font-size: 18px;
}

.description {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.collection-meta {
  display: flex;
  gap: 10px;
  align-items: center;
  margin-bottom: 15px;
  font-size: 14px;
  flex-wrap: wrap;
}

.collection-meta span {
  display: flex;
  align-items: center;
  gap: 5px;
}

.collection-actions {
  display: flex;
  gap: 10px;
}
</style>
