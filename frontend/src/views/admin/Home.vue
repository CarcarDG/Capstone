<template>
  <div class="admin-home">
    <div class="welcome-card">
      <h1>Welcome to Admin Dashboard!</h1>
    </div>

    <div class="stats-grid">
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-icon user-icon">
            <el-icon><User /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">1,234</div>
            <div class="stat-label">Total Users</div>
          </div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-icon recipe-icon">
            <el-icon><Document /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">567</div>
            <div class="stat-label">Total Recipes</div>
          </div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-icon note-icon">
            <el-icon><Notebook /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">890</div>
            <div class="stat-label">Total Notes</div>
          </div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-icon view-icon">
            <el-icon><View /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">12,345</div>
            <div class="stat-label">Total Views</div>
          </div>
        </div>
      </el-card>
    </div>

    <div class="charts-grid">
      <el-card class="chart-card">
        <template #header>
          <div class="card-header">
            <span>User Registration Trend</span>
          </div>
        </template>
        <div ref="barChartRef" class="chart-container"></div>
      </el-card>

      <el-card class="chart-card">
        <template #header>
          <div class="card-header">
            <span>Recipe Category Distribution</span>
          </div>
        </template>
        <div ref="pieChartRef" class="chart-container"></div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const barChartRef = ref()
const pieChartRef = ref()

onMounted(() => {
  // Charts will be implemented with a charting library like ECharts
  // For now, showing placeholder
})
</script>

<style scoped>
.admin-home {
  padding: 20px;
}

.welcome-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 40px;
  border-radius: 10px;
  margin-bottom: 30px;
  text-align: center;
}

.welcome-card h1 {
  margin: 0;
  font-size: 28px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  cursor: pointer;
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: white;
}

.user-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.recipe-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.note-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.view-icon {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.charts-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.chart-card {
  min-height: 400px;
}

.card-header {
  font-size: 16px;
  font-weight: bold;
}

.chart-container {
  height: 350px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
}
</style>
