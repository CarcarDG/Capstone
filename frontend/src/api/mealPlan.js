import apiClient from './axios'

export const mealPlanAPI = {
  // Get user meal plans
  getUserMealPlans(userId) {
    return apiClient.get(`/meal-plans/user/${userId}`)
  },

  // Get meal plans by date
  getMealPlansByDate(userId, date) {
    return apiClient.get(`/meal-plans/user/${userId}/date/${date}`)
  },

  // Get meal plans by date range
  getMealPlansByDateRange(userId, startDate, endDate) {
    return apiClient.get(`/meal-plans/user/${userId}/range`, {
      params: { startDate, endDate }
    })
  },

  // Get meal plan by ID
  getMealPlanById(id) {
    return apiClient.get(`/meal-plans/${id}`)
  },

  // Create meal plan
  createMealPlan(mealPlanData) {
    return apiClient.post('/meal-plans', mealPlanData)
  },

  // Update meal plan
  updateMealPlan(id, mealPlanData) {
    return apiClient.put(`/meal-plans/${id}`, mealPlanData)
  },

  // Delete meal plan
  deleteMealPlan(id) {
    return apiClient.delete(`/meal-plans/${id}`)
  }
}
