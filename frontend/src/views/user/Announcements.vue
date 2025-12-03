<template>
  <div class="announcements-page" v-loading="loading">
    <div class="page-header">
      <h1>Announcements</h1>
    </div>

    <div class="announcements-list" v-if="announcements.length > 0">
      <el-card v-for="announcement in announcements" :key="announcement.id" class="announcement-card" shadow="hover">
        <div class="announcement-header">
          <div class="announcement-title">
            <el-tag :type="getTypeColor(announcement.type)" size="small">{{ announcement.type }}</el-tag>
            <h3>{{ announcement.title }}</h3>
          </div>
          <el-tag v-if="announcement.priority === 1" type="danger" size="small">Important</el-tag>
        </div>
        <div class="announcement-content">
          <p>{{ announcement.content }}</p>
        </div>
        <div class="announcement-footer">
          <span class="date">{{ formatDate(announcement.createdAt) }}</span>
        </div>
      </el-card>
    </div>

    <el-empty v-if="announcements.length === 0" description="No announcements" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { announcementAPI } from '@/api/announcement'
import { ElMessage } from 'element-plus'

const announcements = ref([])
const loading = ref(false)

const fetchAnnouncements = async () => {
  loading.value = true
  try {
    const data = await announcementAPI.getAllAnnouncements()
    console.log('Announcements API response:', data)
    announcements.value = Array.isArray(data) ? data : []
  } catch (error) {
    console.error('Failed to fetch announcements:', error)
    ElMessage.error('Failed to load announcements')
    // Fallback to mock data
    announcements.value = [
      {
        id: 1,
        title: 'Welcome to Recipe Platform!',
        content: 'All features are now available. Start exploring!',
        type: 'SYSTEM',
        priority: 1,
        createdAt: '2025-01-15T10:00:00'
      }
    ]
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchAnnouncements()
})

const getTypeColor = (type) => {
  const colors = {
    'SYSTEM': 'primary',
    'EVENT': 'success',
    'MAINTENANCE': 'warning',
    'UPDATE': 'info'
  }
  return colors[type] || 'info'
}

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', { 
    year: 'numeric', 
    month: 'long', 
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}
</script>

<style scoped>
.announcements-page {
  max-width: 900px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 30px;
}

.page-header h1 {
  margin: 0;
  font-size: 28px;
}

.announcements-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.announcement-card {
  transition: transform 0.2s;
}

.announcement-card:hover {
  transform: translateX(5px);
}

.announcement-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
}

.announcement-title {
  display: flex;
  align-items: center;
  gap: 10px;
  flex: 1;
}

.announcement-title h3 {
  margin: 0;
  font-size: 18px;
}

.announcement-content {
  margin-bottom: 15px;
}

.announcement-content p {
  margin: 0;
  color: #666;
  line-height: 1.6;
}

.announcement-footer {
  display: flex;
  justify-content: flex-end;
  padding-top: 10px;
  border-top: 1px solid #eee;
}

.date {
  font-size: 14px;
  color: #999;
}
</style>
