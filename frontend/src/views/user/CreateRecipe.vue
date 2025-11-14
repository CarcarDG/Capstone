<template>
  <div class="create-recipe-page">
    <div class="page-header">
      <h1>{{ isEdit ? 'Edit Recipe' : 'Create New Recipe' }}</h1>
      <el-button @click="$router.back()">
        <el-icon><ArrowLeft /></el-icon> Back
      </el-button>
    </div>

    <el-form :model="recipeForm" :rules="rules" ref="recipeFormRef" label-width="120px">
      <!-- Basic Information -->
      <el-card class="form-section">
        <template #header>
          <h2>Basic Information</h2>
        </template>

        <el-form-item label="Recipe Title" prop="title">
          <el-input v-model="recipeForm.title" placeholder="Enter recipe title" />
        </el-form-item>

        <el-form-item label="Description" prop="description">
          <el-input
            v-model="recipeForm.description"
            type="textarea"
            :rows="3"
            placeholder="Brief description of your recipe"
          />
        </el-form-item>

        <el-form-item label="Category" prop="category">
          <el-select v-model="recipeForm.category" placeholder="Select category">
            <el-option label="Main Dishes" value="main" />
            <el-option label="Quick Meals" value="quick" />
            <el-option label="Soups" value="soups" />
            <el-option label="Baking" value="baking" />
            <el-option label="Desserts" value="desserts" />
            <el-option label="Vegetarian" value="vegetarian" />
            <el-option label="Meat Dishes" value="meat" />
          </el-select>
        </el-form-item>

        <el-form-item label="Cover Image" prop="coverImage">
          <el-upload
            class="cover-uploader"
            :show-file-list="false"
            :on-change="handleCoverChange"
            :auto-upload="false"
            accept="image/*"
          >
            <img v-if="recipeForm.coverImage" :src="recipeForm.coverImage" class="cover-preview" />
            <el-icon v-else class="cover-uploader-icon"><Plus /></el-icon>
          </el-upload>
          <p class="upload-tip">Click to upload cover image (recommended: 800x600px)</p>
        </el-form-item>
      </el-card>

      <!-- Recipe Details -->
      <el-card class="form-section">
        <template #header>
          <h2>Recipe Details</h2>
        </template>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="Cooking Time" prop="cookingTime">
              <el-input-number v-model="recipeForm.cookingTime" :min="1" :max="999" />
              <span class="unit">minutes</span>
            </el-form-item>
          </el-col>
          
          <el-col :span="8">
            <el-form-item label="Servings" prop="servings">
              <el-input-number v-model="recipeForm.servings" :min="1" :max="50" />
              <span class="unit">people</span>
            </el-form-item>
          </el-col>
          
          <el-col :span="8">
            <el-form-item label="Difficulty" prop="difficulty">
              <el-select v-model="recipeForm.difficulty">
                <el-option label="Easy" value="EASY" />
                <el-option label="Medium" value="MEDIUM" />
                <el-option label="Hard" value="HARD" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>

      <!-- Ingredients -->
      <el-card class="form-section">
        <template #header>
          <div class="section-header">
            <h2>Ingredients</h2>
            <el-button type="primary" size="small" @click="addIngredient">
              <el-icon><Plus /></el-icon> Add Ingredient
            </el-button>
          </div>
        </template>

        <div class="ingredients-list">
          <div v-for="(ingredient, index) in recipeForm.ingredients" :key="index" class="ingredient-item">
            <el-input v-model="ingredient.name" placeholder="Ingredient name" style="width: 60%" />
            <el-input v-model="ingredient.amount" placeholder="Amount" style="width: 30%" />
            <el-button type="danger" circle @click="removeIngredient(index)">
              <el-icon><Delete /></el-icon>
            </el-button>
          </div>
        </div>
      </el-card>

      <!-- Cooking Steps -->
      <el-card class="form-section">
        <template #header>
          <div class="section-header">
            <h2>Cooking Steps</h2>
            <el-button type="primary" size="small" @click="addStep">
              <el-icon><Plus /></el-icon> Add Step
            </el-button>
          </div>
        </template>

        <div class="steps-list">
          <div v-for="(step, index) in recipeForm.steps" :key="index" class="step-item">
            <div class="step-number">{{ index + 1 }}</div>
            <el-input
              v-model="step.description"
              type="textarea"
              :rows="2"
              :placeholder="`Step ${index + 1} description`"
            />
            <el-button type="danger" circle @click="removeStep(index)">
              <el-icon><Delete /></el-icon>
            </el-button>
          </div>
        </div>
      </el-card>

      <!-- Tips & Equipment -->
      <el-card class="form-section">
        <template #header>
          <h2>Additional Information</h2>
        </template>

        <el-form-item label="Cooking Tips">
          <el-input
            v-model="recipeForm.tips"
            type="textarea"
            :rows="3"
            placeholder="Share your cooking tips and tricks..."
          />
        </el-form-item>

        <el-form-item label="Equipment Needed">
          <el-select
            v-model="recipeForm.equipment"
            multiple
            filterable
            allow-create
            placeholder="Select or add equipment"
            style="width: 100%"
          >
            <el-option label="Oven" value="oven" />
            <el-option label="Stove" value="stove" />
            <el-option label="Microwave" value="microwave" />
            <el-option label="Blender" value="blender" />
            <el-option label="Mixer" value="mixer" />
            <el-option label="Pressure Cooker" value="pressure-cooker" />
            <el-option label="Air Fryer" value="air-fryer" />
            <el-option label="Slow Cooker" value="slow-cooker" />
          </el-select>
        </el-form-item>
      </el-card>

      <!-- Action Buttons -->
      <div class="form-actions">
        <el-button @click="$router.back()">Cancel</el-button>
        <el-button type="info" @click="saveDraft">Save as Draft</el-button>
        <el-button type="primary" @click="submitRecipe" :loading="submitting">
          {{ isEdit ? 'Update Recipe' : 'Publish Recipe' }}
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Plus, Delete, ArrowLeft } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const recipeFormRef = ref(null)
const submitting = ref(false)
const isEdit = ref(false)

const recipeForm = reactive({
  title: '',
  description: '',
  category: '',
  coverImage: '',
  cookingTime: 30,
  servings: 2,
  difficulty: 'EASY',
  ingredients: [
    { name: '', amount: '' }
  ],
  steps: [
    { step: 1, description: '' }
  ],
  tips: '',
  equipment: []
})

const rules = {
  title: [
    { required: true, message: 'Please enter recipe title', trigger: 'blur' },
    { min: 3, max: 100, message: 'Title must be 3-100 characters', trigger: 'blur' }
  ],
  description: [
    { required: true, message: 'Please enter description', trigger: 'blur' }
  ],
  category: [
    { required: true, message: 'Please select category', trigger: 'change' }
  ],
  cookingTime: [
    { required: true, message: 'Please enter cooking time', trigger: 'blur' }
  ],
  servings: [
    { required: true, message: 'Please enter servings', trigger: 'blur' }
  ],
  difficulty: [
    { required: true, message: 'Please select difficulty', trigger: 'change' }
  ]
}

const handleCoverChange = (file) => {
  recipeForm.coverImage = URL.createObjectURL(file.raw)
}

const addIngredient = () => {
  recipeForm.ingredients.push({ name: '', amount: '' })
}

const removeIngredient = (index) => {
  if (recipeForm.ingredients.length > 1) {
    recipeForm.ingredients.splice(index, 1)
  } else {
    ElMessage.warning('At least one ingredient is required')
  }
}

const addStep = () => {
  recipeForm.steps.push({
    step: recipeForm.steps.length + 1,
    description: ''
  })
}

const removeStep = (index) => {
  if (recipeForm.steps.length > 1) {
    recipeForm.steps.splice(index, 1)
    // Renumber steps
    recipeForm.steps.forEach((step, i) => {
      step.step = i + 1
    })
  } else {
    ElMessage.warning('At least one step is required')
  }
}

const saveDraft = async () => {
  ElMessage.success('Recipe saved as draft')
  // Save to localStorage or backend
  localStorage.setItem('recipeDraft', JSON.stringify(recipeForm))
}

const submitRecipe = async () => {
  if (!recipeFormRef.value) return
  
  await recipeFormRef.value.validate(async (valid) => {
    if (valid) {
      // Validate ingredients
      const validIngredients = recipeForm.ingredients.filter(i => i.name && i.amount)
      if (validIngredients.length === 0) {
        ElMessage.error('Please add at least one ingredient')
        return
      }
      
      // Validate steps
      const validSteps = recipeForm.steps.filter(s => s.description)
      if (validSteps.length === 0) {
        ElMessage.error('Please add at least one cooking step')
        return
      }
      
      submitting.value = true
      
      try {
        // Simulate API call
        await new Promise(resolve => setTimeout(resolve, 1500))
        
        ElMessage.success(isEdit.value ? 'Recipe updated successfully!' : 'Recipe published successfully!')
        localStorage.removeItem('recipeDraft')
        router.push('/recipes')
      } catch (error) {
        ElMessage.error('Failed to save recipe')
      } finally {
        submitting.value = false
      }
    }
  })
}

onMounted(() => {
  // Check if editing
  if (route.params.id) {
    isEdit.value = true
    // Load recipe data
  }
  
  // Load draft if exists
  const draft = localStorage.getItem('recipeDraft')
  if (draft && !isEdit.value) {
    Object.assign(recipeForm, JSON.parse(draft))
  }
})
</script>

<style scoped>
.create-recipe-page {
  max-width: 900px;
  margin: 0 auto;
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

.form-section {
  margin-bottom: 20px;
}

.form-section h2 {
  margin: 0;
  font-size: 18px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.cover-uploader {
  display: inline-block;
}

.cover-preview {
  width: 300px;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
  cursor: pointer;
}

.cover-uploader-icon {
  font-size: 48px;
  color: #8c939d;
  width: 300px;
  height: 200px;
  line-height: 200px;
  text-align: center;
  border: 2px dashed #d9d9d9;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.cover-uploader-icon:hover {
  border-color: #409eff;
  color: #409eff;
}

.upload-tip {
  margin-top: 10px;
  color: #666;
  font-size: 14px;
}

.unit {
  margin-left: 10px;
  color: #666;
}

.ingredients-list,
.steps-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.ingredient-item {
  display: flex;
  gap: 10px;
  align-items: center;
}

.step-item {
  display: flex;
  gap: 15px;
  align-items: flex-start;
}

.step-number {
  flex-shrink: 0;
  width: 32px;
  height: 32px;
  background: #409eff;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  margin-top: 5px;
}

.step-item .el-textarea {
  flex: 1;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
  padding: 20px;
  background: #f5f5f5;
  border-radius: 8px;
}
</style>
