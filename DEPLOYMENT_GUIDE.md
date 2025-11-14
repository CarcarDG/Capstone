# 🚀 Deployment Guide - Recipe Platform

## Overview

This guide covers deploying your Recipe Platform to a live server. We'll cover multiple hosting options and configurations.

---

## 📋 Pre-Deployment Checklist

### ☐ Code Preparation
- [ ] All features tested locally
- [ ] Environment variables configured
- [ ] Database schema finalized
- [ ] API endpoints tested
- [ ] Frontend build successful
- [ ] Backend build successful

### ☐ Security
- [ ] Change JWT secret key
- [ ] Update database passwords
- [ ] Enable HTTPS
- [ ] Configure CORS properly
- [ ] Remove debug logging
- [ ] Secure API endpoints

### ☐ Performance
- [ ] Frontend optimized (minified)
- [ ] Images compressed
- [ ] Database indexed
- [ ] Caching configured

---

## 🌐 Deployment Options

### Option 1: Traditional VPS (Recommended for Full Control)
- **Providers:** DigitalOcean, Linode, Vultr, AWS EC2
- **Cost:** $5-20/month
- **Best for:** Full control, custom configuration

### Option 2: Platform as a Service (Easiest)
- **Frontend:** Vercel, Netlify, GitHub Pages
- **Backend:** Heroku, Railway, Render
- **Cost:** Free tier available, $7-15/month for production
- **Best for:** Quick deployment, automatic scaling

### Option 3: Cloud Platforms (Enterprise)
- **Providers:** AWS, Google Cloud, Azure
- **Cost:** Variable, pay-as-you-go
- **Best for:** Large scale, enterprise needs

---

## 🔧 Option 1: VPS Deployment (Ubuntu Server)

### Step 1: Server Setup

```bash
# Update system
sudo apt update && sudo apt upgrade -y

# Install Java 17
sudo apt install openjdk-17-jdk -y

# Install MySQL
sudo apt install mysql-server -y

# Install Node.js 18
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install -y nodejs

# Install Nginx
sudo apt install nginx -y

# Install Maven
sudo apt install maven -y
```

### Step 2: Database Setup

```bash
# Secure MySQL
sudo mysql_secure_installation

# Login to MySQL
sudo mysql -u root -p

# Create database and user
CREATE DATABASE recipe_platform;
CREATE USER 'recipeuser'@'localhost' IDENTIFIED BY 'strong_password_here';
GRANT ALL PRIVILEGES ON recipe_platform.* TO 'recipeuser'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# Import schema
mysql -u recipeuser -p recipe_platform < /path/to/schema.sql
mysql -u recipeuser -p recipe_platform < /path/to/sample_data.sql
```

### Step 3: Backend Deployment

```bash
# Create app directory
sudo mkdir -p /var/www/recipe-platform
cd /var/www/recipe-platform

# Upload your backend code (use git or scp)
git clone https://github.com/yourusername/recipe-platform.git
cd recipe-platform/backend

# Update application.properties
nano src/main/resources/application.properties
```

**Production application.properties:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipe_platform
spring.datasource.username=recipeuser
spring.datasource.password=strong_password_here

server.port=8080
jwt.secret=CHANGE_THIS_TO_A_VERY_LONG_RANDOM_STRING_AT_LEAST_256_BITS

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false

cors.allowed-origins=https://yourdomain.com
```

```bash
# Build backend
mvn clean package -DskipTests

# Create systemd service
sudo nano /etc/systemd/system/recipe-backend.service
```

**recipe-backend.service:**
```ini
[Unit]
Description=Recipe Platform Backend
After=syslog.target network.target

[Service]
User=www-data
WorkingDirectory=/var/www/recipe-platform/backend
ExecStart=/usr/bin/java -jar /var/www/recipe-platform/backend/target/recipe-platform-1.0.0.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# Start backend service
sudo systemctl daemon-reload
sudo systemctl enable recipe-backend
sudo systemctl start recipe-backend
sudo systemctl status recipe-backend
```

### Step 4: Frontend Deployment

```bash
cd /var/www/recipe-platform/frontend

# Create production environment file
nano .env.production
```

**.env.production:**
```env
VITE_API_BASE_URL=https://yourdomain.com/api
```

```bash
# Build frontend
npm install
npm run build

# Copy build to nginx directory
sudo cp -r dist/* /var/www/html/
```

### Step 5: Nginx Configuration

```bash
sudo nano /etc/nginx/sites-available/recipe-platform
```

**Nginx Configuration:**
```nginx
server {
    listen 80;
    server_name yourdomain.com www.yourdomain.com;

    # Frontend
    location / {
        root /var/www/html;
        try_files $uri $uri/ /index.html;
    }

    # Backend API
    location /api {
        proxy_pass http://localhost:8080;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

```bash
# Enable site
sudo ln -s /etc/nginx/sites-available/recipe-platform /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

### Step 6: SSL Certificate (HTTPS)

```bash
# Install Certbot
sudo apt install certbot python3-certbot-nginx -y

# Get SSL certificate
sudo certbot --nginx -d yourdomain.com -d www.yourdomain.com

# Auto-renewal is configured automatically
sudo certbot renew --dry-run
```

---

## 🚀 Option 2: Platform as a Service (PaaS)

### Frontend: Vercel Deployment

1. **Push code to GitHub**
```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/yourusername/recipe-platform.git
git push -u origin main
```

2. **Deploy to Vercel**
- Go to https://vercel.com
- Click "New Project"
- Import your GitHub repository
- Configure:
  - Framework: Vite
  - Root Directory: `frontend`
  - Build Command: `npm run build`
  - Output Directory: `dist`
  - Environment Variables:
    ```
    VITE_API_BASE_URL=https://your-backend-url.com/api
    ```
- Click "Deploy"

### Backend: Railway Deployment

1. **Prepare backend**
```bash
# Add Procfile in backend root
echo "web: java -jar target/recipe-platform-1.0.0.jar" > Procfile
```

2. **Deploy to Railway**
- Go to https://railway.app
- Click "New Project"
- Select "Deploy from GitHub repo"
- Choose your repository
- Add environment variables:
  ```
  SPRING_DATASOURCE_URL=jdbc:mysql://...
  SPRING_DATASOURCE_USERNAME=...
  SPRING_DATASOURCE_PASSWORD=...
  JWT_SECRET=...
  ```
- Railway will auto-deploy

### Database: Railway MySQL

- In Railway project, click "New"
- Select "Database" → "MySQL"
- Copy connection details
- Update backend environment variables

---

## 🔒 Security Checklist

### Backend Security

```properties
# application.properties (Production)

# Strong JWT secret (256+ bits)
jwt.secret=${JWT_SECRET:your-very-long-random-secret-key-here}

# Disable debug
spring.jpa.show-sql=false
logging.level.root=WARN

# Secure headers
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.http-only=true

# CORS (specific domains only)
cors.allowed-origins=https://yourdomain.com
```

### Frontend Security

```javascript
// Don't expose sensitive data
// Use environment variables
const API_URL = import.meta.env.VITE_API_BASE_URL

// Validate user input
// Sanitize data before display
```

### Database Security

```sql
-- Use strong passwords
-- Limit user privileges
GRANT SELECT, INSERT, UPDATE, DELETE ON recipe_platform.* TO 'recipeuser'@'localhost';

-- Regular backups
mysqldump -u root -p recipe_platform > backup_$(date +%Y%m%d).sql
```

---

## 📊 Monitoring & Maintenance

### Application Monitoring

```bash
# Check backend logs
sudo journalctl -u recipe-backend -f

# Check Nginx logs
sudo tail -f /var/log/nginx/access.log
sudo tail -f /var/log/nginx/error.log

# Check system resources
htop
df -h
free -m
```

### Database Backup

```bash
# Create backup script
nano /home/user/backup-db.sh
```

```bash
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/home/user/backups"
mkdir -p $BACKUP_DIR

mysqldump -u recipeuser -p'password' recipe_platform > $BACKUP_DIR/recipe_$DATE.sql

# Keep only last 7 days
find $BACKUP_DIR -name "recipe_*.sql" -mtime +7 -delete
```

```bash
# Make executable
chmod +x /home/user/backup-db.sh

# Add to crontab (daily at 2 AM)
crontab -e
0 2 * * * /home/user/backup-db.sh
```

---

## 🔄 Continuous Deployment

### GitHub Actions (Automated Deployment)

Create `.github/workflows/deploy.yml`:

```yaml
name: Deploy to Production

on:
  push:
    branches: [ main ]

jobs:
  deploy-frontend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Setup Node.js
        uses: actions/setup-node@v2
        with:
          node-version: '18'
      - name: Build Frontend
        run: |
          cd frontend
          npm install
          npm run build
      - name: Deploy to Server
        uses: appleboy/scp-action@master
        with:
          host: ${{ secrets.SERVER_HOST }}
          username: ${{ secrets.SERVER_USER }}
          key: ${{ secrets.SSH_KEY }}
          source: "frontend/dist/*"
          target: "/var/www/html"

  deploy-backend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Setup Java
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Build Backend
        run: |
          cd backend
          mvn clean package -DskipTests
      - name: Deploy to Server
        # Add deployment steps
```

---

## 🧪 Testing Production

### Health Checks

```bash
# Backend health
curl https://yourdomain.com/api/health

# Frontend
curl https://yourdomain.com

# Database connection
mysql -h localhost -u recipeuser -p recipe_platform -e "SELECT 1"
```

### Load Testing

```bash
# Install Apache Bench
sudo apt install apache2-utils

# Test API endpoint
ab -n 1000 -c 10 https://yourdomain.com/api/recipes

# Test frontend
ab -n 1000 -c 10 https://yourdomain.com/
```

---

## 📝 Post-Deployment

### DNS Configuration

Point your domain to server:
```
A Record: @ → Your_Server_IP
A Record: www → Your_Server_IP
```

### Email Setup (Optional)

For password reset, notifications:
- Use SendGrid, Mailgun, or AWS SES
- Configure SMTP in application.properties

### Analytics (Optional)

- Google Analytics
- Plausible Analytics
- Matomo

---

## 🆘 Troubleshooting

### Backend won't start
```bash
# Check logs
sudo journalctl -u recipe-backend -n 50

# Check port
sudo netstat -tulpn | grep 8080

# Check Java
java -version
```

### Frontend 404 errors
```bash
# Check Nginx config
sudo nginx -t

# Check file permissions
ls -la /var/www/html

# Restart Nginx
sudo systemctl restart nginx
```

### Database connection failed
```bash
# Check MySQL status
sudo systemctl status mysql

# Test connection
mysql -u recipeuser -p recipe_platform

# Check firewall
sudo ufw status
```

---

## ✅ Success Checklist

- [ ] Application accessible via domain
- [ ] HTTPS working (green padlock)
- [ ] Login/Register working
- [ ] All pages loading correctly
- [ ] API calls successful
- [ ] Images loading
- [ ] Database queries working
- [ ] Backups configured
- [ ] Monitoring setup
- [ ] SSL auto-renewal working

---

## 📚 Additional Resources

- **Nginx Docs:** https://nginx.org/en/docs/
- **Let's Encrypt:** https://letsencrypt.org/
- **Vercel Docs:** https://vercel.com/docs
- **Railway Docs:** https://docs.railway.app/
- **Spring Boot Deployment:** https://spring.io/guides/gs/spring-boot/

---

**Congratulations! Your Recipe Platform is now live! 🎉**
