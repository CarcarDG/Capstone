import apiClient from './axios'

export const collectionAPI = {
  // Get user collections
  getUserCollections(userId) {
    return apiClient.get(`/collections/user/${userId}`)
  },

  // Add to collection
  addToCollection(userId, recipeId) {
    return apiClient.post('/collections', { userId, recipeId })
  },

  // Remove from collection
  removeFromCollection(userId, recipeId) {
    return apiClient.delete(`/collections/user/${userId}/recipe/${recipeId}`)
  },

  // Check if recipe is collected
  checkCollection(userId, recipeId) {
    return apiClient.get(`/collections/user/${userId}/recipe/${recipeId}/check`)
  }
}
