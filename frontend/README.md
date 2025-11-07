# Recipe Platform Frontend

Vue.js frontend application for Recipe and Food Sharing Platform.

## Technology Stack

- **Vue 3** - Progressive JavaScript framework
- **Vue Router** - Official router for Vue.js
- **Pinia** - State management
- **Element Plus** - UI component library
- **Axios** - HTTP client
- **Vite** - Build tool and dev server

## Quick Start

### Prerequisites

- Node.js 22
- npm or yarn

### Setup

1. Install dependencies:
```bash
npm install
```

2. Start development server:
```bash
npm run dev
```

The application will be available at `http://localhost:5173`

3. Build for production:
```bash
npm run build
```

## Project Structure

```
src/
├── assets/              # Static assets (images, styles)
├── components/          # Reusable Vue components
├── router/
│   └── index.js        # Route configuration
├── stores/
│   └── auth.js         # Authentication state
├── utils/
│   └── axios.js        # Axios configuration
├── views/
│   ├── Login.vue       # Login page
│   ├── admin/          # Admin pages
│   │   ├── Layout.vue  # Admin layout
│   │   ├── Home.vue    # Admin dashboard
│   │   ├── Users.vue   # User management
│   │   ├── Recipes.vue # Recipe management
│   │   ├── Notes.vue   # Note management
│   │   └── Announcements.vue
│   └── user/           # User pages
│       └── Home.vue    # User homepage
├── App.vue             # Root component
└── main.js             # Application entry point
```

## Features

### User Features

- **Authentication**
  - Login/Logout
  - JWT token management
  - Auto-redirect based on role

- **Homepage**
  - Carousel banner
  - Featured recipes
  - Food notes/diaries
  - Announcements
  - Product showcase

- **Navigation**
  - Responsive header
  - Search functionality
  - User dropdown menu

### Admin Features

- **Dashboard**
  - Statistics cards
  - Data visualization (charts)
  - Quick access to management pages

- **User Management**
  - View all users
  - Add/Edit/Delete users
  - Search and filter
  - Pagination

- **Recipe Management**
  - View all recipes
  - Moderate recipes
  - Feature recipes
  - Delete recipes

- **Note Management**
  - View all food notes
  - Moderate notes
  - Delete notes

- **Announcement Management**
  - Create announcements
  - Edit announcements
  - Delete announcements

## Configuration

### API Proxy

The development server proxies API requests to the backend:

```javascript
// vite.config.js
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

### Environment Variables

Create `.env` file for environment-specific configuration:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

## State Management

Using Pinia for state management:

```javascript
// stores/auth.js
export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
  
  // ... store logic
})
```

## Routing

Protected routes require authentication:

```javascript
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
  } else {
    next()
  }
})
```

## Styling

- Using Element Plus components for consistent UI
- Custom styles in component `<style scoped>` sections
- Global styles in `App.vue`

## Development

### Hot Module Replacement

Vite provides instant HMR - changes to Vue files update immediately without full page reload.

### Code Organization

- Keep components small and focused
- Use composition API with `<script setup>`
- Extract reusable logic into composables
- Use TypeScript for better type safety (optional)

### Best Practices

1. **Component Naming**: Use PascalCase for component names
2. **Props**: Define prop types and defaults
3. **Events**: Use descriptive event names
4. **State**: Keep component state minimal
5. **API Calls**: Centralize in stores or utils
6. **Error Handling**: Always handle API errors
7. **Loading States**: Show loading indicators

## Testing

```bash
# Run unit tests (when implemented)
npm run test

# Run e2e tests (when implemented)
npm run test:e2e
```

## Building for Production

```bash
# Build optimized production bundle
npm run build

# Preview production build
npm run preview
```

The built files will be in the `dist/` directory.

## Deployment

### Static Hosting

Deploy the `dist/` folder to:
- Netlify
- Vercel
- GitHub Pages
- AWS S3 + CloudFront
- Any static hosting service

### Nginx Configuration

```nginx
server {
    listen 80;
    server_name your-domain.com;
    root /path/to/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## Troubleshooting

### Common Issues

**API calls failing:**
- Check backend is running
- Verify proxy configuration
- Check CORS settings
- Inspect network tab in browser

**Login not working:**
- Clear localStorage
- Check token format
- Verify credentials
- Check API response

**Styles not loading:**
- Ensure Element Plus is imported
- Check component style scoping
- Verify CSS imports

## Next Steps

1. Implement remaining CRUD operations
2. Add form validation
3. Implement file upload
4. Add loading states
5. Implement error boundaries
6. Add unit tests
7. Optimize performance
8. Add accessibility features
9. Implement responsive design
10. Add internationalization (i18n)

## Resources

- [Vue 3 Documentation](https://vuejs.org/)
- [Vue Router Documentation](https://router.vuejs.org/)
- [Pinia Documentation](https://pinia.vuejs.org/)
- [Element Plus Documentation](https://element-plus.org/)
- [Vite Documentation](https://vitejs.dev/)
