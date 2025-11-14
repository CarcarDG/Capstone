# 🚀 Hosting Finalization Checklist

## Complete Step-by-Step Guide to Deploy Your Recipe Platform

---

## 📋 Phase 1: Pre-Deployment (Do This First)

### ☐ 1.1 Choose Your Hosting Provider

**Recommended Options:**

| Provider | Best For | Cost | Difficulty |
|----------|----------|------|------------|
| **Vercel + Railway** | Quick deployment | Free-$15/mo | ⭐ Easy |
| **DigitalOcean** | Full control | $12/mo | ⭐⭐ Medium |
| **AWS/GCP** | Enterprise | Variable | ⭐⭐⭐ Hard |

**My Recommendation:** Start with **Vercel (Frontend) + Railway (Backend + Database)**

### ☐ 1.2 Register Domain Name (Optional but Recommended)

- **Providers:** Namecheap, GoDaddy, Google Domains
- **Cost:** $10-15/year
- **Example:** `myrecipeplatform.com`

### ☐ 1.3 Prepare Your Code

```powershell
# 1. Create production environment files
cd frontend
Copy-Item .env.development .env.production

# 2. Update production API URL
# Edit .env.production and change to your production backend URL
```

---

## 🎯 Phase 2: Quick Deployment (Easiest Method)

### Option A: Vercel + Railway (Recommended for Beginners)

#### Step 1: Deploy Backend to Railway

1. **Create Railway Account**
   - Go to https://railway.app
   - Sign up with GitHub

2. **Create New Project**
   - Click "New Project"
   - Select "Deploy from GitHub repo"
   - Connect your GitHub account
   - Select your repository

3. **Add MySQL Database**
   - In your project, click "New"
   - Select "Database" → "MySQL"
   - Railway will create a database automatically

4. **Configure Backend Environment Variables**
   
   Click on your backend service → Variables → Add these:
   
   ```
   SPRING_DATASOURCE_URL=mysql://[Railway will provide this]
   SPRING_DATASOURCE_USERNAME=[Railway will provide this]
   SPRING_DATASOURCE_PASSWORD=[Railway will provide this]
   JWT_SECRET=your-super-secret-key-change-this-to-something-random-and-long
   CORS_ALLOWED_ORIGINS=https://your-frontend-url.vercel.app
   ```

5. **Deploy Database Schema**
   
   Railway provides a MySQL client. Use it to run:
   ```sql
   -- Copy contents of database/schema.sql
   -- Copy contents of database/sample_data.sql
   ```

6. **Get Backend URL**
   - Railway will provide a URL like: `https://your-app.railway.app`
   - Copy this URL for frontend configuration

#### Step 2: Deploy Frontend to Vercel

1. **Create Vercel Account**
   - Go to https://vercel.com
   - Sign up with GitHub

2. **Import Project**
   - Click "New Project"
   - Import your GitHub repository
   - Select the repository

3. **Configure Build Settings**
   ```
   Framework Preset: Vite
   Root Directory: frontend
   Build Command: npm run build
   Output Directory: dist
   Install Command: npm install
   ```

4. **Add Environment Variables**
   ```
   VITE_API_BASE_URL=https://your-backend.railway.app/api
   ```

5. **Deploy**
   - Click "Deploy"
   - Wait 2-3 minutes
   - Your site will be live at: `https://your-project.vercel.app`

6. **Add Custom Domain (Optional)**
   - Go to Project Settings → Domains
   - Add your custom domain
   - Follow DNS configuration instructions

#### Step 3: Test Your Deployment

1. Visit your Vercel URL
2. Try to login with: `admin` / `admin123`
3. Test all features:
   - ✅ Browse recipes
   - ✅ View recipe details
   - ✅ Create new recipe
   - ✅ User profile
   - ✅ Meal planner

---

## 🖥️ Phase 3: VPS Deployment (For More Control)

### Prerequisites

- Ubuntu 22.04 VPS (DigitalOcean, Linode, Vultr)
- Root or sudo access
- Domain name pointed to your server IP

### Step 1: Initial Server Setup

```bash
# SSH into your server
ssh root@your_server_ip

# Update system
apt update && apt upgrade -y

# Create non-root user
adduser recipeapp
usermod -aG sudo recipeapp
su - recipeapp
```

### Step 2: Install Required Software

```bash
# Install Java 17
sudo apt install openjdk-17-jdk -y
java -version

# Install MySQL
sudo apt install mysql-server -y
sudo mysql_secure_installation

# Install Node.js 18
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install -y nodejs
node --version

# Install Nginx
sudo apt install nginx -y
sudo systemctl status nginx

# Install Maven
sudo apt install maven -y
mvn -version

# Install Git
sudo apt install git -y
```

### Step 3: Setup Database

```bash
# Login to MySQL
sudo mysql

# Create database and user
CREATE DATABASE recipe_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'recipeuser'@'localhost' IDENTIFIED BY 'StrongPassword123!';
GRANT ALL PRIVILEGES ON recipe_platform.* TO 'recipeuser'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# Import schema (upload your SQL files first)
mysql -u recipeuser -p recipe_platform < schema.sql
mysql -u recipeuser -p recipe_platform < sample_data.sql
```

### Step 4: Deploy Backend

```bash
# Clone your repository
cd /home/recipeapp
git clone https://github.com/yourusername/recipe-platform.git
cd recipe-platform/backend

# Create production properties
sudo nano src/main/resources/application-prod.properties
```

**application-prod.properties:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipe_platform
spring.datasource.username=recipeuser
spring.datasource.password=StrongPassword123!

server.port=8080
jwt.secret=CHANGE_THIS_TO_VERY_LONG_RANDOM_STRING_256_BITS_OR_MORE

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false
logging.level.root=WARN

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
After=syslog.target network.target mysql.service

[Service]
User=recipeapp
WorkingDirectory=/home/recipeapp/recipe-platform/backend
ExecStart=/usr/bin/java -jar -Dspring.profiles.active=prod /home/recipeapp/recipe-platform/backend/target/recipe-platform-1.0.0.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# Start backend
sudo systemctl daemon-reload
sudo systemctl enable recipe-backend
sudo systemctl start recipe-backend
sudo systemctl status recipe-backend

# Check logs
sudo journalctl -u recipe-backend -f
```

### Step 5: Deploy Frontend

```bash
cd /home/recipeapp/recipe-platform/frontend

# Create production env
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

# Copy to web directory
sudo mkdir -p /var/www/recipe-platform
sudo cp -r dist/* /var/www/recipe-platform/
sudo chown -R www-data:www-data /var/www/recipe-platform
```

### Step 6: Configure Nginx

```bash
sudo nano /etc/nginx/sites-available/recipe-platform
```

**Nginx Configuration:**
```nginx
server {
    listen 80;
    server_name yourdomain.com www.yourdomain.com;

    # Frontend
    root /var/www/recipe-platform;
    index index.html;

    location / {
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

    # Gzip compression
    gzip on;
    gzip_vary on;
    gzip_min_length 1024;
    gzip_types text/plain text/css text/xml text/javascript application/x-javascript application/xml+rss application/json;
}
```

```bash
# Enable site
sudo ln -s /etc/nginx/sites-available/recipe-platform /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

### Step 7: Setup SSL (HTTPS)

```bash
# Install Certbot
sudo apt install certbot python3-certbot-nginx -y

# Get SSL certificate
sudo certbot --nginx -d yourdomain.com -d www.yourdomain.com

# Follow prompts:
# - Enter email
# - Agree to terms
# - Choose redirect HTTP to HTTPS (option 2)

# Test auto-renewal
sudo certbot renew --dry-run
```

### Step 8: Setup Firewall

```bash
# Configure UFW
sudo ufw allow OpenSSH
sudo ufw allow 'Nginx Full'
sudo ufw enable
sudo ufw status
```

---

## 🔒 Phase 4: Security Hardening

### ☐ 4.1 Change Default Passwords

```bash
# MySQL root password
sudo mysql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'NewStrongPassword123!';
FLUSH PRIVILEGES;
EXIT;
```

### ☐ 4.2 Update JWT Secret

Edit `application-prod.properties`:
```properties
jwt.secret=USE_THIS_COMMAND_TO_GENERATE: openssl rand -base64 64
```

### ☐ 4.3 Setup Fail2Ban (Prevent Brute Force)

```bash
sudo apt install fail2ban -y
sudo systemctl enable fail2ban
sudo systemctl start fail2ban
```

### ☐ 4.4 Enable Automatic Security Updates

```bash
sudo apt install unattended-upgrades -y
sudo dpkg-reconfigure -plow unattended-upgrades
```

---

## 📊 Phase 5: Monitoring & Backups

### ☐ 5.1 Setup Database Backups

```bash
# Create backup script
nano ~/backup-database.sh
```

```bash
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/home/recipeapp/backups"
mkdir -p $BACKUP_DIR

mysqldump -u recipeuser -p'StrongPassword123!' recipe_platform > $BACKUP_DIR/recipe_$DATE.sql

# Keep only last 7 days
find $BACKUP_DIR -name "recipe_*.sql" -mtime +7 -delete

echo "Backup completed: recipe_$DATE.sql"
```

```bash
# Make executable
chmod +x ~/backup-database.sh

# Test backup
~/backup-database.sh

# Schedule daily backups (2 AM)
crontab -e
# Add this line:
0 2 * * * /home/recipeapp/backup-database.sh >> /home/recipeapp/backup.log 2>&1
```

### ☐ 5.2 Setup Monitoring

```bash
# Install htop for monitoring
sudo apt install htop -y

# Check system resources
htop

# Monitor logs
sudo journalctl -u recipe-backend -f
sudo tail -f /var/log/nginx/access.log
sudo tail -f /var/log/nginx/error.log
```

---

## ✅ Phase 6: Final Testing

### ☐ 6.1 Test All Features

Visit your domain and test:

- [ ] Homepage loads
- [ ] Login works (admin/admin123)
- [ ] Register new account
- [ ] Browse recipes
- [ ] View recipe details
- [ ] Create new recipe
- [ ] Edit profile
- [ ] Meal planner
- [ ] Collections
- [ ] Announcements
- [ ] Admin panel (if admin)

### ☐ 6.2 Performance Testing

```bash
# Install Apache Bench
sudo apt install apache2-utils -y

# Test homepage
ab -n 100 -c 10 https://yourdomain.com/

# Test API
ab -n 100 -c 10 https://yourdomain.com/api/recipes
```

### ☐ 6.3 Security Testing

- [ ] HTTPS working (green padlock)
- [ ] HTTP redirects to HTTPS
- [ ] SQL injection protection
- [ ] XSS protection
- [ ] CSRF protection

---

## 🎉 Phase 7: Go Live!

### ☐ 7.1 Update DNS (If Using Custom Domain)

**At your domain registrar:**
```
Type: A Record
Name: @
Value: YOUR_SERVER_IP
TTL: 3600

Type: A Record
Name: www
Value: YOUR_SERVER_IP
TTL: 3600
```

Wait 5-60 minutes for DNS propagation.

### ☐ 7.2 Announce Your Launch

- Share with friends and family
- Post on social media
- Add to your portfolio

### ☐ 7.3 Monitor First 24 Hours

```bash
# Watch logs
sudo journalctl -u recipe-backend -f

# Check errors
sudo tail -f /var/log/nginx/error.log

# Monitor resources
htop
```

---

## 🔄 Maintenance Tasks

### Daily
- [ ] Check application logs
- [ ] Monitor server resources

### Weekly
- [ ] Review backup logs
- [ ] Check SSL certificate status
- [ ] Update dependencies

### Monthly
- [ ] System updates: `sudo apt update && sudo apt upgrade`
- [ ] Review security logs
- [ ] Database optimization

---

## 🆘 Troubleshooting

### Issue: Backend won't start

```bash
# Check logs
sudo journalctl -u recipe-backend -n 100

# Check if port is in use
sudo netstat -tulpn | grep 8080

# Restart service
sudo systemctl restart recipe-backend
```

### Issue: Frontend shows 404

```bash
# Check Nginx config
sudo nginx -t

# Check file permissions
ls -la /var/www/recipe-platform

# Restart Nginx
sudo systemctl restart nginx
```

### Issue: Database connection failed

```bash
# Check MySQL status
sudo systemctl status mysql

# Test connection
mysql -u recipeuser -p recipe_platform

# Check credentials in application-prod.properties
```

### Issue: SSL certificate expired

```bash
# Renew certificate
sudo certbot renew

# Check expiration
sudo certbot certificates
```

---

## 📞 Support Resources

- **Deployment Guide:** See `DEPLOYMENT_GUIDE.md`
- **Backend Setup:** See `BACKEND_CONNECTION_GUIDE.md`
- **Authentication:** See `AUTHENTICATION_SETUP.md`

---

## ✅ Final Checklist

- [ ] Application accessible via domain
- [ ] HTTPS enabled and working
- [ ] All features tested and working
- [ ] Database backups configured
- [ ] Monitoring setup
- [ ] Security hardened
- [ ] Documentation updated
- [ ] Team/users notified

**🎊 Congratulations! Your Recipe Platform is now live and production-ready! 🎊**
