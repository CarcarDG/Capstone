<template>
  <div class="notes-page">
    <div class="page-header">
      <h1>Food Notes</h1>
      <el-button type="primary" @click="createNote">
        <el-icon><Plus /></el-icon> Create Note
      </el-button>
    </div>

    <div class="search-bar">
      <el-input
        v-model="searchQuery"
        placeholder="Search notes..."
        prefix-icon="Search"
        style="width: 400px"
        clearable
      />
    </div>

    <div class="notes-grid">
      <el-card v-for="note in filteredNotes" :key="note.id" class="note-card" shadow="hover" @click="viewNote(note.id)">
        <img :src="note.images[0]" :alt="note.title" class="note-image" />
        <div class="note-content">
          <h3>{{ note.title }}</h3>
          <p class="note-text">{{ note.content }}</p>
          <div class="note-tags">
            <el-tag v-for="tag in note.tags.split(',')" :key="tag" size="small">{{ tag }}</el-tag>
          </div>
          <div class="note-stats">
            <span><el-icon><View /></el-icon> {{ note.viewCount }}</span>
            <span><el-icon><Star /></el-icon> {{ note.likeCount }}</span>
            <span><el-icon><Collection /></el-icon> {{ note.collectCount }}</span>
            <span><el-icon><ChatDotRound /></el-icon> {{ note.commentCount }}</span>
          </div>
        </div>
      </el-card>
    </div>

    <el-empty v-if="filteredNotes.length === 0" description="No notes found" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Plus, View, Star, Collection, ChatDotRound } from '@element-plus/icons-vue'
import { noteAPI } from '@/api/note'
import { ElMessage } from 'element-plus'

const router = useRouter()
const searchQuery = ref('')
const notes = ref([])
const loading = ref(false)

const fetchNotes = async () => {
  loading.value = true
  try {
    const data = await noteAPI.getAllNotes()
    notes.value = Array.isArray(data) ? data : []
  } catch (error) {
    console.error('Failed to fetch notes:', error)
    ElMessage.error('Failed to load notes')
    notes.value = []
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchNotes()
})

const filteredNotes = computed(() => {
  if (!searchQuery.value) return notes.value
  
  const query = searchQuery.value.toLowerCase()
  return notes.value.filter(note => 
    note.title.toLowerCase().includes(query) || 
    note.content.toLowerCase().includes(query) ||
    (note.tags && note.tags.toLowerCase().includes(query))
  )
})

const createNote = () => {
  ElMessage.info('Create note feature coming soon!')
  // router.push('/notes/create')
}

const viewNote = (id) => {
  router.push(`/notes/${id}`)
}
</script>

<style scoped>
.notes-page {
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

.search-bar {
  margin-bottom: 30px;
}

.notes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.note-card {
  cursor: pointer;
  transition: transform 0.2s;
}

.note-card:hover {
  transform: translateY(-5px);
}

.note-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 4px;
}

.note-content {
  padding: 15px 0;
}

.note-content h3 {
  margin: 0 0 10px 0;
  font-size: 18px;
}

.note-text {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.note-tags {
  display: flex;
  gap: 8px;
  margin-bottom: 15px;
  flex-wrap: wrap;
}

.note-stats {
  display: flex;
  gap: 20px;
  font-size: 14px;
  color: #999;
}

.note-stats span {
  display: flex;
  align-items: center;
  gap: 5px;
}
</style>
