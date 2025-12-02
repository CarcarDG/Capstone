import apiClient from './axios'

export const recipeAPI = {
  // Get all recipes
  getAllRecipes() {
    return apiClient.get('/recipes')
  },

  // Get recipe by ID
  getRecipeById(id) {
    return apiClient.get(`/recipes/${id}`)
  },

  // Get recipes by category
  getRecipesByCategory(categoryId) {
    return apiClient.get(`/recipes/category/${categoryId}`)
  },

  // Get recipes by user
  getRecipesByUser(userId) {
    return apiClient.get(`/recipes/user/${userId}`)
  },

  // Get featured recipes
  getFeaturedRecipes() {
    return apiClient.get('/recipes/featured')
  },

  // Search recipes
  searchRecipes(keyword) {
    return apiClient.get('/recipes/search', { params: { keyword } })
  },

  // Create recipe
  createRecipe(recipeData) {
    return apiClient.post('/recipes', recipeData)
  },

  // Update recipe
  updateRecipe(id, recipeData) {
    return apiClient.put(`/recipes/${id}`, recipeData)
  },

  // Delete recipe
  deleteRecipe(id) {
    return apiClient.delete(`/recipes/${id}`)
  }
}
