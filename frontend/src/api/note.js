import apiClient from './axios'

export const noteAPI = {
  // Get all notes
  getAllNotes() {
    return apiClient.get('/notes')
  },

  // Get note by ID
  getNoteById(id) {
    return apiClient.get(`/notes/${id}`)
  },

  // Get notes by user
  getNotesByUser(userId) {
    return apiClient.get(`/notes/user/${userId}`)
  },

  // Create note
  createNote(noteData) {
    return apiClient.post('/notes', noteData)
  },

  // Update note
  updateNote(id, noteData) {
    return apiClient.put(`/notes/${id}`, noteData)
  },

  // Delete note
  deleteNote(id) {
    return apiClient.delete(`/notes/${id}`)
  }
}
