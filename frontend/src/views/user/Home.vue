<template>
  <div class="user-home">
      <!-- Carousel -->
      <section class="carousel-section">
        <el-carousel height="400px" indicator-position="outside">
          <el-carousel-item v-for="(banner, index) in carouselBanners" :key="index">
            <div class="carousel-item" :style="{ backgroundImage: `url(${banner.image})` }">
              <div class="carousel-overlay">
                <h2>{{ banner.title }}</h2>
                <p>{{ banner.subtitle }}</p>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </section>

      <!-- Announcements -->
      <section class="announcements-section">
        <div class="section-header">
          <h2>Announcements</h2>
          <router-link to="/announcements">More >></router-link>
        </div>
        <div class="announcements-list">
          <div class="announcement-item">
            <span class="announcement-title">Welcome to Recipe Platform!</span>
            <span class="announcement-date">2025-01-15</span>
          </div>
          <div class="announcement-item">
            <span class="announcement-title">All features are now available</span>
            <span class="announcement-date">2025-01-14</span>
          </div>
          <div class="announcement-item">
            <span class="announcement-title">Join our weekend cooking challenge</span>
            <span class="announcement-date">2025-01-13</span>
          </div>
        </div>
      </section>

      <!-- Featured Recipes -->
      <section class="recipes-section">
        <div class="section-header">
          <h2>Featured Recipes</h2>
          <router-link to="/recipes">More >></router-link>
        </div>
        <div class="recipes-grid">
          <div class="recipe-card" v-for="(recipe, i) in featuredRecipes" :key="i" @click="viewRecipe(recipe.id)">
            <div class="recipe-image">
              <img :src="recipe.image" :alt="recipe.title">
            </div>
            <div class="recipe-info">
              <h3>{{ recipe.title }}</h3>
              <div class="recipe-meta">
                <el-avatar size="small" src="https://i.pravatar.cc/200?img=33" />
                <span>{{ recipe.author }}</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Food Notes -->
      <section class="notes-section">
        <div class="section-header">
          <h2>Food Notes</h2>
          <router-link to="/notes">More >></router-link>
        </div>
        <div class="notes-grid">
          <div class="note-card" v-for="(note, i) in foodNotes" :key="i" @click="viewNote(note.id)">
            <div class="note-image">
              <img :src="note.image" :alt="note.title">
            </div>
            <div class="note-info">
              <h3>{{ note.title }}</h3>
              <p class="note-desc">{{ note.description }}</p>
            </div>
          </div>
        </div>
      </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const carouselBanners = [
  { 
    image: 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1200&h=400&fit=crop',
    title: 'Featured Food Recommendations',
    subtitle: 'Discover delicious recipes from our community'
  },
  { 
    image: 'https://images.unsplash.com/photo-1556910103-1c02745aae4d?w=1200&h=400&fit=crop',
    title: 'Beginner Tutorial Guide',
    subtitle: 'Learn cooking basics and essential techniques'
  },
  { 
    image: 'https://images.unsplash.com/photo-1547592180-85f173990554?w=1200&h=400&fit=crop',
    title: 'This Week\'s Popular Recipes',
    subtitle: 'Trending dishes everyone is cooking'
  }
]

const featuredRecipes = [
  { 
    id: 1,
    image: 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=300&h=200&fit=crop',
    title: 'Healthy Shrimp Mushroom Tofu Soup',
    author: 'John Doe'
  },
  { 
    id: 2,
    image: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=300&h=200&fit=crop',
    title: 'Low-Calorie Sweet Pumpkin Pancakes',
    author: 'John Doe'
  },
  { 
    id: 3,
    image: 'https://images.unsplash.com/photo-1525351484163-7529414344d8?w=300&h=200&fit=crop',
    title: 'Potato Shred Egg Pancake',
    author: 'John Doe'
  },
  { 
    id: 4,
    image: 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=300&h=200&fit=crop',
    title: 'Comfort Food That Heals the Soul',
    author: 'John Doe'
  }
]

const foodNotes = [
  { 
    id: 1,
    image: 'https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=300&h=200&fit=crop',
    title: 'Braised Pork Belly',
    description: 'Sharing my cooking journey...'
  },
  { 
    id: 2,
    image: 'https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=300&h=200&fit=crop',
    title: 'Peanut Red Bean Smoothie',
    description: 'Nutritious breakfast drink'
  },
  { 
    id: 3,
    image: 'https://images.unsplash.com/photo-1496116218417-1a781b1c416c?w=300&h=200&fit=crop',
    title: 'Juicy Dumplings',
    description: 'Irresistibly good!'
  },
  { 
    id: 4,
    image: 'https://images.unsplash.com/photo-1544025162-d76694265947?w=300&h=200&fit=crop',
    title: 'Braised Spare Ribs',
    description: 'Classic home-style dish'
  }
]

const viewRecipe = (id) => {
  router.push(`/recipes/${id}`)
}

const viewNote = (id) => {
  router.push(`/notes/${id}`)
}
</script>

<style scoped>
.user-home {
  background: #f5f5f5;
}

.carousel-section {
  margin-bottom: 40px;
}

.carousel-item {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: cover;
  background-position: center;
  position: relative;
}

.carousel-overlay {
  background: rgba(0, 0, 0, 0.4);
  padding: 40px;
  border-radius: 10px;
  text-align: center;
  color: white;
}

.carousel-overlay h2 {
  font-size: 36px;
  margin: 0 0 15px 0;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.carousel-overlay p {
  font-size: 18px;
  margin: 0;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-header h2 {
  font-size: 24px;
  color: #333;
}

.section-header a {
  color: #ff6b6b;
  text-decoration: none;
}

.announcements-section {
  background: white;
  padding: 20px;
  border-radius: 10px;
  margin-bottom: 40px;
}

.announcements-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.announcement-item {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.announcement-title {
  color: #333;
}

.announcement-date {
  color: #999;
  font-size: 14px;
}

.recipes-section,
.notes-section {
  margin-bottom: 40px;
}

.recipes-grid,
.notes-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.recipe-card,
.note-card {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.3s;
}

.recipe-card:hover,
.note-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.recipe-image,
.note-image {
  width: 100%;
  height: 200px;
  overflow: hidden;
}

.recipe-image img,
.note-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.recipe-info,
.note-info {
  padding: 15px;
}

.recipe-info h3,
.note-info h3 {
  font-size: 16px;
  margin-bottom: 10px;
  color: #333;
}

.recipe-meta {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  color: #666;
}

.note-desc {
  color: #666;
  font-size: 14px;
  margin: 0;
}
</style>
