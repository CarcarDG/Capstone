<template>
  <div class="note-detail-page">
    <div class="back-button">
      <el-button @click="goBack" icon="ArrowLeft">Back to Notes</el-button>
    </div>

    <el-card class="note-card">
      <div class="note-header">
        <h1>{{ note.title }}</h1>
        <div class="author-info">
          <el-avatar :src="note.authorAvatar" />
          <div>
            <div class="author-name">{{ note.author }}</div>
            <div class="publish-date">{{ formatDate(note.createdAt) }}</div>
          </div>
        </div>
      </div>

      <div class="note-images">
        <img v-for="(image, index) in note.images" :key="index" :src="image" :alt="note.title" />
      </div>

      <div class="note-content">
        <p>{{ note.content }}</p>
      </div>

      <div class="note-tags">
        <el-tag v-for="tag in note.tags.split(',')" :key="tag" size="large">{{ tag }}</el-tag>
      </div>

      <div class="note-stats">
        <span><el-icon><View /></el-icon> {{ note.viewCount }} views</span>
        <span><el-icon><Star /></el-icon> {{ note.likeCount }} likes</span>
        <span><el-icon><Collection /></el-icon> {{ note.collectCount }} saved</span>
        <span><el-icon><ChatDotRound /></el-icon> {{ note.commentCount }} comments</span>
      </div>

      <div class="action-buttons">
        <el-button type="primary" @click="likeNote">
          <el-icon><Star /></el-icon> Like
        </el-button>
        <el-button @click="collectNote">
          <el-icon><Collection /></el-icon> Save
        </el-button>
        <el-button @click="shareNote">
          <el-icon><Share /></el-icon> Share
        </el-button>
      </div>
    </el-card>

    <el-card class="comments-card">
      <template #header>
        <h2>Comments ({{ note.commentCount }})</h2>
      </template>
      
      <div class="comment-input">
        <el-input
          v-model="newComment"
          type="textarea"
          :rows="3"
          placeholder="Write a comment..."
        />
        <el-button type="primary" @click="addComment">Post Comment</el-button>
      </div>

      <div class="comments-list">
        <div v-for="comment in comments" :key="comment.id" class="comment-item">
          <el-avatar :src="comment.userAvatar" size="small" />
          <div class="comment-content">
            <div class="comment-header">
              <span class="comment-author">{{ comment.userName }}</span>
              <span class="comment-date">{{ comment.date }}</span>
            </div>
            <p>{{ comment.content }}</p>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { View, Star, Collection, ChatDotRound, Share, ArrowLeft } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const newComment = ref('')

const notes = {
  1: {
    id: 1,
    title: 'Braised Pork Belly (Super Detailed)',
    content: `Made super delicious braised pork today, fatty but not greasy, melts in your mouth! 
    
Sharing the detailed recipe with everyone. First select pork belly, cut into 2cm square pieces. 
Blanch in cold water to remove impurities. Heat oil in wok, add rock sugar and caramelize until amber colored. 
Add pork belly and stir-fry until evenly coated with sugar color. 

Add ginger, scallions, star anise, cinnamon, and bay leaves. Pour in cooking wine and soy sauce. 
Add enough hot water to cover the meat. Bring to boil then simmer on low heat for 1.5 hours. 

The key is patience - low and slow cooking makes the meat incredibly tender. 
Add salt to taste in the last 15 minutes. The final dish should have a beautiful red color and rich aroma.`,
    images: ['https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=800&h=600&fit=crop'],
    tags: 'braised pork,home cooking',
    author: 'John Doe',
    authorAvatar: 'https://i.pravatar.cc/200?img=33',
    createdAt: '2025-01-15T10:30:00',
    viewCount: 5678,
    likeCount: 456,
    collectCount: 234,
    commentCount: 78
  },
  2: {
    id: 2,
    title: 'Peanut Red Bean Milk Smoothie',
    content: `Nutritious breakfast drink, rich and delicious! Perfect way to start your day.

Soak red beans and peanuts overnight. Cook them until soft in a pressure cooker. 
Blend with milk and a touch of honey for sweetness. The combination is incredibly creamy and satisfying.

This smoothie is packed with protein and fiber, keeping you full until lunch. 
You can make a big batch and store in the fridge for up to 3 days.`,
    images: ['https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=800&h=600&fit=crop'],
    tags: 'breakfast,drinks',
    author: 'John Doe',
    authorAvatar: 'https://i.pravatar.cc/200?img=33',
    createdAt: '2025-01-14T08:00:00',
    viewCount: 3456,
    likeCount: 234,
    collectCount: 123,
    commentCount: 45
  },
  3: {
    id: 3,
    title: 'Juicy Dumplings - Irresistibly Good!',
    content: `Super delicious snack recipe that everyone will love!

The secret to juicy dumplings is in the filling. Mix ground pork with finely chopped cabbage, 
ginger, scallions, and a splash of sesame oil. The key is to add a bit of gelatin or aspic 
to the filling - this melts during cooking and creates that amazing soup inside.

Wrap carefully, making sure to seal the edges well. Steam for 12-15 minutes. 
Serve hot with black vinegar and ginger strips. One bite and the juice bursts in your mouth!`,
    images: ['https://images.unsplash.com/photo-1496116218417-1a781b1c416c?w=800&h=600&fit=crop'],
    tags: 'snacks,desserts',
    author: 'Jane Smith',
    authorAvatar: 'https://i.pravatar.cc/200?img=47',
    createdAt: '2025-01-13T15:20:00',
    viewCount: 6789,
    likeCount: 567,
    collectCount: 345,
    commentCount: 123
  },
  4: {
    id: 4,
    title: 'Braised Spare Ribs',
    content: `Classic home-style dish, bright red color, delicious taste!

Cut spare ribs into pieces and blanch. Marinate with soy sauce, cooking wine, and ginger. 
Heat oil and fry until golden. Add aromatics and sauce, then braise until tender.

The ribs should be fall-off-the-bone tender with a glossy, caramelized exterior. 
Perfect served over steamed rice with some blanched vegetables on the side.`,
    images: ['https://images.unsplash.com/photo-1544025162-d76694265947?w=800&h=600&fit=crop'],
    tags: 'spare ribs,home cooking',
    author: 'John Doe',
    authorAvatar: 'https://i.pravatar.cc/200?img=33',
    createdAt: '2025-01-12T18:45:00',
    viewCount: 4567,
    likeCount: 345,
    collectCount: 234,
    commentCount: 67
  }
}

const note = ref(notes[1])

const comments = ref([
  {
    id: 1,
    userName: 'Alice Chen',
    userAvatar: 'https://i.pravatar.cc/200?img=5',
    content: 'Looks amazing! Can\'t wait to try this recipe.',
    date: '2 hours ago'
  },
  {
    id: 2,
    userName: 'Bob Wilson',
    userAvatar: 'https://i.pravatar.cc/200?img=8',
    content: 'I made this yesterday and it turned out perfect! Thanks for sharing.',
    date: '1 day ago'
  },
  {
    id: 3,
    userName: 'Carol Lee',
    userAvatar: 'https://i.pravatar.cc/200?img=9',
    content: 'What temperature should I use for cooking?',
    date: '2 days ago'
  }
])

onMounted(() => {
  const noteId = parseInt(route.params.id)
  if (notes[noteId]) {
    note.value = notes[noteId]
  }
})

const goBack = () => {
  router.push('/notes')
}

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', { 
    year: 'numeric', 
    month: 'long', 
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const likeNote = () => {
  note.value.likeCount++
  ElMessage.success('Liked!')
}

const collectNote = () => {
  note.value.collectCount++
  ElMessage.success('Saved to collections!')
}

const shareNote = () => {
  ElMessage.success('Link copied to clipboard!')
}

const addComment = () => {
  if (!newComment.value.trim()) {
    ElMessage.warning('Please write a comment')
    return
  }
  
  comments.value.unshift({
    id: comments.value.length + 1,
    userName: 'You',
    userAvatar: 'https://i.pravatar.cc/200?img=33',
    content: newComment.value,
    date: 'Just now'
  })
  
  note.value.commentCount++
  newComment.value = ''
  ElMessage.success('Comment posted!')
}
</script>

<style scoped>
.note-detail-page {
  max-width: 900px;
  margin: 0 auto;
}

.back-button {
  margin-bottom: 20px;
}

.note-header {
  margin-bottom: 25px;
}

.note-header h1 {
  margin: 0 0 20px 0;
  font-size: 32px;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.author-name {
  font-weight: 600;
  font-size: 16px;
}

.publish-date {
  color: #999;
  font-size: 14px;
}

.note-images {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 15px;
  margin-bottom: 25px;
}

.note-images img {
  width: 100%;
  height: 400px;
  object-fit: cover;
  border-radius: 8px;
}

.note-content {
  margin-bottom: 25px;
  line-height: 1.8;
  font-size: 16px;
  color: #333;
  white-space: pre-line;
}

.note-tags {
  display: flex;
  gap: 10px;
  margin-bottom: 25px;
  flex-wrap: wrap;
}

.note-stats {
  display: flex;
  gap: 30px;
  margin-bottom: 25px;
  padding: 20px 0;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
  font-size: 16px;
  color: #666;
}

.note-stats span {
  display: flex;
  align-items: center;
  gap: 8px;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.comments-card {
  margin-top: 30px;
}

.comments-card h2 {
  margin: 0;
  font-size: 22px;
}

.comment-input {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 30px;
}

.comment-input .el-button {
  align-self: flex-end;
}

.comments-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.comment-item {
  display: flex;
  gap: 15px;
}

.comment-content {
  flex: 1;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.comment-author {
  font-weight: 600;
  font-size: 15px;
}

.comment-date {
  color: #999;
  font-size: 13px;
}

.comment-content p {
  margin: 0;
  line-height: 1.6;
  color: #666;
}
</style>
