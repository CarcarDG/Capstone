<template>
  <div class="users-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>User Management</span>
          <el-button type="primary" @click="handleAdd">Add New</el-button>
        </div>
      </template>

      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="Search users..."
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
        <el-table-column prop="username" label="Username" />
        <el-table-column prop="nickname" label="Nickname" />
        <el-table-column prop="role" label="Role" />
        <el-table-column prop="phone" label="Phone" />
        <el-table-column prop="email" label="Email" />
        <el-table-column label="Avatar" width="100">
          <template #default="scope">
            <el-avatar :src="scope.row.avatar" />
          </template>
        </el-table-column>
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
          :total="50"
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
    id: 2,
    username: 'john',
    nickname: 'John Doe',
    role: 'USER',
    phone: '1234567891',
    email: 'john@example.com',
    avatar: '/avatars/user1.jpg'
  },
  {
    id: 3,
    username: 'jane',
    nickname: 'Jane Smith',
    role: 'USER',
    phone: '1234567892',
    email: 'jane@example.com',
    avatar: '/avatars/user2.jpg'
  }
])

const handleAdd = () => {
  ElMessage.info('Add user functionality')
}

const handleEdit = (row) => {
  ElMessage.info(`Edit user: ${row.username}`)
}

const handleDelete = (row) => {
  ElMessageBox.confirm('Are you sure you want to delete this user?', 'Confirm', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(() => {
    ElMessage.success('User deleted successfully')
  })
}
</script>

<style scoped>
.users-page {
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
