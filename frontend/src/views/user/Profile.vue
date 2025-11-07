<template>
  <div class="profile-page">
    <el-card class="profile-header">
      <div class="profile-banner">
        <div class="avatar-section">
          <el-avatar :size="120" :src="userProfile.avatar" />
          <el-button type="primary" size="small" @click="showAvatarDialog = true">
            <el-icon><Camera /></el-icon> Change Avatar
          </el-button>
        </div>
        <div class="profile-info">
          <h1>{{ userProfile.nickname }}</h1>
          <p class="username">@{{ userProfile.username }}</p>
          <el-tag :type="userProfile.role === 'ADMIN' ? 'danger' : 'primary'">
            {{ userProfile.role }}
          </el-tag>
        </div>
        <div class="profile-actions">
          <el-button type="primary" @click="editMode = !editMode">
            <el-icon><Edit /></el-icon> {{ editMode ? 'Cancel' : 'Edit Profile' }}
          </el-button>
        </div>
      </div>
    </el-card>

    <el-row :gutter="20">
      <el-col :span="16">
        <!-- Profile Information -->
        <el-card class="info-card">
          <template #header>
            <h2>Profile Information</h2>
          </template>
          
          <el-form v-if="editMode" :model="editForm" :rules="rules" ref="editFormRef" label-width="120px">
            <el-form-item label="Nickname" prop="nickname">
              <el-input v-model="editForm.nickname" placeholder="Enter your nickname" />
            </el-form-item>
            
            <el-form-item label="Email" prop="email">
              <el-input v-model="editForm.email" type="email" placeholder="Enter your email" />
            </el-form-item>
            
            <el-form-item label="Phone" prop="phone">
              <el-input v-model="editForm.phone" placeholder="Enter your phone number" />
            </el-form-item>
            
            <el-form-item label="Bio">
              <el-input
                v-model="editForm.bio"
                type="textarea"
                :rows="4"
                placeholder="Tell us about yourself..."
              />
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="saveProfile" :loading="saving">Save Changes</el-button>
              <el-button @click="editMode = false">Cancel</el-button>
            </el-form-item>
          </el-form>

          <div v-else class="profile-details">
            <div class="detail-item">
              <span class="label">Nickname:</span>
              <span class="value">{{ userProfile.nickname }}</span>
            </div>
            <div class="detail-item">
              <span class="label">Email:</span>
              <span class="value">{{ userProfile.email || 'Not set' }}</span>
            </div>
            <div class="detail-item">
              <span class="label">Phone:</span>
              <span class="value">{{ userProfile.phone || 'Not set' }}</span>
            </div>
            <div class="detail-item">
              <span class="label">Bio:</span>
              <span class="value">{{ userProfile.bio || 'No bio yet' }}</span>
            </div>
            <div class="detail-item">
              <span class="label">Member Since:</span>
              <span class="value">{{ formatDate(userProfile.createdAt) }}</span>
            </div>
          </div>
        </el-card>

        <!-- Change Password -->
        <el-card class="password-card">
          <template #header>
            <h2>Change Password</h2>
          </template>
          
          <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="150px">
            <el-form-item label="Current Password" prop="currentPassword">
              <el-input v-model="passwordForm.currentPassword" type="password" show-password />
            </el-form-item>
            
            <el-form-item label="New Password" prop="newPassword">
              <el-input v-model="passwordForm.newPassword" type="password" show-password />
            </el-form-item>
            
            <el-form-item label="Confirm Password" prop="confirmPassword">
              <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="changePassword" :loading="changingPassword">
                Change Password
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>

      <el-col :span="8">
        <!-- Statistics -->
        <el-card class="stats-card">
          <template #header>
            <h2>My Statistics</h2>
          </template>
          
          <div class="stat-item">
            <el-icon><Document /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ userStats.recipes }}</div>
              <div class="stat-label">Recipes</div>
            </div>
          </div>
          
          <div class="stat-item">
            <el-icon><Notebook /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ userStats.notes }}</div>
              <div class="stat-label">Food Notes</div>
            </div>
          </div>
          
          <div class="stat-item">
            <el-icon><Star /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ userStats.likes }}</div>
              <div class="stat-label">Likes Received</div>
            </div>
          </div>
          
          <div class="stat-item">
            <el-icon><Collection /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ userStats.collections }}</div>
              <div class="stat-label">Collections</div>
            </div>
          </div>
        </el-card>

        <!-- Quick Actions -->
        <el-card class="actions-card">
          <template #header>
            <h2>Quick Actions</h2>
          </template>
          
          <el-button type="primary" plain block @click="$router.push('/recipes')">
            <el-icon><Document /></el-icon> My Recipes
          </el-button>
          <el-button type="success" plain block @click="$router.push('/notes')">
            <el-icon><Notebook /></el-icon> My Notes
          </el-button>
          <el-button type="warning" plain block @click="$router.push('/collections')">
            <el-icon><Collection /></el-icon> My Collections
          </el-button>
          <el-button type="info" plain block @click="$router.push('/meal-planner')">
            <el-icon><Calendar /></el-icon> Meal Planner
          </el-button>
        </el-card>
      </el-col>
    </el-row>

    <!-- Avatar Upload Dialog -->
    <el-dialog v-model="showAvatarDialog" title="Change Avatar" width="400px">
      <div class="avatar-upload">
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
          :on-change="handleAvatarChange"
          :auto-upload="false"
          accept="image/*"
        >
          <img v-if="previewAvatar" :src="previewAvatar" class="avatar-preview" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
        <p class="upload-tip">Click to upload a new avatar (JPG, PNG)</p>
      </div>
      
      <template #footer>
        <el-button @click="showAvatarDialog = false">Cancel</el-button>
        <el-button type="primary" @click="uploadAvatar" :loading="uploading">Upload</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'
import { Camera, Edit, Document, Notebook, Star, Collection, Calendar, Plus } from '@element-plus/icons-vue'

const authStore = useAuthStore()
const editFormRef = ref(null)
const passwordFormRef = ref(null)

const editMode = ref(false)
const saving = ref(false)
const changingPassword = ref(false)
const showAvatarDialog = ref(false)
const uploading = ref(false)
const previewAvatar = ref('')
const avatarFile = ref(null)

const userProfile = ref({
  username: authStore.user?.username || 'john',
  nickname: authStore.user?.nickname || 'John Doe',
  email: 'john@example.com',
  phone: '1234567891',
  avatar: authStore.user?.avatar || 'https://i.pravatar.cc/200?img=33',
  role: authStore.user?.role || 'USER',
  bio: 'Food enthusiast and home cook. Love sharing recipes!',
  createdAt: '2025-01-01T00:00:00'
})

const editForm = reactive({
  nickname: userProfile.value.nickname,
  email: userProfile.value.email,
  phone: userProfile.value.phone,
  bio: userProfile.value.bio
})

const passwordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const userStats = ref({
  recipes: 12,
  notes: 8,
  likes: 234,
  collections: 45
})

const rules = {
  nickname: [
    { required: true, message: 'Please enter nickname', trigger: 'blur' },
    { min: 2, max: 50, message: 'Nickname must be 2-50 characters', trigger: 'blur' }
  ],
  email: [
    { required: true, message: 'Please enter email', trigger: 'blur' },
    { type: 'email', message: 'Please enter valid email', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^\d{10,}$/, message: 'Please enter valid phone number', trigger: 'blur' }
  ]
}

const passwordRules = {
  currentPassword: [
    { required: true, message: 'Please enter current password', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: 'Please enter new password', trigger: 'blur' },
    { min: 6, message: 'Password must be at least 6 characters', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: 'Please confirm password', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error('Passwords do not match'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const saveProfile = async () => {
  if (!editFormRef.value) return
  
  await editFormRef.value.validate(async (valid) => {
    if (valid) {
      saving.value = true
      
      try {
        // Simulate API call
        await new Promise(resolve => setTimeout(resolve, 1000))
        
        userProfile.value.nickname = editForm.nickname
        userProfile.value.email = editForm.email
        userProfile.value.phone = editForm.phone
        userProfile.value.bio = editForm.bio
        
        // Update auth store
        authStore.user.nickname = editForm.nickname
        localStorage.setItem('user', JSON.stringify(authStore.user))
        
        ElMessage.success('Profile updated successfully!')
        editMode.value = false
      } catch (error) {
        ElMessage.error('Failed to update profile')
      } finally {
        saving.value = false
      }
    }
  })
}

const changePassword = async () => {
  if (!passwordFormRef.value) return
  
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      changingPassword.value = true
      
      try {
        // Simulate API call
        await new Promise(resolve => setTimeout(resolve, 1000))
        
        ElMessage.success('Password changed successfully!')
        passwordForm.currentPassword = ''
        passwordForm.newPassword = ''
        passwordForm.confirmPassword = ''
      } catch (error) {
        ElMessage.error('Failed to change password')
      } finally {
        changingPassword.value = false
      }
    }
  })
}

const handleAvatarChange = (file) => {
  avatarFile.value = file.raw
  previewAvatar.value = URL.createObjectURL(file.raw)
}

const uploadAvatar = async () => {
  if (!avatarFile.value) {
    ElMessage.warning('Please select an image')
    return
  }
  
  uploading.value = true
  
  try {
    // Simulate upload
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    userProfile.value.avatar = previewAvatar.value
    authStore.user.avatar = previewAvatar.value
    localStorage.setItem('user', JSON.stringify(authStore.user))
    
    ElMessage.success('Avatar updated successfully!')
    showAvatarDialog.value = false
  } catch (error) {
    ElMessage.error('Failed to upload avatar')
  } finally {
    uploading.value = false
  }
}

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })
}

onMounted(() => {
  // Load user profile data
  // In real app, fetch from API
})
</script>

<style scoped>
.profile-page {
  max-width: 1200px;
  margin: 0 auto;
}

.profile-header {
  margin-bottom: 20px;
}

.profile-banner {
  display: flex;
  align-items: center;
  gap: 30px;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.profile-info {
  flex: 1;
}

.profile-info h1 {
  margin: 0 0 5px 0;
  font-size: 28px;
}

.username {
  color: #666;
  margin: 0 0 10px 0;
}

.info-card,
.password-card,
.stats-card,
.actions-card {
  margin-bottom: 20px;
}

.info-card h2,
.password-card h2,
.stats-card h2,
.actions-card h2 {
  margin: 0;
  font-size: 18px;
}

.profile-details {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.detail-item {
  display: flex;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}

.detail-item:last-child {
  border-bottom: none;
}

.detail-item .label {
  font-weight: 600;
  width: 150px;
  color: #666;
}

.detail-item .value {
  flex: 1;
  color: #333;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.stat-item:last-child {
  border-bottom: none;
}

.stat-item .el-icon {
  font-size: 32px;
  color: #409eff;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.actions-card .el-button {
  margin-bottom: 10px;
}

.actions-card .el-button:last-child {
  margin-bottom: 0;
}

.avatar-upload {
  text-align: center;
}

.avatar-uploader {
  display: inline-block;
}

.avatar-preview {
  width: 200px;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
  cursor: pointer;
}

.avatar-uploader-icon {
  font-size: 48px;
  color: #8c939d;
  width: 200px;
  height: 200px;
  line-height: 200px;
  text-align: center;
  border: 2px dashed #d9d9d9;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.avatar-uploader-icon:hover {
  border-color: #409eff;
  color: #409eff;
}

.upload-tip {
  margin-top: 10px;
  color: #666;
  font-size: 14px;
}
</style>
