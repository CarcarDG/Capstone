# Setup Checklist

Use this checklist to track your setup progress.

## ☐ Prerequisites Installation

### Java Development Kit (JDK 21)
- [ ] Downloaded JDK 21 from Oracle
- [ ] Installed JDK 21
- [ ] Verified: `java -version` shows version 21.x.x
- [ ] JAVA_HOME environment variable set (optional)

### Apache Maven
- [ ] Downloaded Maven binary zip
- [ ] Extracted to `C:\Program Files\Apache\maven`
- [ ] Added Maven bin to System PATH
- [ ] Closed and reopened PowerShell
- [ ] Verified: `mvn -version` works

### Node.js 22
- [ ] Downloaded Node.js 22 LTS installer
- [ ] Installed Node.js (includes npm)
- [ ] Verified: `node -v` shows v22.x.x
- [ ] Verified: `npm -v` shows version number

### MySQL Database
- [ ] Downloaded MySQL Installer
- [ ] Installed MySQL Server
- [ ] Set root password (and remembered it!)
- [ ] MySQL service is running
- [ ] Verified: `mysql --version` works

## ☐ Database Setup

- [ ] Opened PowerShell in project directory
- [ ] Ran `.\setup-database.bat`
- [ ] Entered MySQL root password
- [ ] Database `recipe_platform` created successfully
- [ ] Sample data loaded (3 users, recipes, notes, etc.)
- [ ] Verified in MySQL: `USE recipe_platform; SHOW TABLES;`

## ☐ Backend Configuration

- [ ] Opened `backend/src/main/resources/application.yml`
- [ ] Updated MySQL password to match root password
- [ ] Saved the file
- [ ] Verified database connection settings

## ☐ Backend Setup

- [ ] Opened PowerShell in `backend` directory
- [ ] Ran `mvn clean install` (first time - takes 5-10 minutes)
- [ ] Build completed successfully
- [ ] No error messages in output
- [ ] Can run `mvn spring-boot:run` without errors

## ☐ Frontend Setup

- [ ] Opened PowerShell in `frontend` directory
- [ ] Ran `npm install` (first time - takes 2-5 minutes)
- [ ] All packages installed successfully
- [ ] No error messages in output
- [ ] Can run `npm run dev` without errors

## ☐ Application Testing

### Starting the Application
- [ ] Ran `.\start-dev.bat` from project root
- [ ] Backend server started on port 8080
- [ ] Frontend server started on port 5173
- [ ] Browser opened automatically to http://localhost:5173
- [ ] No error messages in either terminal

### Admin Login Test
- [ ] Navigated to http://localhost:5173
- [ ] Logged in with: admin / admin123
- [ ] Redirected to admin dashboard
- [ ] Can see statistics cards
- [ ] Can navigate to User Management
- [ ] Can navigate to Recipe Management
- [ ] Can navigate to Note Management
- [ ] Can navigate to Announcements
- [ ] Can logout successfully

### User Login Test
- [ ] Logged in with: ccc / user123
- [ ] Redirected to user homepage
- [ ] Can see carousel banner
- [ ] Can see announcements section
- [ ] Can see recipe cards
- [ ] Can see food notes
- [ ] Can see product showcase
- [ ] User dropdown menu works
- [ ] Can logout successfully

## ☐ Functionality Verification

### Database Connectivity
- [ ] Backend connects to MySQL successfully
- [ ] No database connection errors in backend log
- [ ] Can query user data
- [ ] Can query recipe data

### Authentication
- [ ] Login works for admin account
- [ ] Login works for user account
- [ ] Invalid credentials show error message
- [ ] JWT token is generated and stored
- [ ] Protected routes require authentication
- [ ] Logout clears token and redirects to login

### Admin Features
- [ ] Dashboard displays statistics
- [ ] User list loads correctly
- [ ] Can view user details
- [ ] Recipe list loads correctly
- [ ] Note list loads correctly
- [ ] Announcement list loads correctly
- [ ] Search functionality works
- [ ] Pagination works

### User Features
- [ ] Homepage loads correctly
- [ ] Carousel displays images
- [ ] Announcements display correctly
- [ ] Recipe cards display correctly
- [ ] Note cards display correctly
- [ ] Navigation menu works
- [ ] Search bar is functional
- [ ] User profile dropdown works

## ☐ Development Environment

### Code Editor
- [ ] Have VS Code or preferred IDE installed
- [ ] Can open and edit project files
- [ ] Syntax highlighting works
- [ ] Can navigate project structure

### Browser Developer Tools
- [ ] Know how to open DevTools (F12)
- [ ] Can view Console for errors
- [ ] Can view Network tab for API calls
- [ ] Can view Application tab for localStorage

### Terminal/PowerShell
- [ ] Comfortable using PowerShell commands
- [ ] Can navigate directories (cd)
- [ ] Can run scripts (.bat files)
- [ ] Can stop processes (Ctrl+C)

## ☐ Documentation Review

- [ ] Read START_HERE.txt
- [ ] Read QUICKSTART.md
- [ ] Reviewed WINDOWS_SETUP.md
- [ ] Understand project structure
- [ ] Know where to find API documentation
- [ ] Know where to find frontend documentation

## ☐ Troubleshooting Knowledge

- [ ] Know how to check if MySQL is running
- [ ] Know how to find processes using ports
- [ ] Know how to kill processes
- [ ] Know how to clear browser cache
- [ ] Know how to check error logs
- [ ] Know where to find help documentation

## ☐ Next Steps

- [ ] Understand the codebase structure
- [ ] Reviewed entity models
- [ ] Reviewed API endpoints
- [ ] Reviewed Vue components
- [ ] Ready to implement new features
- [ ] Know how to add new endpoints
- [ ] Know how to add new pages
- [ ] Know how to modify database schema

## Common Issues Checklist

If something doesn't work, check:

- [ ] All prerequisites are installed
- [ ] PowerShell was closed and reopened after installations
- [ ] MySQL service is running
- [ ] Database password is correct in application.yml
- [ ] No other applications using ports 8080 or 5173
- [ ] Backend server started successfully
- [ ] Frontend server started successfully
- [ ] No errors in browser console
- [ ] No errors in backend terminal
- [ ] No errors in frontend terminal

## Success Criteria

You're ready to develop when:

✅ All prerequisites installed and verified
✅ Database created and populated with sample data
✅ Backend starts without errors
✅ Frontend starts without errors
✅ Can login as admin
✅ Can login as user
✅ All pages load correctly
✅ No console errors
✅ Understand project structure
✅ Know where to find documentation

## Time Tracking

- Prerequisites installation: _____ minutes
- Database setup: _____ minutes
- Backend setup: _____ minutes
- Frontend setup: _____ minutes
- Testing: _____ minutes
- **Total setup time: _____ minutes**

## Notes

Use this space to write down any issues you encountered and how you solved them:

```
Issue 1:


Solution:


Issue 2:


Solution:


```

## Completion

- [ ] All items checked
- [ ] Application running successfully
- [ ] Ready to start development

**Setup completed on:** _______________

**Ready to code!** 🚀
