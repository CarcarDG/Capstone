<template>
  <div class="recipes-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Recipe Management</span>
        </div>
      </template>

      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="Search recipes..."
          style="width: 300px"
          clearable
          @input="handleSearch"
        >
          <template #append>
            <el-button icon="Search" @click="handleSearch">Search</el-button>
          </template>
        </el-input>
      </div>

      <el-table 
        :data="filteredRecipes" 
        style="width: 100%" 
        stripe
        v-loading="loading"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="Title" />
        <el-table-column label="Cover" width="100">
          <template #default="scope">
            <el-image 
              v-if="scope.row.coverImage"
              :src="scope.row.coverImage" 
              style="width: 60px; height: 60px" 
              fit="cover" 
            />
            <span v-else>No image</span>
          </template>
        </el-table-column>
        <el-table-column prop="categoryId" label="Category ID" width="120" />
        <el-table-column prop="userId" label="User ID" width="100" />
        <el-table-column label="Created Date" width="180">
          <template #default="scope">
            {{ formatDate(scope.row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="Status" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="250" fixed="right">
          <template #default="scope">
            <el-button 
              type="primary" 
              size="small"
              @click="handleEdit(scope.row)"
            >
              Edit
            </el-button>
            <el-button 
              type="success" 
              size="small"
              @click="handleFeature(scope.row)"
            >
              {{ scope.row.isFeatured ? 'Unfeature' : 'Feature' }}
            </el-button>
            <el-button 
              type="danger" 
              size="small"
              @click="handleDelete(scope.row)"
              :loading="scope.row.deleting"
            >
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="totalRecipes"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { recipeAPI } from '@/api/recipe'

const router = useRouter()
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const loading = ref(false)
const recipes = ref([])

const totalRecipes = computed(() => recipes.value.length)

const filteredRecipes = computed(() => {
  let result = recipes.value
  
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    result = result.filter(recipe => 
      recipe.title?.toLowerCase().includes(query) ||
      recipe.description?.toLowerCase().includes(query)
    )
  }
  
  // Pagination
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return result.slice(start, end)
})

const fetchRecipes = async () => {
  loading.value = true
  try {
    const data = await recipeAPI.getAllRecipes()
    recipes.value = data.map(recipe => ({
      ...recipe,
      deleting: false
    }))
  } catch (error) {
    console.error('Failed to fetch recipes:', error)
    ElMessage.error('Failed to load recipes')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  currentPage.value = 1
}

const handlePageChange = (page) => {
  currentPage.value = page
}

const handleEdit = (recipe) => {
  router.push(`/recipes/edit/${recipe.id}`)
}

const handleFeature = async (recipe) => {
  try {
    const newFeaturedStatus = recipe.isFeatured === 1 ? 0 : 1
    await recipeAPI.updateRecipe(recipe.id, {
      ...recipe,
      isFeatured: newFeaturedStatus
    })
    
    recipe.isFeatured = newFeaturedStatus
    ElMessage.success(
      newFeaturedStatus ? 'Recipe featured successfully' : 'Recipe unfeatured successfully'
    )
  } catch (error) {
    console.error('Failed to update recipe:', error)
    ElMessage.error('Failed to update recipe')
  }
}

const handleDelete = async (recipe) => {
  try {
    await ElMessageBox.confirm(
      `Are you sure you want to delete "${recipe.title}"? This action cannot be undone.`,
      'Delete Recipe',
      {
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel',
        type: 'warning',
        confirmButtonClass: 'el-button--danger'
      }
    )
    
    recipe.deleting = true
    
    try {
      await recipeAPI.deleteRecipe(recipe.id)
      
      // Remove from local list
      const index = recipes.value.findIndex(r => r.id === recipe.id)
      if (index > -1) {
        recipes.value.splice(index, 1)
      }
      
      ElMessage.success('Recipe deleted successfully')
    } catch (error) {
      console.error('Failed to delete recipe:', error)
      ElMessage.error('Failed to delete recipe. Please try again.')
      recipe.deleting = false
    }
  } catch (error) {
    // User cancelled
    console.log('Delete cancelled')
  }
}

const formatDate = (dateString) => {
  if (!dateString) return 'N/A'
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

const getStatusType = (status) => {
  switch (status) {
    case 1: return 'success'
    case 0: return 'info'
    case -1: return 'danger'
    default: return 'info'
  }
}

const getStatusText = (status) => {
  switch (status) {
    case 1: return 'Published'
    case 0: return 'Draft'
    case -1: return 'Deleted'
    default: return 'Unknown'
  }
}

onMounted(() => {
  fetchRecipes()
})
</script>

<style scoped>
.recipes-page {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
