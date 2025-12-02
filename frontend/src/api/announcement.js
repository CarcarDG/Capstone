import apiClient from './axios'

export const announcementAPI = {
  // Get all announcements
  getAllAnnouncements() {
    return apiClient.get('/announcements')
  },

  // Get announcement by ID
  getAnnouncementById(id) {
    return apiClient.get(`/announcements/${id}`)
  },

  // Get announcements by user
  getAnnouncementsByUser(userId) {
    return apiClient.get(`/announcements/user/${userId}`)
  },

  // Create announcement
  createAnnouncement(announcementData) {
    return apiClient.post('/announcements', announcementData)
  },

  // Update announcement
  updateAnnouncement(id, announcementData) {
    return apiClient.put(`/announcements/${id}`, announcementData)
  },

  // Delete announcement
  deleteAnnouncement(id) {
    return apiClient.delete(`/announcements/${id}`)
  }
}
