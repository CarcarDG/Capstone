<template>
  <div class="signup-container">
    <el-card class="signup-card">
      <div class="logo">
        <h1>🍎 Recipe Platform</h1>
        <p>Create your account</p>
      </div>

      <el-form :model="signupForm" :rules="rules" ref="signupFormRef" label-position="top">
        <el-form-item label="Username" prop="username">
          <el-input v-model="signupForm.username" placeholder="Enter username" />
        </el-form-item>

        <el-form-item label="Email" prop="email">
          <el-input v-model="signupForm.email" type="email" placeholder="Enter email" />
        </el-form-item>

        <el-form-item label="Phone" prop="phone">
          <el-input v-model="signupForm.phone" placeholder="Enter phone number" />
        </el-form-item>

        <el-form-item label="Password" prop="password">
          <el-input v-model="signupForm.password" type="password" placeholder="Enter password" show-password />
        </el-form-item>

        <el-form-item label="Confirm Password" prop="confirmPassword">
          <el-input v-model="signupForm.confirmPassword" type="password" placeholder="Confirm password" show-password />
        </el-form-item>

        <el-form-item label="Nickname" prop="nickname">
          <el-input v-model="signupForm.nickname" placeholder="Enter nickname (optional)" />
        </el-form-item>

        <el-button type="primary" @click="handleSignup" :loading="loading" class="signup-btn">
          Sign Up
        </el-button>
      </el-form>

      <div class="login-link">
        Already have an account? 
        <router-link to="/login">Login here</router-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuth } from '@/composables/useAuth'

const router = useRouter()
const { register } = useAuth()
const signupFormRef = ref(null)
const loading = ref(false)

const signupForm = ref({
  username: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: '',
  nickname: ''
})

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please enter password'))
  } else if (value.length < 6) {
    callback(new Error('Password must be at least 6 characters'))
  } else {
    callback()
  }
}

const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please confirm password'))
  } else if (value !== signupForm.value.password) {
    callback(new Error('Passwords do not match'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: 'Please enter username', trigger: 'blur' },
    { min: 3, max: 20, message: 'Username must be 3-20 characters', trigger: 'blur' }
  ],
  email: [
    { required: true, message: 'Please enter email', trigger: 'blur' },
    { type: 'email', message: 'Please enter valid email', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: 'Please enter phone number', trigger: 'blur' }
  ],
  password: [
    { required: true, validator: validatePassword, trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleSignup = async () => {
  if (!signupFormRef.value) return
  
  await signupFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      
      try {
        await register({
          username: signupForm.value.username,
          email: signupForm.value.email,
          phone: signupForm.value.phone,
          password: signupForm.value.password,
          nickname: signupForm.value.nickname || signupForm.value.username
        })
        
        ElMessage.success('Account created successfully! Please login.')
        setTimeout(() => {
          router.push('/login')
        }, 1000)
      } catch (error) {
        const errorMessage = error.message || error.response?.data?.message || 'Registration failed. Please try again.'
        ElMessage.error(errorMessage)
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.signup-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.signup-card {
  width: 100%;
  max-width: 450px;
  padding: 20px;
}

.logo {
  text-align: center;
  margin-bottom: 30px;
}

.logo h1 {
  margin: 0 0 10px 0;
  font-size: 32px;
  color: #333;
}

.logo p {
  margin: 0;
  color: #666;
  font-size: 16px;
}

.signup-btn {
  width: 100%;
  margin-top: 10px;
}

.login-link {
  text-align: center;
  margin-top: 20px;
  color: #666;
}

.login-link a {
  color: #409eff;
  text-decoration: none;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>
