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
        >
          <template #append>
            <el-button icon="Search">Search</el-button>
          </template>
        </el-input>
      </div>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="Title" />
        <el-table-column label="Cover" width="100">
          <template #default="scope">
            <el-image :src="scope.row.coverImage" style="width: 60px; height: 60px" fit="cover" />
          </template>
        </el-table-column>
        <el-table-column prop="category" label="Category" />
        <el-table-column prop="author" label="User ID" />
        <el-table-column prop="createdAt" label="Created Date" />
        <el-table-column prop="status" label="Status" />
        <el-table-column label="Actions" width="250">
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
    title: 'Healthy Shrimp Mushroom Tofu Soup',
    coverImage: '/images/recipe1.jpg',
    category: 'Soups',
    author: 2,
    createdAt: '2025-01-15',
    status: 'Approved'
  },
  {
    id: 2,
    title: 'Sweet Pumpkin Pancakes',
    coverImage: '/images/recipe2.jpg',
    category: 'Desserts',
    author: 2,
    createdAt: '2025-01-14',
    status: 'Approved'
  }
])
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
