<template>
  <div class="notes-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Notes Management</span>
          <el-button type="danger" plain>Batch Delete</el-button>
        </div>
      </template>

      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="Search notes..."
          style="width: 300px"
          clearable
        >
          <template #append>
            <el-button icon="Search">Search</el-button>
          </template>
        </el-input>
      </div>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="Title" />
        <el-table-column label="Cover" width="100">
          <template #default="scope">
            <el-image :src="scope.row.coverImage" style="width: 60px; height: 60px" fit="cover" />
          </template>
        </el-table-column>
        <el-table-column prop="content" label="Description" show-overflow-tooltip />
        <el-table-column prop="tags" label="Tags" />
        <el-table-column prop="recipeId" label="Recipe ID" />
        <el-table-column prop="createdAt" label="Created" />
        <el-table-column prop="viewCount" label="Views" />
        <el-table-column prop="status" label="Status" />
        <el-table-column label="Actions" width="200">
          <template #default="scope">
            <el-button type="primary" size="small">Edit</el-button>
            <el-button type="success" size="small">Feature</el-button>
            <el-button type="danger" size="small">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="10"
          layout="prev, pager, next"
          :total="40"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const searchQuery = ref('')
const currentPage = ref(1)

const tableData = ref([
  {
    id: 1,
    title: 'Braised Pork Belly (Detailed)',
    coverImage: '/images/note1.jpg',
    content: 'Made delicious braised pork today...',
    tags: 'pork,main dish',
    recipeId: 1,
    createdAt: '2025-01-15 22:54:56',
    viewCount: 5678,
    status: 'Approved'
  },
  {
    id: 2,
    title: 'Peanut Red Bean Milk',
    coverImage: '/images/note2.jpg',
    content: 'Nutritious breakfast drink...',
    tags: 'breakfast,drink',
    recipeId: null,
    createdAt: '2025-01-14 10:30:00',
    viewCount: 3456,
    status: 'Approved'
  }
])
</script>

<style scoped>
.notes-page {
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
