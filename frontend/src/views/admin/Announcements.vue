<template>
  <div class="announcements-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Announcements Management</span>
          <el-button type="primary" @click="handleAdd">Add New</el-button>
        </div>
      </template>

      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="Search announcements..."
          style="width: 300px"
          clearable
        >
          <template #append>
            <el-button icon="Search">Search</el-button>
          </template>
        </el-input>
        <el-button type="danger" plain>Batch Delete</el-button>
      </div>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="Title" />
        <el-table-column prop="content" label="Content" show-overflow-tooltip />
        <el-table-column prop="createdAt" label="Created" />
        <el-table-column prop="createdBy" label="Created By" />
        <el-table-column label="Actions" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">
              Edit
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row)">
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="10"
          layout="prev, pager, next"
          :total="30"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const searchQuery = ref('')
const currentPage = ref(1)

const tableData = ref([
  {
    id: 1,
    title: 'Welcome to Recipe Finder!',
    content: 'Thank you for joining our community. Explore delicious recipes and share your culinary creations!',
    createdAt: '2025-01-15',
    createdBy: 'admin'
  },
  {
    id: 2,
    title: 'New Features Available',
    content: 'We have added meal planning and collection features. Check them out!',
    createdAt: '2025-01-14',
    createdBy: 'admin'
  },
  {
    id: 3,
    title: 'Weekend Cooking Challenge',
    content: 'Join our weekend cooking challenge and win prizes!',
    createdAt: '2025-01-13',
    createdBy: 'admin'
  }
])

const handleAdd = () => {
  ElMessage.info('Add announcement functionality')
}

const handleEdit = (row) => {
  ElMessage.info(`Edit announcement: ${row.title}`)
}

const handleDelete = (row) => {
  ElMessageBox.confirm('Are you sure you want to delete this announcement?', 'Confirm', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(() => {
    ElMessage.success('Announcement deleted successfully')
  })
}
</script>

<style scoped>
.announcements-page {
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
