# Installing Prerequisites on Windows

## Quick Links

Download all prerequisites first:

1. **JDK 21**: https://www.oracle.com/java/technologies/downloads/#java21
2. **Maven**: https://maven.apache.org/download.cgi (Binary zip archive)
3. **Node.js 22**: https://nodejs.org/en/download/
4. **MySQL**: https://dev.mysql.com/downloads/installer/

## Installation Steps

### 1. Install JDK 21

1. Download **Windows x64 Installer** from Oracle
2. Run the `.exe` file
3. Follow installation wizard (use default settings)
4. Verify installation:
   ```powershell
   java -version
   ```
   Should show: `java version "21.x.x"`

### 2. Install Maven

1. Download **Binary zip archive** (e.g., `apache-maven-3.9.5-bin.zip`)
2. Extract to: `C:\Program Files\Apache\maven`
3. Add to System PATH:
   - Press `Win + X` → System
   - Click "Advanced system settings"
   - Click "Environment Variables"
   - Under "System variables", find and select "Path"
   - Click "Edit"
   - Click "New"
   - Add: `C:\Program Files\Apache\maven\bin`
   - Click OK on all dialogs
4. **Close and reopen PowerShell**
5. Verify installation:
   ```powershell
   mvn -version
   ```

### 3. Install Node.js 22

1. Download **Windows Installer (.msi)** - LTS version
2. Run the installer
3. Check "Automatically install necessary tools" (optional)
4. Complete installation
5. Verify installation:
   ```powershell
   node -v
   npm -v
   ```

### 4. Install MySQL

1. Download **MySQL Installer** (mysql-installer-community)
2. Run the installer
3. Choose **"Developer Default"** setup type
4. Click "Execute" to download and install components
5. Configuration:
   - **Type and Networking**: Use defaults (Port 3306)
   - **Authentication**: Use Strong Password Encryption
   - **Accounts and Roles**: Set root password (remember this!)
   - **Windows Service**: Keep defaults
6. Complete installation
7. Verify installation:
   ```powershell
   mysql --version
   ```

## Post-Installation Setup

### Configure MySQL Root Password

Remember your MySQL root password! You'll need it for:
- Database setup
- Backend configuration

### Update Backend Configuration

Edit `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    username: root
    password: YOUR_MYSQL_ROOT_PASSWORD  # Change this!
```

## Verification Checklist

Run these commands in PowerShell to verify everything is installed:

```powershell
# Check Java
java -version

# Check Maven
mvn -version

# Check Node.js
node -v

# Check npm
npm -v

# Check MySQL
mysql --version

# Check MySQL is running
Get-Service MySQL*
```

All commands should return version information without errors.

## Troubleshooting

### "Command not found" errors

**Solution**: Close and reopen PowerShell after installation.

If still not working:
1. Verify the program is installed
2. Check System PATH includes the program's bin directory
3. Restart computer if needed

### Maven not found after adding to PATH

1. Verify the path is correct: `C:\Program Files\Apache\maven\bin`
2. Check if folder exists
3. Close ALL PowerShell windows and open a new one
4. Try running as Administrator

### MySQL won't start

1. Open Services: `services.msc`
2. Find "MySQL" service
3. Right-click → Start
4. If fails, check error logs in MySQL data directory

### Node.js installation issues

1. Uninstall any existing Node.js versions
2. Download fresh installer
3. Run as Administrator
4. Restart computer after installation

## Alternative: Using Chocolatey (Package Manager)

If you have Chocolatey installed:

```powershell
# Run PowerShell as Administrator
choco install openjdk21 -y
choco install maven -y
choco install nodejs-lts -y
choco install mysql -y
```

## Next Steps

After all prerequisites are installed:

1. ✅ Verify all installations
2. ✅ Configure MySQL root password in backend config
3. ✅ Run database setup: `.\setup-database.bat`
4. ✅ Start development servers: `.\start-dev.bat`

## Getting Help

If you encounter issues:

1. **Check Windows version**: Windows 10/11 recommended
2. **Run as Administrator**: Some installations require admin rights
3. **Disable antivirus temporarily**: May block installations
4. **Check disk space**: Ensure at least 5GB free space
5. **Restart computer**: After installing all prerequisites

## Estimated Installation Time

- JDK 21: 5 minutes
- Maven: 2 minutes
- Node.js: 5 minutes
- MySQL: 10 minutes
- **Total: ~25 minutes**

First-time project setup (downloading dependencies):
- Backend (Maven): 5-10 minutes
- Frontend (npm): 2-5 minutes
