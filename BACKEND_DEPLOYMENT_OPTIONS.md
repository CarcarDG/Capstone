# Backend Deployment Options

Railway keeps failing, so here are proven alternatives for deploying your Spring Boot backend.

## Option 1: Render (RECOMMENDED - Easiest)

### Why Render?
- ✅ Free tier available (750 hours/month)
- ✅ Excellent Java 21 support
- ✅ Auto-deploys from GitHub
- ✅ Built-in database options
- ✅ Simple configuration

### Steps:
1. Go to https://render.com and sign up
2. Click "New +" → "Web Service"
3. Connect your GitHub repository
4. Configure:
   - **Name**: recipe-backend
   - **Region**: Singapore
   - **Branch**: main
   - **Root Directory**: `backend`
   - **Runtime**: Java
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar target/recipe-platform-1.0.0.jar`
5. Add Environment Variables:
   - `JAVA_VERSION=21`
   - `DATABASE_URL=your_mysql_url`
   - `JWT_SECRET=your_secret_key`
   - `JWT_EXPIRATION=86400000`
6. Click "Create Web Service"

**OR** use the `render.yaml` file (auto-detected):
- Just connect your repo and Render will use the config automatically

---

## Option 2: Heroku (Classic & Reliable)

### Why Heroku?
- ✅ Mature platform with excellent Spring Boot support
- ✅ Free tier available (with some limitations)
- ✅ Easy CLI tools
- ✅ Great documentation

### Steps:
1. Install Heroku CLI: https://devcenter.heroku.com/articles/heroku-cli
2. Login: `heroku login`
3. Create app:
   ```bash
   cd backend
   heroku create your-recipe-backend
   ```
4. Set Java version (already configured in `system.properties`)
5. Add MySQL database:
   ```bash
   heroku addons:create jawsdb:kitefin
   ```
6. Set environment variables:
   ```bash
   heroku config:set JWT_SECRET=your_secret_key
   heroku config:set JWT_EXPIRATION=86400000
   heroku config:set SPRING_PROFILES_ACTIVE=prod
   ```
7. Deploy:
   ```bash
   git push heroku main
   ```

---

## Option 3: Google Cloud Run (Serverless, Pay-per-use)

### Why Cloud Run?
- ✅ Only pay for actual usage (very cheap for low traffic)
- ✅ Auto-scales to zero
- ✅ Fast cold starts
- ✅ Free tier: 2 million requests/month

### Steps:
1. Install Google Cloud SDK: https://cloud.google.com/sdk/docs/install
2. Login and set project:
   ```bash
   gcloud auth login
   gcloud config set project YOUR_PROJECT_ID
   ```
3. Build and deploy (using the Dockerfile):
   ```bash
   cd backend
   gcloud run deploy recipe-backend \
     --source . \
     --platform managed \
     --region asia-southeast1 \
     --allow-unauthenticated \
     --set-env-vars="SPRING_PROFILES_ACTIVE=prod,JWT_SECRET=your_secret,DATABASE_URL=your_db_url"
   ```

---

## Option 4: AWS Elastic Beanstalk (Enterprise-grade)

### Why Elastic Beanstalk?
- ✅ Free tier for 12 months
- ✅ Excellent for Spring Boot
- ✅ Auto-scaling and load balancing
- ✅ Integrated with other AWS services

### Steps:
1. Install EB CLI: `pip install awsebcli`
2. Initialize:
   ```bash
   cd backend
   eb init -p java-21 recipe-backend --region ap-southeast-1
   ```
3. Create environment:
   ```bash
   eb create recipe-backend-env
   ```
4. Set environment variables:
   ```bash
   eb setenv DATABASE_URL=your_db JWT_SECRET=your_secret
   ```
5. Deploy:
   ```bash
   mvn clean package -DskipTests
   eb deploy
   ```

---

## Option 5: DigitalOcean App Platform

### Why DigitalOcean?
- ✅ Simple pricing ($5/month for basic)
- ✅ Good Java support
- ✅ Integrated with DO databases
- ✅ Easy to use

### Steps:
1. Go to https://cloud.digitalocean.com/apps
2. Click "Create App"
3. Connect GitHub repository
4. Configure:
   - **Source Directory**: `backend`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Run Command**: `java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar target/recipe-platform-1.0.0.jar`
5. Add environment variables
6. Deploy

---

## Quick Comparison

| Platform | Free Tier | Ease of Use | Java 21 Support | Best For |
|----------|-----------|-------------|-----------------|----------|
| **Render** | ✅ 750hrs | ⭐⭐⭐⭐⭐ | ✅ Excellent | Quick deployment |
| **Heroku** | ✅ Limited | ⭐⭐⭐⭐ | ✅ Good | Classic apps |
| **Cloud Run** | ✅ 2M req | ⭐⭐⭐ | ✅ Excellent | Low traffic |
| **AWS EB** | ✅ 12mo | ⭐⭐⭐ | ✅ Excellent | Enterprise |
| **DigitalOcean** | ❌ $5/mo | ⭐⭐⭐⭐ | ✅ Good | Simple hosting |

---

## My Recommendation

**Start with Render** - it's the easiest and most reliable for your use case:
1. Already configured with `render.yaml`
2. Free tier is generous
3. Excellent Java 21 support
4. Auto-deploys from GitHub
5. Simple environment variable management

If Render doesn't work, try **Heroku** next (files already created: `Procfile` and `system.properties`).

---

## Files Created for You

- ✅ `render.yaml` - Render configuration
- ✅ `backend/Dockerfile` - Docker configuration (works with Cloud Run, Railway, etc.)
- ✅ `backend/Procfile` - Heroku configuration
- ✅ `backend/system.properties` - Heroku Java version
- ✅ `backend/nixpacks.toml` - Railway/Nixpacks (if you want to retry)
- ✅ `backend/railway.json` - Railway configuration (if you want to retry)

All configurations use Java 21 and are production-ready!
