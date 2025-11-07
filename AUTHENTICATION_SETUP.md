# Authentication System Integration Guide

## Overview

The Recipe Platform now has a fully integrated authentication system that connects the Vue.js frontend with the Spring Boot backend.

## Architecture

### Frontend (Vue.js + Pinia)
- **Auth Store** (`frontend/src/stores/auth.js`) - Manages authentication state
- **API Client** (`frontend/src/api/axios.js`) - Axios instance with interceptors
- **Auth API** (`frontend/src/api/auth.js`) - Authentication API endpoints
- **Auth Composable** (`frontend/src/composables/useAuth.js`) - Reusable auth logic

### Backend (Spring Boot + JWT)
- **AuthService** - Handles login/register logic
- **JwtUtil** - JWT token generation and validation
- **SecurityConfig** - Spring Security configuration
- **UserRepository** - Database access for users

## Features

### ✅ Implemented Features

1. **User Login**
   - JWT token-based authentication
   - Automatic token storage in localStorage
   - Role-based redirection (Admin → /admin, User → /)

2. **User Registration**
   - New user signup
   - Password encryption (BCrypt)
   - Email and username uniqueness validation

3. **Token Management**
   - Automatic token injection in API requests
   - Token refresh on page reload
   - Automatic logout on token expiration

4. **Protected Routes**
   - Authentication required for user pages
   - Admin-only routes for admin panel
   - Automatic redirect to login if not authenticated

5. **Error Handling**
   - 401 Unauthorized → Auto logout and redirect to login
   - 403 Forbidden → Access denied message
   - Network errors → User-friendly messages

## Configuration

### Environment Variables

**Development** (`.env.development`):
```
VITE_API_BASE_URL=http://localhost:8080/api
```

**Production** (`.env.production`):
```
VITE_API_BASE_URL=/api
```

## Usage

### In Components

```vue
<script setup>
import { useAuth } from '@/composables/useAuth'

const { user, isAuthenticated, isAdmin, login, logout } = useAuth()

// Check if user is logged in
if (isAuthenticated.value) {
  console.log('User:', user.value.nickname)
}

// Check if user is admin
if (isAdmin.value) {
  console.log('Admin user')
}
</script>
```

### Login Example

```javascript
const { login } = useAuth()

async function handleLogin() {
  const success = await login({
    username: 'john',
    password: 'user123'
  })
  
  if (success) {
    router.push('/')
  }
}
```

### Register Example

```javascript
const { register } = useAuth()

async function handleRegister() {
  try {
    await register({
      username: 'newuser',
      email: 'user@example.com',
      phone: '1234567890',
      password: 'password123',
      nickname: 'New User'
    })
    router.push('/login')
  } catch (error) {
    console.error('Registration failed:', error)
  }
}
```

## API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | User login |
| POST | `/api/auth/register` | User registration |
| GET | `/api/auth/me` | Get current user info |
| POST | `/api/auth/logout` | User logout |

### Request/Response Examples

**Login Request:**
```json
{
  "username": "john",
  "password": "user123"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "john",
  "nickname": "John Doe",
  "role": "USER",
  "avatar": "https://example.com/avatar.jpg"
}
```

## Testing

### Test Accounts (from sample_data.sql)

**Admin Account:**
- Username: `admin`
- Password: `admin123`
- Role: ADMIN

**User Accounts:**
- Username: `john` / Password: `user123` / Role: USER
- Username: `jane` / Password: `user123` / Role: USER

## Switching Between Mock and Real Auth

### Using Real Authentication (Current Setup)
All components now use `@/stores/auth` which connects to the backend.

### Using Mock Authentication (For Testing Without Backend)
If you want to test without the backend:

1. Update imports in components:
```javascript
// Change from:
import { useAuthStore } from '@/stores/auth'

// To:
import { useAuthStore } from '@/stores/auth-mock'
```

2. Or create a toggle in your environment:
```javascript
// In auth composable
const authStore = import.meta.env.VITE_USE_MOCK_AUTH 
  ? useAuthStore('auth-mock') 
  : useAuthStore('auth')
```

## Security Best Practices

✅ **Implemented:**
- JWT tokens stored in localStorage
- Automatic token expiration handling
- HTTPS recommended for production
- Password hashing with BCrypt
- CORS configuration in backend

⚠️ **Recommendations:**
- Use HttpOnly cookies for tokens (more secure than localStorage)
- Implement refresh tokens for long sessions
- Add rate limiting for login attempts
- Enable CSRF protection
- Use environment-specific secrets

## Troubleshooting

### "Network Error" on Login
- **Cause**: Backend not running or wrong API URL
- **Solution**: 
  1. Start backend: `cd backend && mvn spring-boot:run`
  2. Check `.env.development` has correct URL
  3. Verify backend is running on port 8080

### "401 Unauthorized" After Login
- **Cause**: Token expired or invalid
- **Solution**: Clear localStorage and login again
```javascript
localStorage.clear()
```

### CORS Errors
- **Cause**: Backend not allowing frontend origin
- **Solution**: Update `SecurityConfig.java` to allow your frontend URL

### Token Not Persisting
- **Cause**: localStorage not working
- **Solution**: Check browser privacy settings, ensure localStorage is enabled

## Next Steps

### Recommended Enhancements

1. **Refresh Tokens**
   - Implement token refresh mechanism
   - Auto-refresh before expiration

2. **Remember Me**
   - Add "Remember Me" checkbox
   - Extended token expiration

3. **Password Reset**
   - Forgot password flow
   - Email verification

4. **Social Login**
   - Google OAuth
   - Facebook Login

5. **Two-Factor Authentication**
   - SMS verification
   - Authenticator app support

## Files Modified

### Created:
- `frontend/src/api/axios.js` - Axios configuration
- `frontend/src/api/auth.js` - Auth API methods
- `frontend/src/stores/auth.js` - Real auth store
- `frontend/src/composables/useAuth.js` - Auth composable
- `frontend/.env.development` - Dev environment config
- `frontend/.env.production` - Prod environment config

### Updated:
- `frontend/src/views/Login.vue` - Uses real auth
- `frontend/src/views/Signup.vue` - Uses real auth
- `frontend/src/views/user/Layout.vue` - Uses real auth
- `frontend/src/views/admin/Layout.vue` - Uses real auth
- `frontend/src/router/index.js` - Uses real auth

### Kept (for reference):
- `frontend/src/stores/auth-mock.js` - Mock auth (backup)

## Support

For issues or questions:
1. Check backend logs: `backend/logs/`
2. Check browser console for errors
3. Verify database connection
4. Test API endpoints with Postman/curl
