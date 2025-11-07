<template>
  <div class="meal-planner-page">
    <div class="page-header">
      <h1>Meal Planner</h1>
      <el-button type="primary" @click="addMeal">
        <el-icon><Plus /></el-icon> Add Meal
      </el-button>
    </div>

    <div class="calendar-controls">
      <el-button @click="previousWeek">
        <el-icon><ArrowLeft /></el-icon>
      </el-button>
      <span class="week-label">{{ weekLabel }}</span>
      <el-button @click="nextWeek">
        <el-icon><ArrowRight /></el-icon>
      </el-button>
      <el-button @click="goToToday">Today</el-button>
    </div>

    <div class="meal-calendar">
      <div v-for="day in weekDays" :key="day.date" class="day-column">
        <div class="day-header" :class="{ today: isToday(day.date) }">
          <div class="day-name">{{ day.name }}</div>
          <div class="day-date">{{ formatDate(day.date) }}</div>
        </div>
        
        <div class="meals-container">
          <div v-for="mealType in mealTypes" :key="mealType" class="meal-slot">
            <div class="meal-type-label">{{ mealType }}</div>
            <div v-if="getMeal(day.date, mealType)" class="meal-card">
              <img :src="getMeal(day.date, mealType).recipe.coverImage" 
                   :alt="getMeal(day.date, mealType).recipe.title" 
                   class="meal-image" />
              <div class="meal-info">
                <h4>{{ getMeal(day.date, mealType).recipe.title }}</h4>
                <p v-if="getMeal(day.date, mealType).notes" class="meal-notes">
                  {{ getMeal(day.date, mealType).notes }}
                </p>
              </div>
              <el-button 
                type="danger" 
                size="small" 
                circle 
                class="remove-btn"
                @click="removeMeal(getMeal(day.date, mealType).id)">
                <el-icon><Close /></el-icon>
              </el-button>
            </div>
            <div v-else class="empty-meal" @click="addMealToSlot(day.date, mealType)">
              <el-icon><Plus /></el-icon>
              <span>Add {{ mealType }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Add Meal Dialog -->
    <el-dialog v-model="showAddDialog" title="Add Meal to Planner" width="500px">
      <el-form label-position="top">
        <el-form-item label="Date">
          <el-date-picker
            v-model="selectedDate"
            type="date"
            placeholder="Select date"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        
        <el-form-item label="Meal Type">
          <el-select v-model="selectedMealType" placeholder="Select meal type" style="width: 100%">
            <el-option label="Breakfast" value="Breakfast" />
            <el-option label="Lunch" value="Lunch" />
            <el-option label="Dinner" value="Dinner" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="Recipe">
          <el-select v-model="selectedRecipeId" placeholder="Select recipe" style="width: 100%">
            <el-option
              v-for="recipe in availableRecipes"
              :key="recipe.id"
              :label="recipe.title"
              :value="recipe.id"
            >
              <div style="display: flex; align-items: center; gap: 10px;">
                <img :src="recipe.image" style="width: 40px; height: 30px; object-fit: cover; border-radius: 4px;" />
                <span>{{ recipe.title }}</span>
              </div>
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="Notes (Optional)">
          <el-input
            v-model="mealNotes"
            type="textarea"
            :rows="3"
            placeholder="Add any notes about this meal..."
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="showAddDialog = false">Cancel</el-button>
        <el-button type="primary" @click="confirmAddMeal">Add Meal</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, ArrowLeft, ArrowRight, Close } from '@element-plus/icons-vue'

const currentWeekStart = ref(getMonday(new Date()))
const mealTypes = ['Breakfast', 'Lunch', 'Dinner']

const getTodayDate = () => {
  const today = new Date()
  return today.toISOString().split('T')[0]
}

const getTomorrowDate = () => {
  const tomorrow = new Date()
  tomorrow.setDate(tomorrow.getDate() + 1)
  return tomorrow.toISOString().split('T')[0]
}

const mealPlans = ref([
  {
    id: 1,
    date: getTodayDate(),
    mealType: 'Lunch',
    recipe: {
      id: 1,
      title: 'Healthy Shrimp Mushroom Tofu Soup',
      coverImage: 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=800&h=600&fit=crop'
    },
    notes: 'Having this for lunch today'
  },
  {
    id: 2,
    date: getTodayDate(),
    mealType: 'Dinner',
    recipe: {
      id: 2,
      title: 'Low-Calorie Sweet Pumpkin Pancakes',
      coverImage: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800&h=600&fit=crop'
    },
    notes: 'Dinner preparation'
  },
  {
    id: 3,
    date: getTomorrowDate(),
    mealType: 'Breakfast',
    recipe: {
      id: 3,
      title: 'Potato Shred Egg Pancake',
      coverImage: 'https://images.unsplash.com/photo-1525351484163-7529414344d8?w=800&h=600&fit=crop'
    },
    notes: 'Quick breakfast'
  }
])

const weekDays = computed(() => {
  const days = []
  const dayNames = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
  
  for (let i = 0; i < 7; i++) {
    const date = new Date(currentWeekStart.value)
    date.setHours(0, 0, 0, 0)
    date.setDate(date.getDate() + i)
    // Format date as YYYY-MM-DD in local timezone
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    days.push({
      name: dayNames[i],
      date: `${year}-${month}-${day}`
    })
  }
  return days
})

const weekLabel = computed(() => {
  const start = new Date(currentWeekStart.value)
  const end = new Date(start)
  end.setDate(end.getDate() + 6)
  
  return `${formatDateShort(start)} - ${formatDateShort(end)}`
})

function getMonday(date) {
  const d = new Date(date)
  const day = d.getDay()
  const diff = d.getDate() - day + (day === 0 ? -6 : 1)
  const monday = new Date(d.setDate(diff))
  // Reset time to midnight to avoid timezone issues
  monday.setHours(0, 0, 0, 0)
  return monday
}

function isToday(dateString) {
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  const todayString = today.toISOString().split('T')[0]
  return dateString === todayString
}

function formatDate(dateString) {
  // Add 'T00:00:00' to ensure correct date parsing without timezone offset
  const date = new Date(dateString + 'T00:00:00')
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })
}

function formatDateShort(date) {
  const d = new Date(date)
  d.setHours(0, 0, 0, 0)
  return d.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })
}

function getMeal(date, mealType) {
  return mealPlans.value.find(m => m.date === date && m.mealType === mealType)
}

function previousWeek() {
  const newDate = new Date(currentWeekStart.value)
  newDate.setDate(newDate.getDate() - 7)
  currentWeekStart.value = newDate
}

function nextWeek() {
  const newDate = new Date(currentWeekStart.value)
  newDate.setDate(newDate.getDate() + 7)
  currentWeekStart.value = newDate
}

function goToToday() {
  currentWeekStart.value = getMonday(new Date())
}

const showAddDialog = ref(false)
const selectedDate = ref('')
const selectedMealType = ref('')
const selectedRecipeId = ref(null)
const mealNotes = ref('')

const availableRecipes = [
  { id: 1, title: 'Healthy Shrimp Mushroom Tofu Soup', image: 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=300&h=200&fit=crop' },
  { id: 2, title: 'Low-Calorie Sweet Pumpkin Pancakes', image: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=300&h=200&fit=crop' },
  { id: 3, title: 'Potato Shred Egg Pancake', image: 'https://images.unsplash.com/photo-1525351484163-7529414344d8?w=300&h=200&fit=crop' },
  { id: 4, title: 'Comfort Food That Heals the Soul', image: 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=300&h=200&fit=crop' }
]

function addMeal() {
  selectedDate.value = new Date().toISOString().split('T')[0]
  selectedMealType.value = 'Lunch'
  selectedRecipeId.value = null
  mealNotes.value = ''
  showAddDialog.value = true
}

function addMealToSlot(date, mealType) {
  selectedDate.value = date
  selectedMealType.value = mealType
  selectedRecipeId.value = null
  mealNotes.value = ''
  showAddDialog.value = true
}

function confirmAddMeal() {
  if (!selectedRecipeId.value) {
    ElMessage.warning('Please select a recipe')
    return
  }
  
  const recipe = availableRecipes.find(r => r.id === selectedRecipeId.value)
  const newMeal = {
    id: mealPlans.value.length + 1,
    date: selectedDate.value,
    mealType: selectedMealType.value,
    recipe: {
      id: recipe.id,
      title: recipe.title,
      coverImage: recipe.image
    },
    notes: mealNotes.value
  }
  
  mealPlans.value.push(newMeal)
  showAddDialog.value = false
  ElMessage.success('Meal added to planner!')
}

function removeMeal(id) {
  ElMessageBox.confirm('Remove this meal from planner?', 'Confirm', {
    confirmButtonText: 'Remove',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(() => {
    const index = mealPlans.value.findIndex(m => m.id === id)
    if (index > -1) {
      mealPlans.value.splice(index, 1)
      ElMessage.success('Meal removed')
    }
  })
}
</script>

<style scoped>
.meal-planner-page {
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

.calendar-controls {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 30px;
  justify-content: center;
}

.week-label {
  font-size: 18px;
  font-weight: 500;
  min-width: 200px;
  text-align: center;
}

.meal-calendar {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 10px;
  overflow-x: auto;
}

.day-column {
  min-width: 180px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
}

.day-header {
  background: #f5f5f5;
  padding: 12px;
  text-align: center;
  border-bottom: 1px solid #e0e0e0;
}

.day-header.today {
  background: #409eff;
  color: white;
}

.day-name {
  font-weight: 600;
  font-size: 14px;
}

.day-date {
  font-size: 12px;
  margin-top: 4px;
}

.meals-container {
  padding: 10px;
}

.meal-slot {
  margin-bottom: 15px;
}

.meal-type-label {
  font-size: 12px;
  color: #666;
  margin-bottom: 8px;
  font-weight: 500;
}

.meal-card {
  position: relative;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  overflow: hidden;
  background: white;
  transition: transform 0.2s;
}

.meal-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.meal-image {
  width: 100%;
  height: 100px;
  object-fit: cover;
}

.meal-info {
  padding: 10px;
}

.meal-info h4 {
  margin: 0 0 5px 0;
  font-size: 14px;
  line-height: 1.3;
}

.meal-notes {
  margin: 0;
  font-size: 12px;
  color: #999;
}

.remove-btn {
  position: absolute;
  top: 5px;
  right: 5px;
}

.empty-meal {
  border: 2px dashed #ddd;
  border-radius: 6px;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  color: #999;
  transition: all 0.2s;
}

.empty-meal:hover {
  border-color: #409eff;
  color: #409eff;
  background: #f0f7ff;
}

.empty-meal span {
  display: block;
  margin-top: 5px;
  font-size: 12px;
}
</style>
